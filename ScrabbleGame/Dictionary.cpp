#include "Dictionary.h"
#include <fstream>

Dictionary::Dictionary(std::string dictionary_file_name)
{ 
	std::ifstream ifile(dictionary_file_name);
	std::string tempWord;
	while(!ifile.eof())
	{
		ifile >> tempWord;
		for(unsigned int i = 0; i < tempWord.size(); i++)
		{
			tempWord[i]=tolower(tempWord[i]);
		}
		DictList.insert(tempWord);	
	}
	ifile.close();
}

Dictionary::~Dictionary ()
{
	DictList.clear();
}

bool Dictionary::isLegalWord (std::string const & word) const
{
	std::set<std::string>::iterator setIter;
	//find runs in O(log n) 
	setIter = DictList.find(word);
	if(setIter != DictList.end()) return true;
	else return false;
}

