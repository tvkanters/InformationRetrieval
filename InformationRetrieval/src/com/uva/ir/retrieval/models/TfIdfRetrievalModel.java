package com.uva.ir.retrieval.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.model.Document;
import com.uva.ir.model.PostingsListing;
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
        final Map<Document, Double> documentScores = new HashMap<>();
        final List<QueryResultEntry> results = new LinkedList<>();

        final int documentsAmount = invertedIndex.getDocuments().size();

        // Score documents for each term
        for (final String term : invertedIndex.getPreprocessor().getTerms(query)) {
            final PostingsListing postingsListing = invertedIndex.get(term);

            // If a term is not found, we cannot return any results
            if (postingsListing == null) {
                return results;
            }

            // Get the documents for the term and calculate the idf
            final List<Document> termDocuments = postingsListing.getDocuments();
            final double idf = Math.log10(documentsAmount / (double) termDocuments.size());

            // Score each document that contains the term
            for (final Document document : termDocuments) {
                // Map the document if it didn't receive a score before
                if (!documentScores.containsKey(document)) {
                    documentScores.put(document, 0.0);
                }

                // Add the tf-idf to the document's score
                final double tfIdf = document.getTermFrequency(term) * idf;
                documentScores.put(document, documentScores.get(document) + tfIdf);

            }
        }

        // Put the documents in the order of the scores
        for (final Document document : documentScores.keySet()) {
            final double score = documentScores.get(document);
            boolean inserted = false;

            for (int i = 0; i < results.size(); ++i) {
                // If the new score is better, add the new document before the other
                if (score > results.get(i).getScore()) {
                    results.add(i, new QueryResultEntry(document, score));

                    inserted = true;
                    break;
                }
            }

            // If we haven't inserted the document yet, add it at the end
            if (!inserted) {
                results.add(new QueryResultEntry(document, score));
            }
        }

        return results;
    }

}
