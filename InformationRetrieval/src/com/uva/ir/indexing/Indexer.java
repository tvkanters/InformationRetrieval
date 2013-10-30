package com.uva.ir.indexing;

import java.util.List;

import com.uva.ir.model.Document;

/**
 * The interface for indexing types.
 */
public class Indexer {

    /**
     * Creates an inverted index based on the specified files.
     * 
     * @param files
     *            The files to index
     * 
     * @return The inverted index
     */
    public static InvertedIndex createInvertedIndex(List<Document> documentList) {
        final InvertedIndex invertedIndex = new InvertedIndex();

        for (final Document document : documentList) {
            for (final String term : document.getTermPositions().keySet()) {
                invertedIndex.add(term, document);
            }
        }

        return invertedIndex;
    }
}
