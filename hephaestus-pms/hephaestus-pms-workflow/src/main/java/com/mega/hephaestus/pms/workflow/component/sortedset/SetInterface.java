package com.mega.hephaestus.pms.workflow.component.sortedset;

import java.util.Optional;

public interface SetInterface<T> {

    /**
     * Gets the current number of entries in this set.
     *
     * @return The integer number of entries currently in the set.
     */
    long getCurrentSize();

    /**
     * Sees whether this set is empty.
     *
     * @return  True if the set is empty, or false if not.
     */
    boolean isEmpty();

    /**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * @param newEntry  The object to be added as a new entry.
     * @return  True if the addition is successful, or false if the item already is in the set.
     */
    boolean add(T newEntry);

    /**
     * Removes a specific entry from this set, if possible.
     *
     * @param anEntry  The entry to be removed.
     * @return  True if the removal was successful, or false if not.
     */
    boolean remove(T anEntry);

    /**
     * Removes one unspecified entry from this set, if possible.
     *
     * @return  Either the removed entry, if the removal was successful, or null.
     */
    Optional<T> remove();

    /**
     * Removes the last entry from this set, if possible.
     *
     * @return  Either the removed entry, if the removal was successful, or null.
     */
    Optional<T> removeLast();

    /**
     * Removes the head entry from this set, if possible.
     *
     * @return  Either the removed entry, if the removal was successful, or null.
     */
    Optional<T> removeHead();

    /**
     * Get one unspecified entry from this set, if possible.
     *
     * @return  Either the get entry, if the get was successful, or null.
     */
    Optional<T> get();

    /**
     * Get the last entry from this set, if possible.
     *
     * @return  Either the get entry, if the get was successful, or null.
     */
    Optional<T> getLast();

    /**
     * Get the head entry from this set, if possible.
     *
     * @return  Either the get entry, if the get was successful, or null.
     */
    Optional<T> getHead();

    /**
     * Removes all entries from this set.
     */
    void clear();

    /**
     * Tests whether this set contains a given entry.
     *
     * @param anEntry  The entry to locate.
     * @return  True if the set contains anEntry, or false if not.
     */
    boolean contains(T anEntry);

    /** Retrieves all entries that are in this set.
     *
     * @return  A newly allocated array of all the entries in the set.
     */
    T[] toArray();

}
