// File: HashTable.h

#ifndef _HashTable
#define _HashTable 

#include <vector> 

template<class KeyType>
class HashTable
{
private:
	struct HashItem
	{
		int element;
		bool slotOccupied;
	};

	/**
	PRIVATE MEMBER VARIABLES
	*/

	static const int DEFAULT_CAPACITY = 11; 
	HashItem* ptrTable;
	int M;
	int itemCnt;
	int collisionCnt;

	/**
	PRIVATE MEMBER FUNCTIONS
	*/

	int hashFunction(const int newItem); //HashFunction
	int probeHashTable(const int i);
	bool isCollision(int i) const;

public:
	HashTable();
	HashTable(const int m);
	
	virtual ~HashTable();

	bool isEmpty() const;
	int getNumberOfItems() const;
	void add(const int newItem);
	void copyArray(const KeyType* newItem, int l);
	void display();
}; // end HashTable

// Implementation file for the class HashTable.
// File HashTable.cpp 

/** HashTable Constructors */

template <class KeyType>
HashTable<KeyType>::HashTable() : itemCnt(0), M(DEFAULT_CAPACITY), collisionCnt(0)
{
	ptrTable = new HashItem[DEFAULT_CAPACITY];

	//populate array with HashItems with default values
	for (int i = 0; i < M; i++) 
	{
		ptrTable[i].element = -1;
		ptrTable[i].slotOccupied = false;
	}
} //Default Constructor

template <class KeyType>
HashTable<KeyType>::HashTable(const int m) : itemCnt(0), M(m), collisionCnt(0)
{
	ptrTable = new HashItem[m]; 

	//populate array with HashItems with default values
	for (int i = 0; i < M; i++)
	{
		ptrTable[i].element = -1;
		ptrTable[i].slotOccupied = false;
	}

} //Overloaded Constructor

template <class KeyType>
HashTable<KeyType>::~HashTable() 
{
	delete ptrTable;
} //End Destructor


/** HashTable Member Methods */

/**
PRIVATE MEMBER METHODS
*/

/**
@hashFunction - uses modulo arithmatic to map the search key to a range of 0 and M - 1
which is returned by the function
@pre - the parameter must be an integer. the mod operator requires two operands of
type integer
@post - the search key is hashed and the result of the mod operation is returned as an
integer
*/
template<class KeyType>
int HashTable<KeyType>::hashFunction(const int newItem) 
{
	return newItem % M;
}

/**
@isCollision - function returning a boolean type to indicated if the slot is 
occupied or not
@pre - integer required as parameter
@post - a value of true is returned if the slot is occupied and
false if the slot is not occupied
*/
template <class KeyType>
bool HashTable<KeyType>::isCollision(const int i) const
{
	return ptrTable[i].slotOccupied;
}

/**
@probeHashTable - locate empty slot in the Hash Table when a
collision occurs in the add function.  the function uses linear
probing to identify the correct slot.
@pre - integer required as parameter
@post - returns integer representing a vacant slot in the
array. A value of -1 is returned if the hash table is full.
*/

template <class KeyType>
int HashTable<KeyType>::probeHashTable(const int i)
{
	int probe = i;
	while ((isCollision(probe)) && (probe < M))
	{
		probe++;
	}

	if ((probe == M - 1) && (isCollision(M - 1)))
	{
		probe = 0;
		while ((isCollision(probe)) && (probe <= i))
		{
			probe++;
		}

		if (probe == i)
		{
			probe = -1;
		}
	}

	return probe;
}
/**
PUBLIC MEMBER METHODS
*/

/**
@isEmpty - return boolean type to indicate if the 
hash table contains any keys
@pre - void type
@post - returns true for an empty hash table and false for
a hash table with at least one entry
*/
template <class KeyType>
bool HashTable<KeyType>::isEmpty() const
{
	return itemCnt == 0;
}

/**
@getNumberOfItems - returns the number of slots occupied in the
hash table
@pre - void type
@post - returns an integer indicating the number of slots that
are occupied
*/
template <class KeyType>
int HashTable<KeyType>::getNumberOfItems() const
{
	return itemCnt;
}

/**
@add - add keys to open slots in hash table
@pre - the parameter must be an integer for the hashfunction 
operation 
@post - key added to hash table in an empty slot
*/
template <class KeyType>
void HashTable<KeyType>::add(int newItem) 
{
	int i = hashFunction(newItem);      //map newItem to an integer
	if ((isCollision(i)) && (i != -1))  //condition if collision encountered
	{
		i = probeHashTable(i);			//find empty slot if available
		ptrTable[i].element = newItem; 
		ptrTable[i].slotOccupied = true;
		collisionCnt++;					//accumulator for number of collisions
	}
	else 
	{
		ptrTable[i].element = newItem;
		ptrTable[i].slotOccupied = true;
	}
	itemCnt++; //accumulator for number of keys entered into hash table
}

template <class KeyType>
void HashTable<KeyType>::copyArray(const KeyType* a, int l) 
{
	for (int i = 0; i < l; i++)
	{
		add(a[i]);
	}
}

/**
@display - display contents of hashtable to the console.  Each line contains
the slot position followed by the key values. Empty slots are indicated by the
string empty.
@pre - void function
@post - contents of hash table output to the console
*/
template <class KeyType>
void HashTable<KeyType>::display() 
{
	vector<int> myVector;

	for (int i = 0; i < M; i++)
	{
		if (ptrTable[i].slotOccupied)
		{
			cout  << i << "  " << ptrTable[i].element << endl;
			int s = hashFunction(ptrTable[i].element);
			myVector.push_back(s);
		}
		else 
		{
			cout << i << "  " << "Empty" << endl;
		}
	}
	cout << endl << endl;
	cout << "Collision Count:   " << collisionCnt << endl; //Display Number of Collisions

	//Display Collision Summary
	for (int elem : myVector)
	{
		cout << elem << endl;
	}
	cout << endl << endl;

}

#endif