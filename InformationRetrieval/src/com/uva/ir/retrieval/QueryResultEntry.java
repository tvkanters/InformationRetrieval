package com.uva.ir.retrieval;

import com.uva.ir.model.Document;

/**
 * A structure for a single query result.
 */
public class QueryResultEntry implements Comparable<QueryResultEntry> {

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
     *            The score this result has received
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return mDocument.getName() + "  -  " + mScore;
    }

    /**
     * Compares this result to the specified one and returns the relation based on each result's
     * score.
     * 
     * @param comparative
     *            The result to compare this one to
     * 
     * @return 0 if the scores are equal, -1 if this result's score is better and 1 if this result's
     *         score is worse
     */
    @Override
    public int compareTo(final QueryResultEntry comparative) {
        return (mScore > comparative.mScore ? -1 : (mScore == comparative.mScore ? 0 : 1));
    }
}
