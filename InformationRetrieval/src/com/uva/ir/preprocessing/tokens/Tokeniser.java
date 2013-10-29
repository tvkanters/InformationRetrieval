package com.uva.ir.preprocessing.tokens;

import java.util.List;

/**
 * An interface for performing tokenising tasks.
 */
public interface Tokeniser {

    /**
     * Splits the query up in tokens.
     * 
     * @param query
     *            The query to split up
     * 
     * @return The tokens from within the query
     */
    public List<String> retrieveTokens(final String query);
}
