package com.uva.ir.indexing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uva.ir.model.Document;
import com.uva.ir.model.PostingsListing;
import com.uva.ir.preprocessing.Preprocessor;
import com.uva.ir.utils.Vector;

/**
 * The index for the dictionary and postings that is created as a result of using an indexer.
 */
public class InvertedIndex {

    /** The preprocessor used for parsing the documents in this index */
    private final Preprocessor mPreprocessor;

    /** The mapping from each term to its postings information */
    private final Map<String, PostingsListing> mTermMapping = new HashMap<>();

    /** All documents within the inverted index */
    private final List<Document> mDocuments = new LinkedList<>();

    /** The average size of all documents within the index */
    private final double mAverageDocumentSize;

    /**
     * Prepares a new inverted index for the specified list for documents.
     * 
     * @param preprocessor
     *            The preprocessor to be used for parsing the documents
     * @param files
     *            The documents that should be indexed
     */
    public InvertedIndex(final Preprocessor preprocessor, final File[] files) {
        mPreprocessor = preprocessor;

        // Index the files as documents
        long documentSize = 0;
        for (final File file : files) {
            final Document document;
            try {
                document = new Document(preprocessor, file);
            } catch (IOException ex) {
                System.err.println("Failed to load: " + file);
                continue;
            }

            mDocuments.add(document);
            documentSize += document.getDocumentSize();

            for (final String term : document.getTermPositions().keySet()) {
                add(term, document);
            }
        }

        mAverageDocumentSize = documentSize / files.length;
    }

    /**
     * Maps a document to the specified term.
     * 
     * @param term
     *            The term to add a document to
     * @param document
     *            The document to add
     */
    private void add(final String term, final Document document) {
        PostingsListing postingsListing = mTermMapping.get(term);

        if (postingsListing == null) {
            postingsListing = new PostingsListing();
            mTermMapping.put(term, postingsListing);
        }

        postingsListing.add(document);
    }

    /**
     * Retrieves preprocessor used for parsing the documents in this index.
     * 
     * @return The index's preprocessor
     */
    public Preprocessor getPreprocessor() {
        return mPreprocessor;
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
        return mDocuments;
    }

    /**
     * Retrieves the average size of all documents that the index contains.
     * 
     * @return The average document size
     */
    public double getAverageDocumentSize() {
        return mAverageDocumentSize;
    }

    /**
     * Creates a vector for the specified document based on the dimensions of the inverted index
     * terms. Each dimension will be specified by how many time the term occurs in the document.
     * 
     * @param document
     *            The document to represent as a vector
     * 
     * @return The document in vector form
     */
    public Vector getVector(final Document document) {
        final Set<String> keys = mTermMapping.keySet();
        double[] data = new double[keys.size()];

        int i = 0;
        for (final String term : mTermMapping.keySet()) {
            data[i++] = document.getTermFrequency(term);
        }

        return new Vector(data);
    }

    /**
     * Creates a vector for the specified query based on the dimensions of the inverted index terms.
     * Each dimension will be specified by how many time the term occurs in the query.
     * 
     * @param query
     *            The query to represent as a vector
     * 
     * @return The query in vector form
     */
    public Vector getVector(final String query) {
        final Map<String, Integer> termFrequencies = mPreprocessor.getTermFrequencies(query);
        final Set<String> keys = mTermMapping.keySet();
        double[] data = new double[keys.size()];

        int i = 0;
        for (final String term : mTermMapping.keySet()) {
            if (termFrequencies.containsKey(term)) {
                data[i] = termFrequencies.get(term);
            }

            ++i;
        }

        return new Vector(data);
    }

}
