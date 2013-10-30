package com.uva.ir.preprocessing.lemma;

import java.util.List;

/**
 * An interface for performing lemmatising tasks.
 */
public interface Lemmatiser {

    /**
     * Lemmatises the specified tokens.
     * 
     * @param tokens
     *            The tokens to lemmatise
     * 
     * @return The lemmatised tokens
     */
    public List<String> performLemmatisation(List<String> tokens);

}
