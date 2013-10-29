package com.uva.ir.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A structure for the document.
 */
public class Document {

    /** The contents of the document */
    private final String mContents;

    /** The positions of the terms within the document */
    private final Map<Term, List<Integer>> mTermPositions = new HashMap<>();

    /**
     * Prepares a new document for the specified file.
     * 
     * @param file
     *            The file used for the document
     */
    public Document(final File file) {
        // TODO: Set the contents based on the file
        mContents = "";
    }

    /**
     * Retrieves the contents of the document.
     * 
     * @return The file's document
     */
    public String getContents() {
        return mContents;
    }
}
