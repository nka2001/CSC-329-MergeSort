/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m05mergesort;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.*; // for cloning
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 *
 * @author gerstl This is somewhat based on the code from Open Data Structures
 * by Pat Morin, Java Edition but with lots of my modifications
 * @param <T>
 */
//public class DLList<T extends Comparable<T> & Cloneable> implements SortTestable<T> , Iterable<T>, Cloneable  {
public class DLList<T extends Comparable<T>> implements SortTestable<T>, Iterable<T> {

    class Node<T> {

        T data;
        Node<T> next;
        Node<T> previous;

    }

    int n; // number of elements
    Node<T> dummy;

    /**
     * default ctor
     */
    public DLList() {

        n = 0;
        dummy = new Node<T>();
        dummy.next = dummy;
        dummy.previous = dummy;

        // remember to create dummy
    }

    /**
     * Copy ctor
     *
     * @param copyMe the list to copy
     */
    public DLList(DLList copyMe) {
        DLList<T> other = copyMe.cloneIsh();
        dummy = other.dummy;
        n = other.n;
    }

    /**
     *
     * @return String representation of the list. Note that this uses the
     * iterator, so that must be implemented. Format returned mirrors that
     * returned by ArrayList<>.toString()
     */
    @Override
    public String toString() {
        Iterator<T> iter = iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Produces a possible deep copy. See
     * https://www.baeldung.com/java-deep-copy and
     * https://stackoverflow.com/questions/1138769/why-is-the-clone-method-protected-in-java-lang-object
     * for some flavor on this. NOTE THAT THIS IS NOT REALLY TESTED WELL
     *
     * @return Another DLList with the same items
     */
    public DLList<T> cloneIsh() {
        DLList<T> rv = new DLList<>();
        Iterator<T> otherIterator = iterator();
        // See the first article above
        Gson gson = new Gson();
        while (otherIterator.hasNext()) {
            // O(1) operation
            T otherItem = otherIterator.next();
            Type typeOfT = new TypeToken<T>() {
            }.getType();
            T otherItemCopy = gson.fromJson(gson.toJson(otherItem), typeOfT);
            rv.append(otherItemCopy);
        }
        return rv;
    }

    /**
     *
     * @return an iterator implementing next() and hasNext(). Extra credit for
     * implementing remove() [see the assignment sheet]
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterRv; // anon inner class iterator
        iterRv = new Iterator<T>() {

            int position = -1;//position is used to track where the iterator is 
            Node<T> current = dummy.next;//same as head? 

            @Override
            public boolean hasNext() {
                return current != null;//if current isnt null, then there is a next in the linked list 
            } // hasNext()

            @Override
            public T next() {

                if (!hasNext()) {//so long as there is a next then...

                    throw new NoSuchElementException();

                } else {
                T data = current.data;//saves the current nodes data
                current = current.next;//advances the node through the list
                position++;//position increases as the iterator calls next
                return data;//data is returned 
                }
            } // next()

            // note that remove removes the prior element (i.e., you must
            // call next() first, then remove will remove the element that 
            // next pointed to in that prior call
            @Override
            public void remove() {
                // For student version, add exception
                if (!hasNext()) {
                    throw new IllegalStateException();
                } else {

                    next();//calls next to remove the previous element 
                    DLList.this.removeAt(position - 1);//calls removeAt, position is the count where the iterator is at
                    position--;//position is decremented 

                    current = current.previous;//pulls the iterator back, so it is at the correct position after removal 
                }
                //throw new UnsupportedOperationException();
                // make sure there is an element to move to 
                // (next() must have been called)
                // otherwise, throw new IllegalStateException();
            } // remove()
        };
        return iterRv;
    }

    /**
     *
     * @return true iff the item is in the list
     */
    public boolean contains(T findMe) {
        return (-1 != indexOf(findMe));
    }

    /**
     *
     * @return first index of item in the list, -1 if not found. Note that the
     * first actual item (not dummy) is 0.
     */
    public int indexOf(T findMe) {

        Node<T> findIndex = dummy.next;//create a new node
        findIndex.data = dummy.next.data;//set the data of the node to the heads data

        int index = -1;//index is -1, since if findMe is not found, -1 is returned 

        for (int i = 0; i < n; i++) {//for loop iterates throgh the list until...
            if (findMe.equals(findIndex.data)) {//if the findMe equals the nodes data then...
                index = i;//index is set to i
                return index;//index is returned 
            }
            findIndex = findIndex.next;//the node is advanced through the linked list 
        }
        return index;//otherwise, -1 is returned
    }

    /**
     *
     * @return last index of item in the list, -1 if not found. Note that the
     * first actual item (not dummy) is 0.
     */
    public int lastIndexOf(T findMe) {

        Node<T> findLast = dummy.previous;//similar to indexOf except starting from the back of the list, since the first occurence from the rear of the list is also the last occurence starting from the front
        findLast.data = dummy.previous.data;//data is set to the "tail"
        int index = -1;

        for (int i = n - 1; i > 0; i--) {//for loop for determining index
            if (findMe.equals(findLast.data)) {//if findMe equals the nodes data, then...
                index = i;//index is set to i
                return index;//index is returned 
            }
            findLast = findLast.previous;//node is advanced through the linked list
        }
        return index; //otherwise, -1 is returned 
    }

    /**
     *
     * @return true iff the item was found and removed
     */
    public boolean remove(T removeMe) {

        Node<T> removeNode = dummy.next;//new node set to head
        removeNode.data = dummy.next.data;//data set to head

        if (removeNode.data == null) {//if the data in the node is null then nothing happens
            return false;
        }
        if (removeNode.data.equals(removeMe)) {//if the data of the node equals the removeMe, then...

            n--;//the size of the list is decremented 
            removeNode = removeNode.next;//the node is advanced 

            return true;
        }
        int index = indexOf(removeMe);//finds the index of removeMe

        if (index > 0) {//if the index is bigger than 0,
            removeAt(index);//the removeAt method removes the data
            return true;
        } else {

            return false;
        }
    }

    // Internal method to get a node
    /**
     *
     * @return The data at position. Note that the first actual item (not dummy)
     * is 0.
     */
    @Override
    public T getItemAt(int position) {

        Node<T> test = dummy.next;//new node is set to head

        test.data = dummy.next.data;//data set to heads data
        T nodeData = test.data;

        for (int i = 0; i <= n - 1; i++) {

            if (i == position) {//if i == the position passed then, 

                nodeData = test.data;//the data is set 
            }
            test = test.next;

        }

        return nodeData;//nodeData is returned 

    }

    /**
     *
     * Changes the data stored in the existing item at position (undefined if
     * that item doesn't exist)
     *
     * @return The data previously stored at that position
     */
    public T setDataInNodeAt(int position, T newData) {

        Node<T> addData = dummy.next;//new node set the head 
        addData.data = dummy.next.data;//new node data is set to head data

        T oldData = getItemAt(position);//gets the old data, since thats whats returned 

        for (int i = 0; i <= n - 1; i++) {
            if (i == position) {//if i equals the position, 
                addData.data = newData;//addData is set to the new data given
            }
            addData = addData.next;//advance the iterator 
        }

        return oldData;//the OLD data is returned from this method 
    }

    /**
     *
     * @return The number of items in the list
     */
    public int size() {
        return n;
    }

    // required by SortTestable. 
    public int getCount() {
        return size();
    }

    /**
     * Adds a new data item so it will be at position (displacing all later
     * items).
     *
     * @return true if the item was added (you can't add a node to position
     * "position" if that position will not exist when you add the item
     */
    public boolean addAt(int position, T addMe) {
        
        Node<T> addingNode = new Node<T>();//new node is created 

        addingNode.data = addMe;//data is set 
        Node<T> replacement;//a replacement node is created to temp. hold the data 
        replacement = getNode(position);//replacement node calls the getNode method given the position given 

        addingNode.next = replacement;//pointer placement 
        addingNode.previous = replacement.previous;//pointer placement 
        replacement.previous = addingNode;//pointer placement 
        if (addingNode.previous != null) {//if the previous != null then
            addingNode.previous.next = addingNode;//pointer placement 
        }

        n++;//size of the list is incremented 

        if (getNode(position).data.equals(addMe)) {//final if check, to see if the item is added into the list 
            return true;
        }

        return false;

    }

    public Node<T> getNode(int position) {//comes from open algorithms (P. 68)
        Node<T> current = dummy;
        if (position < n / 2) {
            current = dummy.next;
            for (int i = 0; i < position; i++) {
                current = current.next;
            }
        } else {
            current = dummy;
            for (int i = n; i > position; i--) {
                current = current.previous;
            }
        }
        return current;

    }

    /**
     *
     * Remove the item at position
     *
     * @return The data previously stored at that position (null if not
     * successful)
     */
    public T removeAt(int position) {
        Node<T> removeMe = getNode(position);//another method for removing, by positon (USED BY REMOVE())

        T data = removeMe.data;//data is set to node data 

        removeMe.previous.next = removeMe.next;//pointer placement

        if (removeMe.next != null) {//if the pointer to next is not null, then 
            removeMe.next.previous = removeMe.previous;//more pointer placement 
        }

        n--;//n is decremented, since the size goes down 

        return data;

    }

    // all this should be the MERGESORT assignment. AddAtTail, equals (arrayList)
    // added by me for sorting. 
    public void append(T addMe) {

        Node<T> addBack = new Node<T>();//create a new node 
        addBack.data = addMe;//new nodes data is set to addMe
        addBack.previous = dummy.previous;//pointer placement 
        dummy.previous.next = addBack;//pointer placement 
        dummy.previous = addBack;//pointer placement 
        n++;//n is increased 

    }

    /**
     *
     * add to the beginning of the list
     *
     */
    public void prepend(T addMe) {

        Node<T> addFront = new Node<T>();//create a new node 
        addFront.data = addMe;//pointer placement 
        addFront.next = dummy.next;//pointer placement
        dummy.next.previous = addFront;//pointer placement 
        dummy.next = addFront;//pointer placement 
        n++;//n is increased 

    }

    /**
     *
     * Note that this requires working iterators
     *
     * @param compareWith the ArrayList to compare with
     * @return true iff the ArrayList and DLL contain the same data in the same
     * order
     */
    public boolean sameContentsAs(ArrayList<T> compareWith) {
        // O(1) check--if they are not hte same length, they are not 
        // the same
        if (compareWith.size() != n) {
            return false;
        }
        Iterator<T> myIterator = iterator();
        Iterator<T> cwIterator = compareWith.iterator();
        // now element by element
        // note that we start at the pre- for cwIterator, but in the element
        // for llIterator. We need to do this to process a 1 element correctly
        while (myIterator.hasNext() && cwIterator.hasNext()) {
            // get and advance the cw iterator
            T cwElement = cwIterator.next();
            T llElement = myIterator.next();
            if (!cwElement.equals(llElement)) {
                return false;
            }
        }
        // if we get here, we found no differences
        return true;
    }

    /**
     *
     * Note that this requires working iterators
     *
     * @param compareWith the DLList to compare with
     * @return true iff both us and the param contain the same data in the same
     * order
     */
    public boolean sameContentsAs(DLList<T> otherDll) {

        if (n != otherDll.size()) {
            return false;
        }
        Iterator<T> myDLL = iterator();
        Iterator<T> otherDLL = otherDll.iterator();

        while (myDLL.hasNext() && otherDLL.hasNext()) {
            T myElement = myDLL.next();
            T otherElement = otherDLL.next();

            if (!otherElement.equals(myElement)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return an integer hashcode
     */
    @Override
    public int hashCode() {
        // just hash the data.
        int rv = 0;
        Iterator i = iterator();
        while (i.hasNext()) {
            // note that this ideally should account for position, but 
            // this does meet the requirements that
            // if a.equals(b), then a.hashCode()==b.hashCode()
            rv += i.next().hashCode();
        }
        // negative values are possible since rv is a 32 bit twos-complement
        // int and since this can overflow safely
        return rv;
    }

    /**
     *
     * @param obj object to check if we are equal
     * @return true iff both are DLList holding the same data
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DLList<?> other = (DLList<?>) obj;
        if (this.n != other.n) {
            return false;
        }
        return sameContentsAs((DLList<T>) other);
    }

    /**
     * This is for the Mergesort Module (5) only. Implement mergesort on the
     * list
     */
    
    
   
    @Override
    public void sort() {
        // do not implement this. This is M5
        
        
        
      Node<T> split1 = splitList();
      mergeSort(split1);
       
          
        
    }
    
    private Node<T> splitList(){
        
       Node<T> slow = dummy.next;
       slow.data = dummy.next.data;
       Node<T> fast = dummy.next.next;
       fast.data = dummy.next.next.data;
       
       
       while(fast.next != null && fast.next.next != null){
           fast = fast.next.next;
           slow = slow.next;
       }
       
       Node<T> temp = slow.next;
       slow.next = null;
       return temp;
       
       

        
    }
    
    private Node<T> mergeSort(Node<T> split){
          
       if(split == null || split.next == null){
           return split;
       }
       
       Node<T> another = splitList();
        
       split = mergeSort(split);
       another = mergeSort(split);
       
       return merge(split, another);
        
    }
 
    
    private Node<T> merge(Node<T> right, Node<T> left){
        if( right == null)
            return left;
        if(left == null)
            return right; 
        
        
        
        
        if(right.data.compareTo(left.data) > 0){
            
            right.next = merge(right.next, left);
            right.next.previous = left;
            right.previous = null;
            return right;
            
            
        } else { 
            
            left.next = merge(right, left.next);
            left.previous.next = left;
            left.previous = null;
            return left;
            
            
            
        }
        
       
            
            
    }
    
    

}
