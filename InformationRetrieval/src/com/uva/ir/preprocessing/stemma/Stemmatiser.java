package com.uva.ir.preprocessing.stemma;

import java.util.List;

/**
 * An interface for performing stemmatising tasks.
 */
public interface Stemmatiser {

    /**
     * Stemmatises the specified tokens.
     * 
     * @param tokens
     *            The tokens to stemmatise
     * 
     * @return The stemmatised tokens
     */
    public List<String> performStemmatisation(List<String> tokens);

}
