package com.uva.ir.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uva.ir.preprocessing.Preprocessor;
import com.uva.ir.utils.FileManager;

/**
 * A structure for the document.
 */
public class Document {

    /** The name of the document */
    private final String mName;

    /** The contents of the document */
    private final String mContents;

    /** The list of the terms within the document */
    private final List<String> mTermList;

    /** The positions of the terms within the document */
    private final Map<String, List<Integer>> mTermPositions = new HashMap<>();

    /**
     * Prepares a new document for the specified file.
     * 
     * @param file
     *            The file used for the document
     * 
     * @throws IOException
     *             Thrown when the file cannot have its contents read
     */
    public Document(final File file) throws IOException {
        mName = file.getName();
        mContents = FileManager.getFileContents(file);
        mTermList = Preprocessor.getTerms(mContents);

        // Find the positions of each term and save them for later use
        int i = 0;
        for (final String term : mTermList) {
            if (!mTermPositions.containsKey(term)) {
                mTermPositions.put(term, new LinkedList<Integer>());
            }
            mTermPositions.get(term).add(i);
            ++i;
        }
    }

    /**
     * Retrieves the name of the document.
     * 
     * @return The file's name
     */
    public String getName() {
        return mName;
    }

    /**
     * Retrieves the contents of the document.
     * 
     * @return The file's document
     */
    public String getContents() {
        return mContents;
    }

    /**
     * Retrieves the list of the terms within the document.
     * 
     * @return The list of terms
     */
    public List<String> getTermList() {
        return mTermList;
    }

    /**
     * Retrieves the positions of the terms within the document.
     * 
     * @return The mapping from terms to positions
     */
    public Map<String, List<Integer>> getTermPositions() {
        return mTermPositions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return mName;
    }

}