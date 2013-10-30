package com.uva.ir.indexing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uva.ir.model.Document;
import com.uva.ir.model.PostingsListing;

/**
 * The index for the dictionary and postings that is created as a result of using an indexer.
 */
public class InvertedIndex {

    /** The mapping from each term to its postings information */
    private final Map<String, PostingsListing> mTermMapping = new HashMap<>();

    /** All documents within the inverted index */
    private final List<Document> mDocumentList;

    /** The average size of all documents within the index */
    private final double mAverageDocumentSize;

    /**
     * Prepares a new inverted index for the specified list for documents.
     * 
     * @param documentList
     *            The documents that should be indexed
     */
    public InvertedIndex(final List<Document> documentList) {
        mDocumentList = documentList;

        // Index the documents
        long documentSize = 0;
        for (final Document document : documentList) {
            documentSize += document.getDocumentSize();

            for (final String term : document.getTermPositions().keySet()) {
                add(term, document);
            }
        }

        mAverageDocumentSize = documentSize / documentList.size();
    }

    /**
     * Maps a document to the specified term.
     * 
     * @param term
     *            The term to add a document to
     * @param document
     *            The document to add
     */
    public void add(final String term, final Document document) {
        PostingsListing postingsListing = mTermMapping.get(term);

        if (postingsListing == null) {
            postingsListing = new PostingsListing();
            mTermMapping.put(term, postingsListing);
        }

        postingsListing.add(document);
    }

    /**
     * Retrieves the postings list for the specified term.
     * 
     * @param term
     *            The term to find the postings list for
     * 
     * @return The postings list
     */
    public PostingsListing get(final String term) {
        return mTermMapping.get(term);
    }

    /**
     * Retrieves all documents that the index contains.
     * 
     * @return The index's documents
     */
    public List<Document> getDocuments() {
        return mDocumentList;
    }

    /**
     * Retrieves the average size of all documents that the index contains.
     * 
     * @return The average document size
     */
    public double getAverageDocumentSize() {
        return mAverageDocumentSize;
    }

}
