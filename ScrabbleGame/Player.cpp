#include "Player.h"
#include "Tile.h"
#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <set>

Player::Player(std::string const & name, size_t maxTiles)
{
	playerName = name;
	maxT = maxTiles;
	score = 0;
}

Player::~Player ()
{
	for(unsigned int i = 0; i < currTiles.size(); i++)
	{
		delete currTiles[i];
	}
}

std::set<Tile*> Player::getHandTiles() const
{
	std::set<Tile*> handTiles;
	for(unsigned int i = 0; i < currTiles.size(); i++)
	{
		handTiles.insert(currTiles[i]);
	}
	return handTiles;
}

bool Player::hasTiles(std::string const & move, bool resolveBlanks) const
{
	std::string tempString = move;
	unsigned int count = 0;
	std::vector<Tile*> temp = currTiles;

	//temp[j]->getLetter() returns lowercase so we must make move string lower
	for(unsigned int x = 0; x < tempString.size(); x++)
	{
		if(tempString[x] != '?') tempString[x]=tolower(tempString[x]);
	}

	for(unsigned int i = 0; i < tempString.length(); i++)
	{
		for(unsigned int j = 0; j < temp.size(); j++)
		{
			if(!resolveBlanks)
			{
				if(tempString[i] == temp[j]->getLetter())
				{
					count++;
					temp.erase(temp.begin() + j);
					break;
				}
			}
			else
			{
				if(tempString[i] == '?')
				{
					if(temp[j]->isBlank())
					{
						count+=2;
						temp.erase(temp.begin() + j);
						i++;
						break;
					}
				}
				if(tempString[i] == temp[j]->getLetter())
				{
					count++;
					temp.erase(temp.begin() + j);
					break;
				}
			}
		}	
	}
	if(count == move.length()) return true;
	else return false;	
}

std::vector<Tile*> Player::takeTiles(std::string const & move, bool resolveBlanks)
{
	std::string tempString = move;
	std::vector<Tile*> retVector;
	resetTiles = currTiles;

	for(unsigned int x = 0; x < tempString.size(); x++)
	{
		if(tempString[x] != '?') tempString[x]=tolower(tempString[x]);
	}


	for(unsigned int i = 0; i < tempString.length(); i++)
	{
		for(unsigned int j = 0; j < currTiles.size(); j++)
		{
			if(!resolveBlanks)
			{
				if(tempString[i] == currTiles[j]->getLetter())
				{
					retVector.push_back(currTiles[j]);
					currTiles.erase(currTiles.begin() + j);
					break;
				}
			}
			else
			{
				if(tempString[i] == '?')
				{
					if(currTiles[j]->isBlank())
					{
						currTiles[j]->useAs(tempString[i+1]);
						retVector.push_back(currTiles[j]);
						currTiles.erase(currTiles.begin() + j);
						i++;
						break;
					}
				}
				if(tempString[i] == currTiles[j]->getLetter())
				{
					retVector.push_back(currTiles[j]);
					currTiles.erase(currTiles.begin() + j);
					break;
				}
			}
		}
	}

	return retVector;
}


void Player::addTiles (std::vector<Tile*> const & tilesToAdd)
{
	for(unsigned int i = 0; i < tilesToAdd.size(); i++)
	{
		currTiles.push_back(tilesToAdd[i]);
	}
}

void Player::changeScore(int points)
{
	score = score + points;
}

void Player::resetTakenTiles()
{
	currTiles = resetTiles;
}

void Player::setResetTiles(std::vector<Tile*> reset)
{
	resetTiles = reset;
}

size_t Player::getMaxTiles() const
{
	return maxT;
}

int Player::getScore()
{
	return score;
}

std::string Player::getPlayerName() const
{
	return playerName;
}

std::vector<Tile*> Player::getCurrTiles() const
{
	return currTiles;
}
