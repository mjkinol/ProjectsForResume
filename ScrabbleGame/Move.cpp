#include "Move.h"
#include "Board.h"
#include <sstream>
#include <iostream>

Move::Move(Player * player)
{
	_player = player;
	validMove = true;
}

Move::~Move(){}

Move * Move::parseMove(std::string moveString, Player &p)
{
	std::stringstream ss;
	std::string temp;
	std::string mover;

	for(unsigned int x = 0; x < moveString.size(); x++)
	{
		if(moveString[x] != '?') moveString[x]=toupper(moveString[x]);
	}
	ss << moveString;
	ss >> temp;

	if(temp == "PASS")
	{
		PassMove* currMove = new PassMove(&p);
		return currMove;
	}
	else if(temp == "EXCHANGE")
	{
		ss >> mover;
		ExchangeMove* currMove = new ExchangeMove(mover, &p);
		return currMove;
	}
	else if (temp == "PLACE")
	{
		bool horizontal;
		size_t x;
		size_t y;
		temp.clear();
		ss >> temp;
		if (temp == "-") horizontal = true;
		else horizontal = false;
		ss >> y >> x >> mover;
		PlaceMove* currMove = new PlaceMove(x, y, horizontal, mover, &p);
		return currMove;
	}
	else
	{
		std::string error = "_invalid_input-over-max_tiles_";
		ExchangeMove* currMove = new ExchangeMove(error, &p);
		return currMove;
	}
}

PassMove::PassMove(Player * player) : Move(player)
{

}

void PassMove::execute(Board & board, Bag & bag, Dictionary & dictionary)
{
	return;
}

ExchangeMove::ExchangeMove(std::string &tileString, Player * p) : Move(p)
{
	ExmoveTiles = tileString;
}

void ExchangeMove::execute(Board & board, Bag & bag, Dictionary & dictionary)
{
	if(ExmoveTiles == "_invalid_input-over-max_tiles_") 
	{
		validMove = false;
		std::cout << "Enter a valid move..." << std::endl;
		return;
	}
	std::vector<Tile*> backToBag;
	std::vector<Tile*> tilesToAdd;
	if(_player->hasTiles(ExmoveTiles, false))
	{
		backToBag = _player->takeTiles(ExmoveTiles, false);
		bag.addTiles(backToBag);
		tilesToAdd = bag.drawTiles(backToBag.size());
		_player->addTiles(tilesToAdd);
	}
	else
	{
		std::cout << "You do not have the proper tiles." << std::endl;
		validMove = false;
		return;
	}
	//set reset tiles
	_player->setResetTiles(_player->getCurrTiles());
}

PlaceMove::PlaceMove(size_t x, size_t y, bool horizontal, std::string &tileString, Player * p) : Move(p)
{
	validMove = true;
	x1 = x;
	y1 = y;
	hzntl = horizontal;
	moveTiles = tileString;
}

void PlaceMove::execute(Board & board, Bag & bag, Dictionary & dictionary)
{
	std::vector<std::pair<std::string, unsigned int>> words;
	std::vector<Tile*> onBoard;
	std::vector<Tile*> tilesToAdd;
	bool firstMove = board.getFirstMove();

	//make sure player starts on start square
	if(firstMove && !board.getSquare(x1, y1)->isStart())
	{
		std::cout << "Must start on start square" << std::endl;
		validMove = false;
		return;
	}

	words = board.getPlaceMoveResults(*this);

	//words will be zero if any failures
	if(words.size() == 0)
	{
		std::cout << "Invalid Move" << std::endl;
		validMove = false;
		if(!firstMove)_player->resetTakenTiles();
		return;
	}
	//checks that all words are legal
	int tempPoints = 0;
	for(unsigned int i = 0; i < words.size(); i++)
	{
		if(dictionary.isLegalWord(words[i].first))
		{
			tempPoints += words[i].second;
		}
		else
		{
			_player->resetTakenTiles();
			std::cout << "Word does not exist" << std::endl;
			validMove = false;
			return;
		}
	}

	tilesToAdd = bag.drawTiles(moveTiles.size());
	_player->addTiles(tilesToAdd);
	//This is to take tiles after playing a given number

	if(moveTiles.size() == _player->getMaxTiles())
	{
		tempPoints += 50;
	}
	board.executePlaceMove(*this);

	_player->changeScore(tempPoints);
}

size_t PlaceMove::getx1() const
{
	return x1;
}

size_t PlaceMove::gety1() const
{
	return y1;
}

bool PlaceMove::getHorz() const
{
	return hzntl;
}

Player* PlaceMove::getPlayer() const
{
	return _player;
}

std::string PlaceMove::getMoveTiles() const
{
	return moveTiles;
}

bool Move::getValidMove() const
{
	return validMove;
}

