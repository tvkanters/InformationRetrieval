package com.uva.ir.preprocessing;

import java.util.List;

/**
 * Inferface for types performing the preprocessing tasks for indexing and retrieval
 */
public interface Preprocessor {

    /**
     * Retrieves the terms from the specified contents or queries.
     * 
     * @param contents
     *            The contents to turn into terms
     * 
     * @return The terms found in the contents
     */
    public List<String> getTerms(String contents);
}
