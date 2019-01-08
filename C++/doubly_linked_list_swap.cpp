/*******************************************************
  Created by Joel Jacques on 3/22/2017
  CSC-241 Midterm Makeup Assignment - DLL Swap Function
*******************************************************/

#include<string>
#include<iostream>
using namespace std;

struct Node
{
	string data;
	Node *prev;
	Node *next;
};

void displayList(Node *head);
void insertNode(Node *&head, string value);
void swapNodes(Node *head);

int main()
{
	Node *head = NULL;
	insertNode(head, "100");
	insertNode(head, "200");
	insertNode(head, "300");
	insertNode(head, "400");
	displayList(head);

	swapNodes(head);
	cout << "Nodes swapped" << endl;
	displayList(head);
	system("pause");
	return 0;
}

void insertNode(Node *&head, string value) {
	Node *nodePtr(nullptr);
	Node *currPtr(nullptr);
	Node *newNode;
	newNode = new Node();
	newNode->next = NULL;
	newNode->prev = NULL;
	newNode->data = value;

	if (!head)
		head = newNode;
	else
	{
		nodePtr = head;
		while (nodePtr != NULL) 
		{
			currPtr = nodePtr;
			nodePtr = nodePtr->next;
		}
		currPtr->next = newNode;
		newNode->prev = currPtr;
	}
}

void displayList(Node *head)
{
	Node *nodePtr;
	nodePtr = head;
	if (head == NULL)
		cout << "list is empty" << endl;
	else
	{
		while (nodePtr)
		{
			cout << nodePtr->data << endl;
			nodePtr = nodePtr->next;
		}
	}
}

/******************************************************
*@swapNodes - swaps the two adjacent nodes in the doubly
linked list in the second and third position.
*@pre - a head pointer to the linked list must be 
passed in. The function will only work on a linked list
with at minimum of 4 nodes.
*@post - the reordered list has the node in the third
position in the second position
********************************************************/

void swapNodes(Node* head) {
	Node *nodePtr;
	nodePtr = head->next; //move node to the next node in the list

	nodePtr->next = nodePtr->next->next; //Step 1:
	nodePtr = nodePtr->next->prev;		 //Step 2: Set Pointer
	nodePtr->prev = nodePtr->prev->prev; //Step 3:
	nodePtr = nodePtr->prev->next;		 //Step 4: Set Pointer
	nodePtr->prev = nodePtr->next->prev; //Step 5:
	nodePtr = nodePtr->next->prev;;		 //Step 6: Set Pointer
	nodePtr->next = nodePtr->prev->next; //Step 7:
	nodePtr->prev->next = nodePtr;		 //Step 8:
	nodePtr = nodePtr->next;			 //Step 9: Set Pointer				
	nodePtr->next->prev = nodePtr;		 //Step 10:
}

