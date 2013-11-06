package com.uva.ir.preprocessing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for types performing the preprocessing tasks for indexing and retrieval
 */
public abstract class Preprocessor {

    /**
     * Retrieves the terms from the specified contents or queries.
     * 
     * @param contents
     *            The contents to turn into terms
     * 
     * @return The terms found in the contents
     */
    public abstract List<String> getTerms(String contents);

    /**
     * Retrieves the terms in the contents, mapped to the amount of times they occur.
     * 
     * @param contents
     *            The contents to preprocess
     * 
     * @return The terms mapped to the amount of times they occur
     */
    public Map<String, Integer> getTermFrequencies(final String contents) {
        final Map<String, Integer> termFrequencies = new HashMap<>();

        for (final String term : getTerms(contents)) {
            if (!termFrequencies.containsKey(term)) {
                termFrequencies.put(term, 0);
            }

            termFrequencies.put(term, termFrequencies.get(term) + 1);
        }

        return termFrequencies;
    }
}
