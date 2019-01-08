#include <iostream>
#include "HashTable.h"
using namespace std;

int main()
{
	HashTable<int> hashPrime;		 //HashTable Created With 11 Slots
	HashTable<int> hashNonPrime(15); //HashTable Created With 15 Slots

	int arr[] = {12,24,36,48,60,72,84,96,108,120};

	//calculate array size
	int arrLength = 0;
	for (auto val : arr) 
	{
		arrLength++;
	}

	//Initialize Hash Table of size M = 11 with an Array of Integers 
	hashPrime.copyArray(arr, arrLength);
	hashPrime.display();

	//Initialize Hash Table of size M = 15 with an Array of Integers 
	hashNonPrime.copyArray(arr, arrLength);
	hashNonPrime.display();

	system("pause");

	
	return 0;
}



