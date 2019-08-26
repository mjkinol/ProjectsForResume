#include "Board.h"
#include <iostream>
#include <fstream>


Board::Board (std::string board_file_namey)
{
	firstMove = true;
	unsigned int startX;
	unsigned int startY;
	std::ifstream ifile(board_file_namey);
	std::string tempLine;

	ifile >> cols;
	ifile >> rows;
	ifile >> startX;
	ifile >> startY;
	std::getline(ifile, tempLine);

	for(unsigned int i = 0; i < rows; i++)
	{
		gameSquare.push_back(std::vector<Square*>());
		tempLine.clear();
		std::getline(ifile, tempLine);
		for(unsigned int j = 0; j < cols; j++)
		{
			unsigned int lmult = 1;
			unsigned int wmult = 1;
			bool start = false;
			if(j == startX-1 && i == startY-1) start = true;
			if(tempLine[j] == '2') lmult = 2;
			if(tempLine[j] == '3') lmult = 3;
			if(tempLine[j] == 'd') wmult = 2;
			if(tempLine[j] == 't') wmult = 3;
			gameSquare[i].push_back(new Square(lmult, wmult, start));
		}
	}
}

Board::~Board()
{
	for(unsigned int i = 0; i < cols; i++)
	{
		for (unsigned int j = 0; j < rows; j++)
		{
			delete gameSquare[i][j];
		}
	}
}

std::vector<std::pair<std::string, unsigned int>> Board::getPlaceMoveResults(const PlaceMove &m) const
{
	//variables copied over from player and move
	Player* _player = m.getPlayer();
	_player->adjacent = false;
	std::string moveTiles = m.getMoveTiles();
	size_t x1 = m.getx1();
	size_t y1 = m.gety1();
	bool hzntl = m.getHorz();
	//vectors used to create pairs 
	std::pair<std::string, unsigned int> mainPair;
	std::string tempWord = "";
	std::string LtempWord = "";
	std::string RtempWord = "";
	unsigned int tempScore = 0;
	std::vector<std::pair<std::string, unsigned int>> retVec;

	//creates a vector of tiles about to be places
	//need it for creating main word with proper score
	if(firstMove) _player->adjacent = true;
	if(!_player->hasTiles(moveTiles, true))
	{
		std::cout << "You do not have the proper tiles..." << std::endl;
		retVec.clear();
		return retVec;
	}

	_player->placeTiles = _player->takeTiles(moveTiles, true);
	const std::vector<Tile*> wordTiles = _player->placeTiles;

	//checks if start space is occupied
	if(getSquare(x1, y1)->isOccupied())
	{
		std::cout << "Cannot start a word in an occupied square..." << std::endl;
		retVec.clear();
		return retVec;
	}

	mainPair = getMainWord(m, wordTiles);
	if (mainPair.second == 0 && !wordTiles[0]->isBlank())
	{
		retVec.clear(); 
		return retVec;
	}

	//checks for other words created not at the end or begning
	int tempWMult = 1;
	bool accounted = false;
	if(hzntl == false)
	{
		//counter from y1 to end of the word, including already played letters
		unsigned int ycount = y1;
		//checks left and right of each tile in the verticle line
		unsigned int y = 0;
		while(y < wordTiles.size())
		{
			if(!getSquare(x1, ycount)->isOccupied())
			{
				//checks all of the values to the left
				unsigned int xcount = x1;
				if(xcount - 1 > 0 && ycount <= rows)
				{
					while(getSquare(xcount - 1, ycount)->isOccupied() && xcount > 0)
					{
						_player->adjacent = true;
						if(tempWMult) tempWMult = getSquare(x1, ycount)->getWMult();
						LtempWord = getSquare(xcount - 1, ycount)->getLetter()+LtempWord;
						tempScore += getSquare(xcount - 1, ycount)->getScore();
						if(xcount == x1 && !accounted)
						{
							tempScore += wordTiles[y]->getPoints()*getSquare(x1, ycount)->getLMult();
						}
						accounted = true;
						xcount--;
						if(xcount - 1 < 1) break;
					}
				}
				//checks all of the values to the right
				xcount = x1;
				if(xcount + 1 <= cols && ycount <= rows)
				{
					while(getSquare(xcount+1, ycount)->isOccupied()	&& xcount <= cols)
					{
						_player->adjacent = true;
						if(tempWMult == 1) tempWMult = getSquare(x1, ycount)->getWMult();
						if(xcount == x1 && !accounted)
						{
							tempScore += (wordTiles[y]->getPoints())*getSquare(x1, ycount)->getLMult();
						}
						RtempWord = RtempWord + getSquare(xcount+1, ycount)->getLetter();
						tempScore += getSquare(xcount + 1, ycount)->getScore();
						accounted = true;
						xcount++;
						if(xcount + 1 > cols) break;
					}
				}
				//adds words to retVector
				tempWord = LtempWord + wordTiles[y]->getUse() + RtempWord;
				if(tempWord.size() > 1)
				{
					retVec.push_back(std::make_pair(tempWord, tempWMult*tempScore));
				}
				tempWMult = 1;
				tempWord.clear();
				tempScore = 0;
				ycount++;
			}
			y++;

		}
	}
	if(hzntl == true)
	{
		//counter from y1 to end of the word, including already played letters
		unsigned int xcount = x1;
		//checks left and right of each tile in the verticle line
		unsigned int x = 0;
		while(x < wordTiles.size())
		{
			if(!getSquare(xcount, y1)->isOccupied())
			{
				//checks all of the values to the left
				//makes sure that we do not go out of the board 
				unsigned int ycount = y1;
				if(ycount - 1 > 0 && xcount <= cols)
				{
					while(getSquare(xcount, ycount-1)->isOccupied() && ycount > 0)
					{
						_player->adjacent = true;
						if(tempWMult == 1) getSquare(x1, ycount)->getWMult();
						LtempWord=getSquare(xcount, ycount-1)->getLetter()+LtempWord;
						if(ycount == y1 && !accounted)
						{
							tempScore += wordTiles[x]->getPoints()*getSquare(xcount, y1)->getLMult();
						}
						tempScore += getSquare(xcount, ycount-1)->getScore();
						accounted = true;
						ycount--;
						if(ycount - 1 < 0) break;
					}
				}
				//checks all of the values to the right
				ycount = y1;
				if(ycount + 1 <= rows && xcount <= cols)
				{
					while(getSquare(xcount, ycount+1)->isOccupied() && ycount <= rows)
					{
						_player->adjacent = true;
						if(tempWMult == 1) tempWMult = getSquare(x1, ycount)->getWMult();
						if(ycount == y1 && !accounted)
						{
							tempScore += wordTiles[x]->getPoints()*getSquare(xcount, y1)->getLMult();
						}
						RtempWord=RtempWord+getSquare(xcount, ycount+1)->getLetter();
						tempScore += getSquare(xcount, ycount+1)->getScore();
						accounted = true;
						ycount++;
						if(ycount + 1 > rows) break;
					}
				}
				//add right and left side of the word
				tempWord = LtempWord + wordTiles[x]->getUse() + RtempWord;
				if(tempWord.size() > 1)
				{
					retVec.push_back(std::make_pair(tempWord, tempWMult*tempScore));
				}
				tempWord.clear();
				tempScore = 0;
				xcount++;
				tempWMult = 1;
			}
			x++;

		}
	}
	if(y1 + wordTiles.size() <= rows)
	{
		if(getSquare(x1, y1 + wordTiles.size())->isOccupied())_player->adjacent=true;
	}
	if(x1 + wordTiles.size() <= cols)
	{
		if(getSquare(x1 + wordTiles.size(), y1)->isOccupied())_player->adjacent=true;
	}
	if(!_player->adjacent)
	{
		std::cout << "At least one tile must be adjacent..." << std::endl;
		_player->addTiles(wordTiles);
		retVec.clear();
		return retVec;
	}
	retVec.push_back(mainPair);
	return retVec;
}


std::pair<std::string, unsigned int> Board::getMainWord(const PlaceMove &m, std::vector<Tile*> wordTiles) const
{
	Player* _player = m.getPlayer();
	std::string leftSide = "";
	std::string rightSide = "";
	int x1 = m.getx1();
	int y1 = m.gety1();
	bool hzntl = m.getHorz();
	//vectors used to create pairs 
	std::string mainWord = "";
	unsigned int mainScore = 0;

	//Creates main word and score for vertical
	int MainWMult = 1;
	if(hzntl == false)
	{
		//counter from y1 to end of the word, including already played letters
		unsigned int bcounter = y1;
		unsigned int fcounter = y1;
		for(unsigned int y = 0; y < wordTiles.size(); y++)
		{
			//checks if word is too long, prevents segfaults
			if(1+fcounter > rows && y < wordTiles.size()-1)
			{
				std::cout << "Word does not fit vertically..." << std::endl;
				_player->addTiles(wordTiles);
				mainScore = 0;
				return std::make_pair(mainWord, mainScore* MainWMult);
			}
			if(bcounter-1 > 0)
			{
				//checks for letters above start space
				while(getSquare(x1, bcounter-1)->isOccupied() && bcounter > 0)
				{
					leftSide = getSquare(x1, bcounter-1)->getLetter() + leftSide;
					mainScore += getSquare(x1, bcounter-1)->getScore();
					bcounter--;
					_player->adjacent = true;
					if(bcounter-1 < 1) break;
				}
			}
			if(fcounter+1 <= rows)
			{
				//checks for anything after start space
				while(getSquare(x1, fcounter+1)->isOccupied() && fcounter <= rows)
				{
					//if there is occupied square add it to word and score
					rightSide = rightSide + getSquare(x1, fcounter+1)->getLetter();
					mainScore += getSquare(x1, fcounter+1)->getScore();
					_player->adjacent = true;
					fcounter++;
					if(fcounter+1 > rows) break;
				}
			}
			int LMult = 1;
			if(y == 0) LMult = getSquare(x1, y1)->getLMult();
			else LMult = getSquare(x1, fcounter)->getLMult();
			MainWMult *= getSquare(x1, fcounter)->getWMult();
			//put together componants of main word and score
			if(leftSide != "") mainWord = mainWord +leftSide;
			mainWord = mainWord + wordTiles[y]->getUse();
			if(rightSide != "") mainWord = mainWord + rightSide;
			mainScore += (wordTiles[y]->getPoints())*LMult;
			fcounter++;
			rightSide = "";
			leftSide = "";
		}
	}
	//creates mainword and score for horozontal
	if(hzntl == true)
	{
		//counter from y1 to end of the word, including already played letters
		unsigned int bcounter = x1;
		unsigned int fcounter = x1;
		for(unsigned int x = 0; x < wordTiles.size(); x++)
		{
			//checks if word is too long, prevents segfaults
			if((1+fcounter > cols && x < wordTiles.size()-1))
			{
				std::cout << "Word does not fit horozontally..." << std::endl;
				_player->addTiles(wordTiles);
				mainScore = 0;
				return std::make_pair(mainWord, mainScore* MainWMult);
			}
			if(1-bcounter >= 1)
			{
				//checks for letters above start space
				while(getSquare(bcounter-1, y1)->isOccupied() && bcounter > 0)
				{
					leftSide = getSquare(bcounter-1, y1)->getLetter() + leftSide;
					mainScore += getSquare(bcounter-1, y1)->getScore();
					bcounter--;
					_player->adjacent = true;
					if(1-bcounter < 1) break;
				}
			} 
			if(1+fcounter <= cols)
			{
				//checks for anything after start space
				while(getSquare(fcounter+1, y1)->isOccupied() && fcounter <= cols)
				{
					//if there is occupied square add it to word and score
					rightSide = rightSide + getSquare(fcounter+1, y1)->getLetter();
					mainScore += getSquare(fcounter+1, y1)->getScore();
					_player->adjacent = true;
					fcounter++;
					if(1+fcounter > cols) break;
				}
			}
			//add our new letters to the board using our tiles
			int LMult = 1;
			if(x == 0) LMult = getSquare(x1, y1)->getLMult();
			else LMult = getSquare(fcounter, y1)->getLMult();
			MainWMult *= getSquare(fcounter, y1)->getWMult();
			//put together components of mainword and score
			if(leftSide != "") mainWord = mainWord +leftSide;
			mainWord = mainWord + wordTiles[x]->getUse();
			if(rightSide != "") mainWord = mainWord + rightSide;
			mainScore +=(wordTiles[x]->getPoints())*LMult;
			fcounter++;
			rightSide = "";
			leftSide = "";
		}
		//combine letters before and after given tiles
		mainWord = mainWord + rightSide;
	}
	return std::make_pair(mainWord, mainScore* MainWMult);
}

void Board::executePlaceMove (const PlaceMove & m)
{
	Player* _player = m.getPlayer();
	int x1 = m.getx1();
	int y1 = m.gety1();
	bool hzntl = m.getHorz();

	if(hzntl == false)
	{	
		unsigned int y = 0;
		for(y = y1; y < y1 + _player->placeTiles.size(); y++)
		{
			if(gameSquare[y-1][x1-1]->isOccupied()) y1++;
			else gameSquare[y-1][x1-1]->placeTile(_player->placeTiles[y-y1]);
		}
	}
	else if(hzntl == true)
	{
		unsigned int x = 0;
		for(x= x1; x < x1 + _player->placeTiles.size(); x++)
		{
			if(gameSquare[y1-1][x-1]->isOccupied()) x1++;
			else gameSquare[y1-1][x-1]->placeTile(_player->placeTiles[x-x1]);
		}
	}
	firstMove = false;
}


Square * Board::getSquare (size_t x, size_t y) const
{
	return gameSquare[y-1][x-1];
}


size_t Board::getRows() const
{
	return rows;
}

size_t Board::getColumns() const
{
	return cols;
}

bool Board::getFirstMove() const
{
	return firstMove;
}