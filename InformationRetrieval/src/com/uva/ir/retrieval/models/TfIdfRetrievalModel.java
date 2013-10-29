package com.uva.ir.retrieval.models;

import java.util.List;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.retrieval.QueryResultEntry;

/**
 * The model that retrieves results ranked according to the tf-idf model.
 */
public class TfIdfRetrievalModel implements RetrievalModel {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QueryResultEntry> executeQuery(final InvertedIndex invertedIndex, final String query) {
        return null;
    }

}
