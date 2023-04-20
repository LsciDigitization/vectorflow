package com.mega.hephaestus.pms.workflow.component.sortedset;

import java.util.Optional;

public class SortedSet implements SetInterface<String> {

    // Node class
    private static class Node {

        // Variables
        String data;
        Node prev, next;

        // Constructors

        // parameter constructor (prev and next are null)
        public Node (String data) {
            this.data = data;
        }
    }


    // Variables
    Node head;
    int count;

    // Constructor

    // default constructor
    public SortedSet () {
        head = null;
        count = 0;
    }


    /**
     * Gets the current number of entries in this set.
     * @return  The integer number of entries currently in the set.
     */
    @Override
    public long getCurrentSize() {
        return count;
    }

    /** Sees whether this set is empty.
     * @return  True if the set is empty, or false if not.
     */
    @Override
    public boolean isEmpty() {
        // if count is more than 0
        if (count > 0) {
            return false;
        }
        // otherwise
        return true;
    }

    /**
     * Adds a new entry to this set, avoiding duplicates.
     * @param newEntry  The object to be added as a new entry.
     * @return  True if the addition is successful, or false if the item already is in the set.
     */
    @Override
    public boolean add(String newEntry) {
        Node newNode = new Node(newEntry);
        // check for duplicate
        if (contains(newEntry)) {
            return false;
        }
        // if empty
        if(isEmpty()) {
            head = newNode;
            count++;
            return true;
        }
        else if (head.data.compareTo(newNode.data) >= 0) {  // if head data greater than or equal to new data
            newNode.next = head;  // make the head the new node and shift data
            newNode.next.prev = newNode;
            head = newNode;
            count++;
            return true;
        }
        // if head less than newEntry, loop through set to find greater than or equal data
        Node current = head;
        while (current.next != null && current.next.data.compareTo(newNode.data) < 0) {  // loop through
            current = current.next;
        }
        // once data is found
        newNode.next = current.next;  // create link to next data

        if (current.next != null) {  // if newNode is not added to end of set
            newNode.next.prev = newNode;
        }
        current.next = newNode;  // add in data
        newNode.prev = current;
        count++;
        return true;
    }

    /**
     * Removes a specific entry from this set, if possible.
     * @param anEntry  The entry to be removed.
     * @return  True if the removal was successful, or false if not.
     */
    @Override
    public boolean remove(String anEntry) {
        // if empty
        if (isEmpty()) {
            return false;
        }
        Node current = head;
        // if data in head
        if (head.data == anEntry) {
            count--;
            if (head.next != null) {
                head.next.prev = null; // set head to null if next node exists
            }
            head = head.next;
            return true;
        }

        while(current.next != null) {
            if(current.next.data.equals(anEntry)) {
                if(current.next.next == null) {
                    current.next = null;
                    count--;
                    return true;
                } else {
                    current.next.next.prev = current.next;
                    current.next = current.next.next;
                    count--;
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Removes one unspecified entry from this set, if possible.
     * @return  Either the removed entry, if the removal was successful, or null.
     */
    @Override
    public Optional<String> remove() {
        Node temp = null;
        // if only one element
        if (head != null && head.next == null) {
            temp = head;
            head = null;
            count--;
            return Optional.ofNullable(temp.data);
        }
        // remove the first entry
        if (head != null) {
            temp = head;
            head = head.next;
            head.prev = null;
            count--;
            return Optional.ofNullable(temp.data);
        }
        return null; // if head is null, data will be null
    }

    /**
     * Removes the last entry from this set, if possible.
     * @return  Either the removed entry, if the removal was successful, or null.
     */
    @Override
    public Optional<String> removeLast() {
        // if empty
        if (isEmpty()) {
            return null;
        }
        Node temp = null;
        Node current = head;
        // if there is only one node
        if (head.next == null) {
            temp = head;
            head = null;
            count--;
            return Optional.ofNullable(temp.data);
        }
        while (current.next != null) {  // loop through set
            current = current.next;
        }
        temp = current; // make temp the last node
        // current.next = null; // set last node to null
        current.prev.next = current.next;  // set link
        count--;
        return Optional.ofNullable(temp.data);  // return the data
    }

    @Override
    public Optional<String> removeHead() {
        return Optional.empty();
    }

    @Override
    public Optional<String> get() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLast() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getHead() {
        return Optional.empty();
    }

    /**
     * Removes all entries from this set.
     */
    @Override
    public void clear() {
        while (count > 0) {
            remove();
        }
    }

    /**
     * Tests whether this set contains a given entry.
     * @param anEntry  The entry to locate.
     * @return  True if the set contains anEntry, or false if not.
     */
    @Override
    public boolean contains(String anEntry) {
        // if empty
        if (isEmpty()) {
            return false;
        }
        Node current = head;
        while (current.next != null) {
            if (current.data.equals(anEntry)) {
                return true;
            }
            current = current.next;
        }
        // if anEntry is the last in the set
        if (current.data.equals(anEntry)) {
            return true;
        }
        return false;
    }

    /*
     * returns a string representation of the items in the set,
     * specifically a space separated list of the strings, enclosed
     * in square brackets [].  For example, if the set contained
     * cat, dog, then this should return "[cat dog]".  If the
     * set were empty, then this should return "[]".
     */
    @Override
    public String toString() {
        String str = "[ ";
        Node current = head;
        while (current!= null) {
            str += current.data + " ";
            current = current.next;
        }
        str += "]";
        return str;
    }

    /**
     * Retrieves all entries that are in this set.
     * @return  A newly allocated array of all the entries in the set.
     */
    @Override
    public String[] toArray() {
        String[] entries = new String[count];  // create string array of size count
        Node current = head;
        for (int i = 0; i < count; i++) {
            entries[i] = current.data;
            current = current.next;
        }
        return entries;
    }

}
