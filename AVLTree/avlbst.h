#ifndef AVLBST_H
#define AVLBST_H

#include <iostream>
#include <cstdlib>
#include <string>
#include "rotateBST.h"

/**
* A special kind of node for an AVL tree, which adds the height as a data member, plus 
* other additional helper functions. You do NOT need to implement any functionality or
* add additional data members or helper functions.
*/
template <typename Key, typename Value>
class AVLNode : public Node<Key, Value>
{
public:
	// Constructor/destructor.
    AVLNode(const Key& key, const Value& value, AVLNode<Key, Value>* parent);
    virtual ~AVLNode();

    // Getter/setter for the node's height.
    int getHeight() const;
    void setHeight(int height);

    // Getters for parent, left, and right. These need to be redefined since they 
    // return pointers to AVLNodes - not plain Nodes. See the Node class in bst.h
    // for more information.
    virtual AVLNode<Key, Value>* getParent() const override;
    virtual AVLNode<Key, Value>* getLeft() const override;
    virtual AVLNode<Key, Value>* getRight() const override;

protected:
    int mHeight;
};

/*
--------------------------------------------
Begin implementations for the AVLNode class.
--------------------------------------------
*/

/**
* Constructor for an AVLNode. Nodes are initialized with a height of 0.
*/
template<typename Key, typename Value>
AVLNode<Key, Value>::AVLNode(const Key& key, const Value& value, AVLNode<Key, Value>* parent)
    : Node<Key, Value>(key, value, parent)
    , mHeight(0)
{

}

/**
* Destructor.
*/
template<typename Key, typename Value>
AVLNode<Key, Value>::~AVLNode()
{

}

/**
* Getter function for the height. 
*/
template<typename Key, typename Value>
int AVLNode<Key, Value>::getHeight() const
{
    return mHeight;
}

/**
* Setter function for the height. 
*/
template<typename Key, typename Value>
void AVLNode<Key, Value>::setHeight(int height)
{
    mHeight = height;
}

/**
* Getter function for the parent. Used since the node inherits from a base node.
*/
template<typename Key, typename Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getParent() const
{
    return static_cast<AVLNode<Key,Value>*>(this->mParent);
}

/**
* Getter function for the left child. Used since the node inherits from a base node.
*/
template<typename Key, typename Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getLeft() const
{
    return static_cast<AVLNode<Key,Value>*>(this->mLeft);
}

/**
* Getter function for the right child. Used since the node inherits from a base node.
*/
template<typename Key, typename Value>
AVLNode<Key, Value>* AVLNode<Key, Value>::getRight() const
{
    return static_cast<AVLNode<Key,Value>*>(this->mRight);
}

/*
------------------------------------------
End implementations for the AVLNode class.
------------------------------------------
*/

/**
* A templated balanced binary search tree implemented as an AVL tree.
*/
template <class Key, class Value>
class AVLTree : public rotateBST<Key, Value>
{
public:
	// Methods for inserting/removing elements from the tree. You must implement
	// both of these methods. 
    virtual void insert(const std::pair<Key, Value>& keyValuePair) override;
    void remove(const Key& key);

private:
	/* Helper functions are strongly encouraged to help separate the problem
	   into smaller pieces. You should not need additional data members. */
    void AVLrebalance(AVLNode<Key, Value>* node);
    void insertHelper(const std::pair<Key, Value>& keyValuePair, AVLNode<Key, Value>* root);
    AVLNode<Key, Value>* findPred(const Key& key);
    AVLNode<Key, Value>* internalFind(const Key& key) const;
    int maxHeight(int first, int sec);
    void updateHeight(AVLNode<Key, Value>* root);
};

/*
--------------------------------------------
Begin implementations for the AVLTree class.
--------------------------------------------
*/

/**
* Insert function for a key value pair. Finds location to insert the node and then balances the tree. 
*/
template<typename Key, typename Value>
void AVLTree<Key, Value>::AVLrebalance(AVLNode<Key, Value>* node)
{
    if(node == NULL) return;
    AVLNode<Key, Value>* root = dynamic_cast<AVLNode<Key,Value>*>(this->mRoot);
    while(node != NULL)
    {
        updateHeight(root); 
        if(node->getLeft() != NULL && node->getRight() != NULL)
        {
            if(node->getLeft()->getHeight() >= 2 + node->getRight()->getHeight())
            {
                if(node->getLeft()->getLeft() != NULL && node->getLeft()->getRight() != NULL)
                {
                    if(node->getLeft()->getLeft()->getHeight() >= node->getLeft()->getRight()->getHeight())
                    {
                        this->rightRotate(node);
                    }
                    else
                    {
                        this->leftRotate(node->getLeft());
                        this->rightRotate(node); 
                    }
                }
                else if((node->getLeft()->getLeft() != NULL && node->getLeft()->getRight() == NULL))
                {
                    this->rightRotate(node);
                }
                else if(node->getLeft()->getLeft() == NULL && node->getLeft()->getRight() != NULL)
                {
                    this->leftRotate(node->getLeft());
                    this->rightRotate(node);
                }

            }
            else if(node->getRight()->getHeight() >= 2 + node->getLeft()->getHeight())
            {
                if(node->getRight()->getRight() != NULL && node->getRight()->getLeft() != NULL)
                {
                    if(node->getRight()->getRight()->getHeight() >= node->getRight()->getLeft()->getHeight())
                    {
                        this->leftRotate(node);
                    }
                    else
                    {
                        this->rightRotate(node->getRight());
                        this->leftRotate(node);
                    }
                }
                else if(node->getRight()->getRight() != NULL && node->getRight()->getLeft() == NULL)
                {
                    this->leftRotate(node);
                }
                else if(node->getRight()->getRight() == NULL && node->getRight()->getLeft() != NULL)
                {
                    this->rightRotate(node->getRight());
                    this->leftRotate(node);
                }
                else
                {
                    this->rightRotate(node->getRight());
                    this->leftRotate(node);
                }
            }
        }
        else if(node->getLeft() == NULL && node->getRight() != NULL)
        {
            if(node->getLeft() == NULL && node->getRight()->getHeight() >= 2)
            {
                if(node->getRight()->getRight() != NULL && node->getRight()->getLeft() == NULL)
                {
                    this->leftRotate(node);
                }
                else if(node->getRight()->getRight() == NULL && node->getRight()->getLeft() != NULL)
                {
                    this->rightRotate(node->getRight());
                    this->leftRotate(node);
                }
                else if(node->getRight()->getRight() != NULL && node->getRight()->getLeft() != NULL)
                {
                    if(node->getRight()->getRight()->getHeight() >= node->getRight()->getLeft()->getHeight())
                    {
                        this->leftRotate(node);
                    }
                    else
                    {
                        this->rightRotate(node->getRight());
                        this->leftRotate(node);
                    }
                }
                
            }
        }
        else if(node->getRight() == NULL && node->getLeft() != NULL)
        {
            if(node->getRight() == NULL && node->getLeft()->getHeight() >= 2)
            {
                if(node->getLeft()->getLeft() != NULL && node->getLeft()->getRight() == NULL)
                {
                    this->rightRotate(node);
                }
                else if(node->getLeft()->getLeft() == NULL && node->getLeft()->getRight() != NULL)
                {
                    this->leftRotate(node->getLeft());
                    this->rightRotate(node);
                }
                else if(node->getLeft()->getLeft() != NULL && node->getLeft()->getRight() != NULL)
                {
                    if(node->getLeft()->getLeft()->getHeight() >= node->getLeft()->getRight()->getHeight())
                    {
                        this->rightRotate(node);
                    }
                    else
                    {
                        this->leftRotate(node->getLeft());
                        this->rightRotate(node); 
                    }
                }
            }
        }
        node = node->getParent();    
    }
}

template<typename Key, typename Value>
void AVLTree<Key, Value>::insert(const std::pair<Key, Value>& keyValuePair)
{
    AVLNode<Key, Value>* avltemp = dynamic_cast<AVLNode<Key,Value>*>(this->mRoot);
    insertHelper(keyValuePair, avltemp);
    AVLNode<Key, Value>* checkBal = internalFind(keyValuePair.first);
    AVLrebalance(checkBal);
}

template<typename Key, typename Value>
void AVLTree<Key, Value>::insertHelper(const std::pair<Key, Value>& keyValuePair, AVLNode<Key, Value>* root)
{
    if(root == NULL)
    {
        this->mRoot = new AVLNode<Key, Value>(keyValuePair.first, keyValuePair.second, NULL);
        dynamic_cast<AVLNode<Key,Value>*>(this->mRoot)->setHeight(1);
        return;
    }
    if(root->getKey() == keyValuePair.first)
    {
        root->setValue(keyValuePair.second);
    }
    else if(root->getLeft() == NULL && keyValuePair.first < root->getKey())
    {
        AVLNode<Key, Value>* addNode = new AVLNode<Key, Value>(keyValuePair.first, keyValuePair.second, root);
        root->setLeft(addNode);
    }
    else if(root->getRight() == NULL && keyValuePair.first > root->getKey())
    {
        AVLNode<Key, Value>* addNode = new AVLNode<Key, Value>(keyValuePair.first, keyValuePair.second, root);
        root->setRight(addNode);
    }
    if(keyValuePair.first < root->getKey()) insertHelper(keyValuePair, root->getLeft());
    if(keyValuePair.first > root->getKey()) insertHelper(keyValuePair, root->getRight());
}

/**
* Remove function for a given key. Finds the node, reattaches pointers, and then balances when finished. 
*/
template<typename Key, typename Value>
void AVLTree<Key, Value>::remove(const Key& key)
{
    AVLNode<Key, Value>* temp = internalFind(key);
    if(temp == NULL) return;
    if((temp == this->mRoot) && (this->mRoot->getLeft() == NULL) && (this->mRoot->getRight() == NULL))
    {
        delete this->mRoot;
        this->mRoot = NULL;
    }
    //no children
    else if(temp->getLeft() == NULL && temp->getRight() == NULL)
    {
        if(temp->getKey() < temp->getParent()->getKey()) temp->getParent()->setLeft(NULL);
        else temp->getParent()->setRight(NULL);
        AVLrebalance(temp->getParent());
        delete temp;
        return;
    }
    //1 children
    else if(temp->getLeft() == NULL || temp->getRight() == NULL)
    {
        //add case for 1 child root node
        if(temp->getLeft() != NULL)
        {
            if(temp == this->mRoot)
            {
                temp->getLeft()->setParent(NULL);
                this->mRoot = temp->getLeft();
                AVLrebalance(temp->getLeft());
                delete temp;
                return;
            }
            if(temp->getParent()->getLeft() == temp) temp->getParent()->setLeft(temp->getLeft());
            else if(temp->getParent()->getRight() == temp) temp->getParent()->setRight(temp->getLeft());
            temp->getLeft()->setParent(temp->getParent());
            AVLrebalance(temp->getLeft());
            delete temp;
            return;
        }
        else if(temp->getRight() != NULL)
        {
            if(temp ==this->mRoot)
            {
                temp->getRight()->setParent(NULL);
                this->mRoot = temp->getRight();
                AVLrebalance(temp->getRight());
                delete temp;
                return;
            }
            if(temp->getParent()->getLeft() == temp) temp->getParent()->setLeft(temp->getRight());
            else if(temp->getParent()->getRight() == temp) temp->getParent()->setRight(temp->getRight());
            temp->getRight()->setParent(temp->getParent());
            AVLrebalance(temp->getRight());
            delete temp;
            return;
        }
    }
    //2 children
    else
    {
        AVLNode<Key, Value>* addNode = findPred(key);
        AVLNode<Key, Value>* swapNode = new AVLNode<Key, Value>(addNode->getKey(), addNode->getValue(), addNode->getParent());
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
        if(temp == this->mRoot)
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
        if(temp == this->mRoot) this->mRoot = swapNode;
        delete temp;
        delete addNode;
        AVLrebalance(swapNode);
        return;
    }
}
template<typename Key, typename Value>
AVLNode<Key, Value>* AVLTree<Key, Value>::findPred(const Key& key)
{
    AVLNode<Key, Value>* temp = internalFind(key);
    AVLNode<Key, Value>* p = temp->getParent();
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

template<typename Key, typename Value>
AVLNode<Key, Value>* AVLTree<Key, Value>::internalFind(const Key& key) const
{
    AVLNode<Key, Value>* temp = dynamic_cast<AVLNode<Key,Value>*>(this->mRoot);
    while(temp != NULL)
    {
        if(temp->getKey() == key) return temp;
        else if(key < temp->getKey()) temp = temp->getLeft();
        else if(key > temp->getKey()) temp = temp->getRight();
    }
    return NULL;
}

template<typename Key, typename Value>
void AVLTree<Key, Value>::updateHeight(AVLNode<Key, Value>* root)
{
    if(root == NULL)
    {
        return;
    }
    else
    {
        updateHeight(root->getLeft());
        updateHeight(root->getRight());
        int lheight;
        int rheight;
        if(root->getLeft() != NULL) lheight = root->getLeft()->getHeight();
        else lheight = 0;
        if(root->getRight() != NULL) rheight = root->getRight()->getHeight();
        else rheight = 0;
        root->setHeight(1 + maxHeight(lheight, rheight));
    }

}

template<typename Key, typename Value>
int AVLTree<Key, Value>::maxHeight(int first, int sec)
{
    if(first <= sec) return sec;
    else return first;
}




/*
------------------------------------------
End implementations for the AVLTree class.
------------------------------------------
*/

#endif
