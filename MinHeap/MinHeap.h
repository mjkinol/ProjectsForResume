#include <iostream>
#include <vector>

template <class T>
  class MinHeap {
     public:
       MinHeap (int d);
       /* Constructor that builds a d-ary Min Heap
          This should work for any d >= 2,
          but doesn't have to do anything for smaller d.*/

       ~MinHeap ();

       void add (T item, int priority);
         /* adds the item to the heap, with the given priority. */

       const T & peek () const;
         /* returns the element with smallest priority.  
            Break ties however you wish.  
            Throws an exception if the heap is empty. */

       void remove ();
         /* removes the element with smallest priority.
            Break ties however you wish.
            Throws an exception if the heap is empty. */

       bool isEmpty ();
         /* returns true iff there are no elements on the heap. */

   private:
      std::vector<T> nodes;
      std::vector<int> priorVec;
      int arySize;
      // whatever you need to naturally store things.
      // You may also add helper functions here.
      void moveUp(int loc);
      void heapify(int index);
  };

  template <class T>
  MinHeap<T>::MinHeap(int d)
  {
    arySize = d;
  }

  template <class T>
  MinHeap<T>::~MinHeap()
  {
  }

  template <class T>
  const T& MinHeap<T>::peek () const
  {
    if(nodes.empty()) throw std::logic_error("Empty heap. Can't peek.");
    return nodes[0];
  }

  template <class T>
  void MinHeap<T>::add(T item, int priority)
  {
    nodes.push_back(item);
    priorVec.push_back(priority);
    moveUp(int(nodes.size()-1));
  }

  template <class T>
  void MinHeap<T>::moveUp(int loc)
  {
    int parent = (loc-1)/arySize;
    while(parent >= 0 && priorVec[loc] < priorVec[parent])
    {
      //swap priorities
      int temp;
      temp = priorVec[loc];
      priorVec[loc] = priorVec[parent];
      priorVec[parent] = temp;
      //swap nodes
      T temp2;
      temp2 = nodes[loc];
      nodes[loc] = nodes[parent];
      nodes[parent] = temp2;

      loc = parent;
      parent = (loc-1)/arySize;
    }
  }

  template <class T>
  void MinHeap<T>::remove()
  {
    if(nodes.empty()) throw std::logic_error("Empty heap. Can't remove.");;
    nodes[0] = nodes.back();
    nodes.pop_back();
    priorVec[0] = priorVec.back();
    priorVec.pop_back();
    heapify(0);
  }

  template <class T>
  void MinHeap<T>::heapify(int index)
  {
    std::vector<int> child;
    int smallestChild;
    while((arySize*(index))+1 < int(nodes.size()))
    {
      smallestChild = (arySize*index)+1;
      for(int i = 2; i <= arySize; i++)
      {
        if((arySize*index)+i >= (int)nodes.size()) break;
        else
        {
          child.push_back((arySize*index)+i);
        }
      }
      for(unsigned int x = 0; x < child.size(); x++)
      {
        if(priorVec[child[x]] < priorVec[smallestChild])
        {
          smallestChild = child[x];
        }
      }
      if(priorVec[index] > priorVec[smallestChild])
      {
        int temp;
        temp = priorVec[index];
        priorVec[index] = priorVec[smallestChild];
        priorVec[smallestChild] = temp;
        T temp2;
        temp2 = nodes[index];
        nodes[index] = nodes[smallestChild];
        nodes[smallestChild] = temp2;
        child.clear();
        index = smallestChild;
      }
      else return;
    }
  }

  template <class T>
  bool MinHeap<T>::isEmpty ()
  {
    return nodes.empty();
  }
