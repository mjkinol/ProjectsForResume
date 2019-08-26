#include <iostream>
#include <vector>
#include <string>
#include <utility>

template <class T, class Comparator>
void mergeSort (std::vector<T>& myArray, int k, Comparator comp)
{
	std::vector<T> temp(myArray);
	mergeSort(myArray, temp, k, comp, 0, myArray.size()-1);
}

template <class T, class Comparator>
void mergeSort(std::vector<T>& myArray, std::vector<T>& temp, int k, Comparator comp, int l, int r)
{
	int arrSize = (r - l + 1);
	std::vector<std::pair<int, int>> index;
	if(l < r)
	{
		if(arrSize <= k)
		{
			//if the size of the current vector is less than k just selection sort it
			for(int i = l; i <= r-1; i++)
			{
				for(int j = i+1; j <= r; j++)
				{
					//true when j less than i
					if(comp(myArray[j], myArray[i]))
					{
						T tempVal = myArray[i];
						myArray[i] = myArray[j];
						myArray[j] = tempVal;
					}
				}
			}
		}
		else
		{
			std::vector<int> iters;
			int firstIndex, secondIndex;
			int remainder = arrSize%k;
			int constRemain = arrSize%k;
			int part = arrSize/k;
			for(int i = 0; i < k; i++)
			{
				if(remainder == 0) iters.push_back(part);
				else if(remainder > 0)
				{
					iters.push_back(part+1);
					remainder--;
				}
			}
			remainder = constRemain;
			for(int i = 0; i < k; i++)
			{
				if(remainder > 0)
				{
					remainder--;
					firstIndex = i*iters[i]+l;
					secondIndex = (i+1)*iters[i]-1+l;
				}
				else
				{
					firstIndex = i*iters[i]+constRemain+l;
					secondIndex = (i+1)*iters[i]-1+constRemain+l;					 
				}
				index.push_back(std::make_pair(firstIndex, secondIndex));
			}

			for(int i = 0; i < k; i++)
			{
				mergeSort(myArray, temp, k, comp, index[i].first, index[i].second);
			}
			merge(myArray, temp, k, comp, index, arrSize);
		}
	}
}

template <class T, class Comparator>
void merge(std::vector<T>& myArray, std::vector<T>& temp, int k, Comparator comp, std::vector<std::pair<int, int>>& index, int& arrSize)
{
	std::vector<int> counter;
	std::vector<int> max;
	int currMinIndex;
	int increase;

	for(int i = 0; i < k; i++)
	{
		counter.push_back(index[i].first);
		max.push_back(index[i].second);
	}

	for(int i = index[0].first; i < index[0].first+arrSize; i++)
	{
		for(int x = 0; x < k; x++)
		{
			if(counter[x] <= max[x])
			{
				currMinIndex = counter[x];
				increase = x;
				break;
			}
		}
		for(int j = 0; j < k; j++)
		{
			//comp true when j is less than currMinIndex
			if((counter[j] <= max[j]) && (comp(myArray[counter[j]], myArray[currMinIndex])))
			{
				currMinIndex = counter[j];
				increase = j;
			}
		}
		temp[i] = myArray[currMinIndex];
		counter[increase]++;
	}
	for(unsigned int i = 0; i < myArray.size(); i++)
	{
		myArray[i] = temp[i];
	}
}

