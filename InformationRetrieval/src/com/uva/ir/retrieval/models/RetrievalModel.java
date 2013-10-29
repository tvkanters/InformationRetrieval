package com.uva.ir.retrieval.models;

import java.util.List;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.retrieval.QueryResultEntry;

/**
 * The interface for the models that can be used to retrieve results from an index given a query.
 */
public interface RetrievalModel {

    /**
     * Executes the specified query to find results within the index.
     * 
     * @param invertedIndex
     *            The index to search
     * @param query
     *            The query to execute
     * 
     * @return The results for the specified query
     */
    public List<QueryResultEntry> executeQuery(final InvertedIndex invertedIndex, final String query);

}
