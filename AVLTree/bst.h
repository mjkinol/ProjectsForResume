#ifndef BST_H
#define BST_H

#include <iostream>
#include <exception>
#include <cstdlib>
#include <utility>
#include <algorithm>

/**
* A templated class for a Node in a search tree. The getters for parent/left/right are virtual so that they
* can be overridden for future kinds of search trees, such as Red Black trees, Splay trees, and AVL trees.
*/
template <typename Key, typename Value>
class Node
{
public:
	Node(const Key& key, const Value& value, Node<Key, Value>* parent);
	virtual ~Node();

	const std::pair<Key, Value>& getItem() const;
	std::pair<Key, Value>& getItem();
	const Key& getKey() const;
	const Value& getValue() const;
	Key& getKey();
	Value& getValue();

	virtual Node<Key, Value>* getParent() const;
	virtual Node<Key, Value>* getLeft() const;
	virtual Node<Key, Value>* getRight() const;

	void setParent(Node<Key, Value>* parent);
	void setLeft(Node<Key, Value>* left);
	void setRight(Node<Key, Value>* right);
	void setValue(const Value &value);

protected:
	std::pair<Key, Value> mItem;
	Node<Key, Value>* mParent;
	Node<Key, Value>* mLeft;
	Node<Key, Value>* mRight;
};

/*
	-----------------------------------------
	Begin implementations for the Node class.
	-----------------------------------------
*/

/**
* Explicit constructor for a node.
*/
template<typename Key, typename Value>
Node<Key, Value>::Node(const Key& key, const Value& value, Node<Key, Value>* parent)
	: mItem(key, value)
	, mParent(parent)
	, mLeft(NULL)
	, mRight(NULL)
{

}

/**
* Destructor, which does not need to do anything since the pointers inside of a node
* are only used as references to existing nodes. The nodes pointed to by parent/left/right
* are freed within the deleteAll() helper method in the BinarySearchTree.
*/
template<typename Key, typename Value>
Node<Key, Value>::~Node()
{

}

/**
* A const getter for the item.
*/
template<typename Key, typename Value>
const std::pair<Key, Value>& Node<Key, Value>::getItem() const
{
	return mItem;
}

/**
* A non-const getter for the item.
*/
template<typename Key, typename Value>
std::pair<Key, Value>& Node<Key, Value>::getItem()
{
	return mItem;
}

/**
* A const getter for the key.
*/
template<typename Key, typename Value>
const Key& Node<Key, Value>::getKey() const
{
	return mItem.first;
}

/**
* A const getter for the value.
*/
template<typename Key, typename Value>
const Value& Node<Key, Value>::getValue() const
{
	return mItem.second;
}

/**
* A non-const getter for the key.
*/
template<typename Key, typename Value>
Key& Node<Key, Value>::getKey()
{
	return mItem.first;
}

/**
* A non-const getter for the value.
*/
template<typename Key, typename Value>
Value& Node<Key, Value>::getValue()
{
	return mItem.second;
}

/**
* An implementation of the virtual function for retreiving the parent.
*/
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getParent() const
{
	return mParent;
}

/**
* An implementation of the virtual function for retreiving the left child.
*/
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getLeft() const
{
	return mLeft;
}

/**
* An implementation of the virtual function for retreiving the right child.
*/
template<typename Key, typename Value>
Node<Key, Value>* Node<Key, Value>::getRight() const
{
	return mRight;
}

/**
* A setter for setting the parent of a node.
*/
template<typename Key, typename Value>
void Node<Key, Value>::setParent(Node<Key, Value>* parent)
{
	mParent = parent;
}

/**
* A setter for setting the left child of a node.
*/
template<typename Key, typename Value>
void Node<Key, Value>::setLeft(Node<Key, Value>* left)
{
	mLeft = left;
}

/**
* A setter for setting the right child of a node.
*/
template<typename Key, typename Value>
void Node<Key, Value>::setRight(Node<Key, Value>* right)
{
	mRight = right;
}

/**
* A setter for the value of a node.
*/
template<typename Key, typename Value>
void Node<Key, Value>::setValue(const Value& value)
{
	mItem.second = value;
}

/*
	---------------------------------------
	End implementations for the Node class.
	---------------------------------------
*/

/**
* A templated unbalanced binary search tree.
*/
template <typename Key, typename Value>
class BinarySearchTree
{
	public:
		BinarySearchTree();
		virtual ~BinarySearchTree();
  		virtual void insert(const std::pair<Key, Value>& keyValuePair);
        virtual void remove(const Key& key);
  		void clear();
  		void print() const;
  		bool isBalanced() const;

	public:
		/**
		* An internal iterator class for traversing the contents of the BST.
		*/
		class iterator
		{
			public:
				iterator(Node<Key,Value>* ptr);
				iterator();

				std::pair<Key,Value>& operator*() const;
				std::pair<Key,Value>* operator->() const;

				bool operator==(const iterator& rhs) const;
				bool operator!=(const iterator& rhs) const;
				iterator& operator=(const iterator& rhs);

				iterator& operator++();

			protected:
				Node<Key, Value>* mCurrent;

				friend class BinarySearchTree<Key, Value>;
		};

	public:
		iterator begin() const;
		iterator end() const;
		iterator find(const Key& key) const;

	protected:
		Node<Key, Value>* internalFind(const Key& key) const;
		Node<Key, Value>* getSmallestNode() const;
		void printRoot (Node<Key, Value>* root) const;

	protected:
		Node<Key, Value>* mRoot;

	public:
		void print() {this->printRoot(this->mRoot);}

	private:
		void insertHelper(const std::pair<Key, Value>& keyValuePair, Node<Key, Value>* root);
		Node<Key, Value>* findPred(const Key& key);
		bool isBalancedHelper(Node<Key, Value>* root) const;
		int height(Node<Key, Value>* root) const;
		void clearHelper(Node<Key, Value>* root);

};

/*
	---------------------------------------------------------------
	Begin implementations for the BinarySearchTree::iterator class.
	---------------------------------------------------------------
*/

/**
* Explicit constructor that initializes an iterator with a given node pointer.
*/
template<typename Key, typename Value>
BinarySearchTree<Key, Value>::iterator::iterator(Node<Key,Value>* ptr)
	: mCurrent(ptr)
{

}

/**
* A default constructor that initializes the iterator to NULL.
*/
template<typename Key, typename Value>
BinarySearchTree<Key, Value>::iterator::iterator()
	: mCurrent(NULL)
{

}

/**
* Provides access to the item.
*/
template<typename Key, typename Value>
std::pair<Key, Value>& BinarySearchTree<Key, Value>::iterator::operator*() const
{
	return mCurrent->getItem();
}

/**
* Provides access to the address of the item.
*/
template<typename Key, typename Value>
std::pair<Key, Value>* BinarySearchTree<Key, Value>::iterator::operator->() const
{
	return &(mCurrent->getItem());
}

/**
* Checks if 'this' iterator's internals have the same value
* as 'rhs'
*/
template<typename Key, typename Value>
bool BinarySearchTree<Key, Value>::iterator::operator==(const BinarySearchTree<Key, Value>::iterator& rhs) const
{
	return this->mCurrent == rhs.mCurrent;
}

/**
* Checks if 'this' iterator's internals have a different value
* as 'rhs'
*/
template<typename Key, typename Value>
bool BinarySearchTree<Key, Value>::iterator::operator!=(const BinarySearchTree<Key, Value>::iterator& rhs) const
{
	return this->mCurrent != rhs.mCurrent;
}

/**
* Sets one iterator equal to another iterator.
*/
template<typename Key, typename Value>
typename BinarySearchTree<Key, Value>::iterator &BinarySearchTree<Key, Value>::iterator::operator=(const BinarySearchTree<Key, Value>::iterator& rhs)
{
	this->mCurrent = rhs.mCurrent;
	return *this;
}

/**
* Advances the iterator's location using an in-order traversal.
*/
template<typename Key, typename Value>
typename BinarySearchTree<Key, Value>::iterator& BinarySearchTree<Key, Value>::iterator::operator++()
{
	if(mCurrent->getRight() != NULL)
	{
		mCurrent = mCurrent->getRight();
		while(mCurrent->getLeft() != NULL)
		{
			mCurrent = mCurrent->getLeft();
		}
	}
	else if(mCurrent->getRight() == NULL)
	{
		Node<Key, Value>* parent = mCurrent->getParent();
		while(parent != NULL && mCurrent == parent->getRight())
		{
			mCurrent = parent;
			parent = parent->getParent();
		}
		mCurrent = parent;
	}
	return *this;
}

/*
	-------------------------------------------------------------
	End implementations for the BinarySearchTree::iterator class.
	-------------------------------------------------------------
*/

/*
	-----------------------------------------------------
	Begin implementations for the BinarySearchTree class.
	-----------------------------------------------------
*/

/**
* Default constructor for a BinarySearchTree, which sets the root to NULL.
*/
template<typename Key, typename Value>
BinarySearchTree<Key, Value>::BinarySearchTree()
{
	mRoot = NULL;
}

template<typename Key, typename Value>
BinarySearchTree<Key, Value>::~BinarySearchTree()
{
	clear();
}

template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::print() const
{
	printRoot(mRoot);
	std::cout << "\n";
}

/**
* Returns an iterator to the "smallest" item in the tree
*/
template<typename Key, typename Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::begin() const
{
	BinarySearchTree<Key, Value>::iterator begin(getSmallestNode());
	return begin;
}

/**
* Returns an iterator whose value means INVALID
*/
template<typename Key, typename Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::end() const
{
	BinarySearchTree<Key, Value>::iterator end(NULL);
	return end;
}

/**
* Returns an iterator to the item with the given key, k
* or the end iterator if k does not exist in the tree
*/
template<typename Key, typename Value>
typename BinarySearchTree<Key, Value>::iterator BinarySearchTree<Key, Value>::find(const Key& key) const
{
	Node<Key, Value>* curr = internalFind(key);
	BinarySearchTree<Key, Value>::iterator it(curr);
	return it;
}

/**
* An insert method to insert into a Binary Search Tree. The tree will not remain balanced when
* inserting.
*/
template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::insert(const std::pair<Key, Value>& keyValuePair)
{
	insertHelper(keyValuePair, mRoot);
}

template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::insertHelper(const std::pair<Key, Value>& keyValuePair, Node<Key, Value>* root)
{
	if(root == NULL)
	{
		mRoot = new Node<Key, Value>(keyValuePair.first, keyValuePair.second, NULL);
		return;
	}
	if(root->getKey() == keyValuePair.first) root->setValue(keyValuePair.second);
	if(root->getLeft() == NULL && keyValuePair.first < root->getKey())
	{
		Node<Key, Value>* addNode = new Node<Key, Value>(keyValuePair.first, keyValuePair.second, root);
		root->setLeft(addNode);
		return;
	}
	if(root->getRight() == NULL && keyValuePair.first > root->getKey())
	{
		Node<Key, Value>* addNode = new Node<Key, Value>(keyValuePair.first, keyValuePair.second, root);
		root->setRight(addNode);
		return;
	}
	if(keyValuePair.first < root->getKey()) insertHelper(keyValuePair, root->getLeft());
	if(keyValuePair.first > root->getKey()) insertHelper(keyValuePair, root->getRight());
}

/**
* An remove method to remove a specific key from a Binary Search Tree. The tree may not remain balanced after
* removal.
*/
template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::remove(const Key& key)
{
	Node<Key, Value>* temp = internalFind(key);
	if(temp == NULL) return;
	if((temp == mRoot) && (mRoot->getLeft() == NULL) && (mRoot->getRight() == NULL))
	{
		delete mRoot;
		mRoot = NULL;
	}
	//no children
	else if(temp->getLeft() == NULL && temp->getRight() == NULL)
	{
		if(temp->getKey() < temp->getParent()->getKey()) temp->getParent()->setLeft(NULL);
		else temp->getParent()->setRight(NULL);
		delete temp;
		return;
	}
	//1 children
	else if(temp->getLeft() == NULL || temp->getRight() == NULL)
	{
		//add case for 1 child root node
		if(temp->getLeft() != NULL)
		{
			if(temp == mRoot)
			{
				temp->getLeft()->setParent(NULL);
				mRoot = temp->getLeft();
				delete temp;
				return;
			}
			if(temp->getParent()->getLeft() == temp) temp->getParent()->setLeft(temp->getLeft());
			else if(temp->getParent()->getRight() == temp) temp->getParent()->setRight(temp->getLeft());
			temp->getLeft()->setParent(temp->getParent());
			delete temp;
			return;
		}
		else if(temp->getRight() != NULL)
		{
			if(temp == mRoot)
			{
				temp->getRight()->setParent(NULL);
				mRoot = temp->getRight();
				delete temp;
				return;
			}
			if(temp->getParent()->getLeft() == temp) temp->getParent()->setLeft(temp->getRight());
			else if(temp->getParent()->getRight() == temp) temp->getParent()->setRight(temp->getRight());
			temp->getRight()->setParent(temp->getParent());
			delete temp;
			return;
		}
	}
	//2 children
	else
	{
		Node<Key, Value>* addNode = findPred(key);
		Node<Key, Value>* swapNode = new Node<Key, Value>(addNode->getKey(), addNode->getValue(), addNode->getParent());
		swapNode->setParent(temp->getParent());
		//assings children of replacement node. makes sure no self assinging
		if(addNode != temp->getLeft()) swapNode->setLeft(temp->getLeft());
		if(addNode != temp->getRight()) swapNode->setRight(temp->getRight());
		//makes sure newly assinged children point to correct parent
		if(temp->getLeft() != addNode) temp->getLeft()->setParent(swapNode);
		if(temp->getRight() != addNode) temp->getRight()->setParent(swapNode);
		//accounts for nodes left after swapnode that need to be pointed to and point to parents
		if(addNode->getLeft() != NULL && addNode->getParent() != temp)
		{
			addNode->getLeft()->setParent(addNode->getParent());
			if(addNode->getKey() > addNode->getParent()->getKey()) addNode->getParent()->setRight(addNode->getLeft());
			if(addNode->getKey() < addNode->getParent()->getKey()) addNode->getParent()->setLeft(addNode->getLeft());
		}
		//accounts for nodes left after swapnode that need to be pointed to and point to parents
		if(addNode->getRight() != NULL && addNode->getParent() != temp)
		{
			addNode->getRight()->setParent(addNode->getParent());
			if(addNode->getKey() > addNode->getParent()->getKey()) addNode->getParent()->setRight(addNode->getRight());
			if(addNode->getKey() < addNode->getParent()->getKey()) addNode->getParent()->setLeft(addNode->getRight());
		}
		if(temp == mRoot)
		{
			//if statement handles case when original assingment of children exlcudes case near root
			if(addNode->getParent() == temp && addNode == temp->getLeft())
			{
				swapNode->setLeft(addNode->getLeft());
				if(addNode->getLeft() != NULL) addNode->getLeft()->setParent(swapNode);
			}
			if(addNode->getParent() == temp && addNode == temp->getRight())
			{
				swapNode->setRight(addNode->getRight());
				if(addNode->getRight() != NULL) addNode->getRight()->setParent(swapNode);
			}
			//sets removed nodes parent to point to the nw swapnode
			else if(addNode->getKey() < addNode->getParent()->getKey()) addNode->getParent()->setLeft(addNode->getLeft());
			else if(addNode->getKey() > addNode->getParent()->getKey()) addNode->getParent()->setRight(addNode->getRight());
		}
		else
		{
			//if statement handles case when original assingment of children excludes case near root
			if(addNode->getParent() == temp && addNode == temp->getLeft())
			{
				swapNode->setLeft(addNode->getLeft());
				if(addNode->getLeft() != NULL) addNode->getLeft()->setParent(swapNode);
			}
			if(addNode->getParent() == temp && addNode == temp->getRight())
			{
				swapNode->setRight(addNode->getRight());
				if(addNode->getRight() != NULL) addNode->getRight()->setParent(swapNode);
			}
			//sets removed nodes parent to point to the new swapnode
			else if(temp->getKey() < temp->getParent()->getKey()) temp->getParent()->setLeft(swapNode);
			else if(temp->getKey() > temp->getParent()->getKey()) temp->getParent()->setRight(swapNode);
		}
		if(temp == mRoot) mRoot = swapNode;
		delete temp;
		delete addNode;
		return;
	}
}

template<typename Key, typename Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::findPred(const Key& key)
{
	Node<Key, Value>* temp = internalFind(key);
	Node<Key, Value>* p = temp->getParent();
	if(temp->getLeft() != NULL)
	{
		temp = temp->getLeft();
		while(temp->getRight() != NULL)
		{
			temp = temp->getRight();
		}
		return temp;
	}
	else
	{
		while(p != NULL && p->getLeft())
		{
			temp = p;
			p = p->getParent();
		}
	}
	return p;
}

/**
* A method to remove all contents of the tree and reset the values in the tree
* for use again.
*/
template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::clear()
{
	clearHelper(mRoot);
	mRoot = NULL;
}

template<typename Key, typename Value>
void BinarySearchTree<Key, Value>::clearHelper(Node<Key, Value>* root)
{
	if(root == NULL) return;
	clearHelper(root->getLeft());
	clearHelper(root->getRight());
	delete root;
}

/**
* A helper function to find the smallest node in the tree.
*/
template<typename Key, typename Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::getSmallestNode() const
{
	Node<Key, Value>* temp = mRoot;
	if(temp == NULL) return NULL;
	while(temp->getLeft() != NULL)
	{
		temp = temp->getLeft();
	}
	return temp;
}

/**
* Helper function to find a node with given key, k and
* return a pointer to it or NULL if no item with that key
* exists
*/
template<typename Key, typename Value>
Node<Key, Value>* BinarySearchTree<Key, Value>::internalFind(const Key& key) const
{
	Node<Key, Value>* temp = mRoot;
	while(temp != NULL)
	{
		if(temp->getKey() == key) return temp;
		else if(key < temp->getKey()) temp = temp->getLeft();
		else if(key > temp->getKey()) temp = temp->getRight();
	}
	return NULL;
}


/**
 * Return true iff the BST is an AVL Tree.
 */
template<typename Key, typename Value>
bool BinarySearchTree<Key, Value>::isBalanced() const
{
	return isBalancedHelper(mRoot);
}

template<typename Key, typename Value>
bool BinarySearchTree<Key, Value>::isBalancedHelper(Node<Key, Value>* root) const
{
	int lh, rh;
	if(root == NULL) return true;
	lh = height(root->getLeft());
	rh = height(root->getRight());
	if(abs(lh-rh) <= 1 && isBalancedHelper(root->getLeft()) && isBalancedHelper(root->getRight())) return true;
	return false;
}

template<typename Key, typename Value>
int BinarySearchTree<Key, Value>::height(Node<Key, Value>* root) const
{
	if(root == NULL) return 0;
	return 1 + std::max(height(root->getLeft()), height(root->getRight()));
}



/**
 * Lastly, we are providing you with a print function, BinarySearchTree::printRoot().
   Just call it with a node to start printing at, e.g:
   this->printRoot(this->mRoot)

   It will print up to 5 levels of the tree rooted at the passed node, in ASCII graphics format.
   We hope it will make debugging easier!
  */

// include print function (in its own file because it's fairly long)
#include "print_bst.h"

/*
	---------------------------------------------------
	End implementations for the BinarySearchTree class.
	---------------------------------------------------
*/

#endif
