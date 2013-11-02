package com.uva.ir.retrieval.models;

import java.util.LinkedList;
import java.util.List;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.model.Document;
import com.uva.ir.model.PostingsListing;
import com.uva.ir.retrieval.QueryResultEntry;

/**
 * The model that retrieves results ranked according to the tf-idf model.
 */
public class IntersectionRetrievalModel implements RetrievalModel {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QueryResultEntry> executeQuery(final InvertedIndex invertedIndex, final String query) {
        final List<QueryResultEntry> results = new LinkedList<>();

        // Find the documents that match the terms
        List<Document> documents = null;
        for (final String term : invertedIndex.getPreprocessor().getTerms(query)) {
            final PostingsListing postingListing = invertedIndex.get(term);
            if (postingListing != null) {
                final List<Document> tempDocuments = postingListing.getDocuments();
                if (documents == null) {
                    // This is the first document list found, so accept it entirely
                    documents = tempDocuments;
                } else {
                    // For further matches, strip away none-existing documents
                    documents.retainAll(tempDocuments);
                }
            } else {
                // If no results are found for one term, stop searching
                documents = null;
                break;
            }
        }

        // Convert the terms to query result entries
        if (documents != null) {
            for (final Document document : documents) {
                results.add(new QueryResultEntry(document, 0));
            }
        }

        return results;
    }

    @Override
    public String getModelName() {
        return "Intersection Retrieval";
    }
}
