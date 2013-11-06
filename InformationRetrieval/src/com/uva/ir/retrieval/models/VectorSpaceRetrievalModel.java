package com.uva.ir.retrieval.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.model.Document;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.utils.Vector;

/**
 * The model that retrieves results ranked according to the vector space model.
 */
public class VectorSpaceRetrievalModel implements RetrievalModel {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QueryResultEntry> executeQuery(final InvertedIndex invertedIndex, final String query) {
        final Map<Document, Double> documentScores = new HashMap<>();
        final List<QueryResultEntry> results = new LinkedList<>();

        final Vector queryVector = invertedIndex.getVector(query);
        final double queryNorm = queryVector.norm();

        // Score each document
        for (final Document document : invertedIndex.getDocuments()) {
            final Vector documentVector = invertedIndex.getVector(document);

            final double score = queryVector.dot(documentVector)
                    / (queryNorm * documentVector.norm());
            documentScores.put(document, (!Double.isNaN(score) ? score : 0));
        }

        // Put the documents as results in the order of the scores
        for (final Document document : documentScores.keySet()) {
            results.add(new QueryResultEntry(document, documentScores.get(document)));
        }
        Collections.sort(results);

        return results;
    }

    @Override
    public String getModelName() {
        return "Vector space retrieval model";
    }
}
