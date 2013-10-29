package com.uva.ir.retrieval;

import com.uva.ir.model.Document;

/**
 * A structure for a single query result.
 */
public class QueryResultEntry {

    /** The document which the result refers to */
    private final Document mDocument;

    /** The score that the result has received */
    private final double mScore;

    /**
     * Prepares a new query result entry based on the document and score.
     * 
     * @param document
     *            The document the result refers to
     * @param score
     *            THe score this reuslt has received
     */
    public QueryResultEntry(final Document document, final double score) {
        mDocument = document;
        mScore = score;
    }

    /**
     * Retrieves the document which the result refers to
     * 
     * @return The result's document
     */
    public Document getDocument() {
        return mDocument;
    }

    /**
     * Retrieves the score that the query result received
     * 
     * @return The result's score
     */
    public double getScore() {
        return mScore;
    }
}
