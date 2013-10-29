package com.uva.ir.retrieval;

import java.util.List;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.retrieval.models.RetrievalModel;

/**
 * Retrieves the results for a query based on a specified inverted index.
 */
public class Retriever {

    /** THe index to query */
    private final InvertedIndex mInvertedIndex;

    /** The model to use when executing the queries */
    private final RetrievalModel mRetrievalModel;

    /**
     * Prepares the retriever with an index and model.
     * 
     * @param invertedIndex
     *            The inverted index to search
     * @param retrievalModel
     *            The model to use when executing queries
     */
    public Retriever(final InvertedIndex invertedIndex, final RetrievalModel retrievalModel) {
        mInvertedIndex = invertedIndex;
        mRetrievalModel = retrievalModel;
    }

    /**
     * Performs the query on this retriever's index and retrieval model.
     * 
     * @param query
     *            The query to execute
     * 
     * @return The results for the specified query
     */
    public List<QueryResultEntry> executeQuery(final String query) {
        return mRetrievalModel.executeQuery(mInvertedIndex, query);
    }
}
