#include <iostream>
#include <fstream>
#include <set>
#include <vector>
#include "Player.h"
#include "ConsolePrinter.h"
#include "Board.h"
#include "Move.h"
#include "Bag.h"
#include "rang.h"

using namespace std;

int main(int argc, char const *argv[])
{
	int handsize;
	int seed = 0;
	int numPlayers;
	bool gameOver = false;
	vector<Player*> players;
	string tempName;
	string dictionaryFile;
	string boardFile;
	string tileFile;

	if(argc < 2)
	{
		cout << "Enter a config file" << endl;
		return 0;
	}
	ifstream ifile(argv[1]);
	if(ifile.fail())
	{
		cout << "Error loading config file." << endl;
		return 0;
	}

	string configTitle;
	//initialize file
	while(!ifile.eof())
	{
		ifile >> configTitle;
		if(configTitle == "HANDSIZE:") ifile >> handsize;
		else if(configTitle == "TILES:") ifile >> tileFile;
		else if(configTitle == "DICTIONARY:") ifile >> dictionaryFile;
		else if(configTitle == "BOARD:") ifile >> boardFile;
		else if(configTitle == "SEED:") ifile >> seed;
		else cout << "Unknown config command" << endl; continue;
	}
	ifile.close();
	//create classes
	ConsolePrinter printer;
	Board GameBoard(boardFile);
	Bag GameBag(tileFile, seed);
	Dictionary GameDic(dictionaryFile);

	cout << "Enter a number of players between 1 and 8" << endl;
	cin >> numPlayers;
	while(numPlayers < 1 || numPlayers > 8)
	{
		cout << "Error: Must enter a number of players between 1 and 8" << endl;
		cin >> numPlayers;
	}

	//initialize players
	for(int i = 1; i <= numPlayers; i++)
	{
		cout << "Enter Player " << i << " name." << endl;
		cin >> tempName; 
		players.push_back(new Player(tempName, handsize));
	}
	//show starting player and hand
	printer.printBoard(GameBoard);
	for(int i = 0; i < numPlayers; i++)
	{
		players[i]->addTiles(GameBag.drawTiles(handsize));
	}
	printer.printHand(*players[0]);

	int i = 0;
	int passCount = 0;
	bool firstMove = true;
	//string pause;
	while(!gameOver)
	{
		//allows game to be easier to follow
		if(!firstMove) cout << "Press enter to continue:" << endl;
		cin.ignore();

		//print player and board data
		printer.printBoard(GameBoard);
		cout << endl;
		cout << players[i]->getPlayerName() << "'s hand." << endl;
 		printer.printHand(*players[i]);

 		//prompt player to enter move
		std::string tempMove;
		cout << players[i]->getPlayerName() << "'s turn. Enter a move (Place, Exchange, Pass):" << endl;
		getline(cin, tempMove);

		//execute that move
		Move* m1 = Move::parseMove(tempMove, *players[i]);
		m1->execute(GameBoard, GameBag, GameDic);

		//retry turn if move fails
		firstMove = false;
		if(!m1->getValidMove())
		{
			cout << "Try again!" << endl;
			delete m1;
			continue;
		}

		//show new hand if it is an exchange
		if(m1->isExchange())
		{
			cout << endl;
			cout << players[i]->getPlayerName() << "'s new hand." << endl;
			printer.printHand(*players[i]);
		}

		cout << players[i]->getPlayerName() << "'s score: " << players[i]->getScore() << endl;

		//check if any players have gotten rid of all tiles
		if(players[i]->getCurrTiles().size() == 0 && GameBag.tilesRemaining() == 0)
		{
			gameOver = true;
			for(int j = 0; j < numPlayers; j++)
			{
				if(j != i)
				{
					int negativeScore = 0;
					for(unsigned int z = 0; z < players[j]->getCurrTiles().size(); z++)
					{
						negativeScore += players[j]->getCurrTiles()[z]->getPoints();
					}
					players[j]->changeScore((negativeScore*-1));
					players[i]->changeScore(negativeScore);
				}
			}
		}
		//check if each player has passed
		if(m1->isPass())
		{
			passCount++;
			if(passCount == numPlayers)
			{
				gameOver = true;
				for(int j = 0; j < numPlayers; j++)
				{
					int negativeScore = 0;
					for(unsigned int z = 0; z < players[j]->getCurrTiles().size(); z++)
					{
						negativeScore += players[j]->getCurrTiles()[z]->getPoints();
					}
					players[j]->changeScore((negativeScore*-1));
				}
			}	
		}
		else
		{
			passCount = 0;
		}
		delete m1;
		if(i == numPlayers-1) i = 0;
		else i++;
	}

	//print winners
	int tempLeaderScore = -1000;
	for(int i = 0; i < numPlayers; i++)
	{
		if(players[i]->getScore() >= tempLeaderScore)
		{
			tempLeaderScore = players[i]->getScore();
		}
	}
	vector<Player*> winners;
	for(int i = 0; i < numPlayers; i++)
	{
		if(players[i]->getScore() == tempLeaderScore)
		{
			winners.push_back(players[i]);
		}
	}
	if(winners.size() == 1) cout << "Winner: " << winners[0]->getPlayerName() << endl;
	else
	{
		cout << "Winners:";
		for(unsigned int i = 0; i < winners.size(); i++)
		{
			cout << " " << winners[i]->getPlayerName();
		}
		cout << endl;
	}	
	//delete players
	for(int i = 0; i < numPlayers; i++)
	{
		delete players[i];
	}
		
	return 0;
}