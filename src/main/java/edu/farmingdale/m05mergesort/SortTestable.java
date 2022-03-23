/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m05mergesort;


/**
 * Interface to define a minimal set of methods to test a sort
 * @author gerstl
 */
public interface SortTestable<T extends Comparable<T>> {
    /**
     * Appends an item to the current set of items
     * @param newItem the item to add
     */
    public void append(T newItem);
    /** 
     * Sort the items
     */
    public void sort();
    /**
     * Get the count of items
     * @return number of items in the container
     */
    public int getCount();
    /** 
     * Get the item at a location
     * @param index Location of the item (0...getCount()-1)
     * @return The item at location index
     */
    public T getItemAt(int index);
}