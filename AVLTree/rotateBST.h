#include "bst.h"

template <typename Key, typename Value>
class rotateBST : public BinarySearchTree<Key, Value>
{

	public:
		rotateBST();
		~rotateBST();
		bool sameKeys(const rotateBST& t2) const;
		void transform(rotateBST& t2) const;

	protected:
		void leftRotate(Node<Key, Value>* r);
		void rightRotate(Node<Key, Value>* r);

	private:
		void inOrder(Node<Key, Value>* node, std::vector<Key>& v) const;
		void transformHelper(Node<Key, Value>* root, rotateBST& t2) const;
		void transformHelperChild(Node<Key, Value>* root, Node<Key, Value>* root2, rotateBST& t2) const;
};

//constructor
template <typename Key, typename Value>
rotateBST<Key, Value>::rotateBST() 
{
}

template <typename Key, typename Value>
rotateBST<Key, Value>::~rotateBST()
{
}

//FIGURE OUT HOW TO DO THIS CHECKING REGARDLESS OF STRUCTURE
template <typename Key, typename Value>
bool rotateBST<Key, Value>::sameKeys(const rotateBST& t2) const
{
	std::vector<Key> v1;
	std::vector<Key> v2;
	inOrder(t2.mRoot, v1);
	inOrder(this->mRoot, v2);
	if(v1.size() != v2.size()) return false;
	for(unsigned int i = 0; i < v1.size(); i++)
	{
		if(v1[i] != v2[i]) return false;
	}
	return true;
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::inOrder(Node<Key, Value>* node, std::vector<Key>& v) const
{
	if(node == NULL) return;
	inOrder(node->getLeft(), v);
	v.push_back(node->getKey());
	inOrder(node->getRight(), v);
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::transformHelper(Node<Key, Value>* root, rotateBST& t2) const
{
	if(root == NULL) return;
	while(root->getLeft() != NULL)
	{
		t2.rightRotate(root);
		root = root->getParent();
	}
	transformHelper(root->getRight(), t2);
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::transformHelperChild(Node<Key, Value>* root, Node<Key, Value>* root2, rotateBST& t2) const
{
	if(root == NULL) return;
	if(root->getRight() == NULL && root->getLeft() == NULL) return;
	while(root->getRight() != NULL && root->getKey() != root2->getKey())
	{
		t2.leftRotate(root);
		root = root->getParent();
	}
	while(root->getLeft() != NULL && root->getKey() != root2->getKey())
	{
		t2.rightRotate(root);
		root = root->getParent();
	}
	transformHelperChild(root->getLeft(), root2->getLeft(), t2);
	transformHelperChild(root->getRight(), root2->getRight(), t2);
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::transform(rotateBST& t2) const
{
	if(!sameKeys(t2)) return;
	transformHelper(t2.mRoot, t2);
	Node<Key, Value>* node = t2.mRoot;
	while(node->getKey() != this->mRoot->getKey())
	{
		t2.leftRotate(node);
		node = node->getParent();
	}
	transformHelperChild(t2.mRoot->getRight(), this->mRoot->getRight(), t2);
	transformHelperChild(t2.mRoot->getLeft(), this->mRoot->getLeft(), t2);
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::rightRotate(Node<Key, Value>* r)
{
	Node<Key, Value>* tempLNode;
	//checks that left child exists
	if(r->getLeft() == NULL) return;
	//sets new root nodes parent to previous root nodes parent
	r->getLeft()->setParent(r->getParent());
	//make sure new root new parent points back
	if(r->getParent() != NULL)
	{
		if(r->getParent()->getKey() > r->getKey())  r->getParent()->setLeft(r->getLeft());
		else  r->getParent()->setRight(r->getLeft());
	}
	//set old root new parent to new root
	r->setParent(r->getLeft());
	//set branch node to have proper new parent
	if(r->getLeft()->getRight() != NULL) r->getLeft()->getRight()->setParent(r);
	//Need to temporarily preserve node or new root node
	tempLNode = r->getLeft();
	//left node of the previous root is now new root old right node 
	r->setLeft(r->getLeft()->getRight());
	//set new root node to have a new right pointer to old root
	//need preserved node to be able to set this after last line of code
	tempLNode->setRight(r);
	//if we rotated on mRoot node, the new rootnode is the previous left node
	if(r == this->mRoot) this->mRoot = tempLNode;
}

template <typename Key, typename Value>
void rotateBST<Key, Value>::leftRotate(Node<Key, Value>* r)
{
	Node<Key, Value>* tempRNode;
	//checks that left child exists
	if(r->getRight() == NULL) return;
	//sets new root nodes parent to previous root nodes parent
	r->getRight()->setParent(r->getParent());
	//make sure new root new parent points back
	if(r->getParent() != NULL)
	{
		if(r->getParent()->getKey() > r->getKey())  r->getParent()->setLeft(r->getRight());
		else  r->getParent()->setRight(r->getRight());
	}
	//set old root new parent to new root
	r->setParent(r->getRight());
	//set branch node to have proper new parent
	if(r->getRight()->getLeft() != NULL) r->getRight()->getLeft()->setParent(r);
	//Need to temporarily preserve node or new root node
	tempRNode = r->getRight();
	//left node of the previous root is now new root old right node 
	r->setRight(r->getRight()->getLeft());
	//set new root node to have a new right pointer to old root
	//need preserved node to be able to set this after last line of code
	tempRNode->setLeft(r);
	//if we rotated on mRoot node, the new rootnode is the previous left node
	if(r == this->mRoot) this->mRoot = tempRNode;
}