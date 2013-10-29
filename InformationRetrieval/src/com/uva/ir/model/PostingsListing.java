package com.uva.ir.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The list of postings that is mapped to a term in the inverted index.
 */
public class PostingsListing {

    /** The list of documents (i.e., postings) for this postings list */
    private final List<Document> mDocuments = new LinkedList<>();

    /**
     * Adds a document to the postings list.
     * 
     * @param document
     *            The document to add
     */
    public void add(final Document document) {
        mDocuments.add(document);
    }

    /**
     * Retrieves the list of documents for the postings list.
     * 
     * @return The documents
     */
    public List<Document> getDocuments() {
        return mDocuments;
    }
}
