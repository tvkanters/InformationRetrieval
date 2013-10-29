package com.uva.ir.indexing;

import java.io.File;

/**
 * The interface for indexing types.
 */
public interface Indexer {

    /**
     * Creates an inverted index based on the specified files.
     * 
     * @param files
     *            The files to index
     * 
     * @return The inverted index
     */
    public InvertedIndex createInvertedIndex(File[] files);

}
