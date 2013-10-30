package com.uva.ir.preprocessing.norm;

import java.util.List;

/**
 * An interface for performing normalising tasks.
 */
public interface TermNormaliser {

    /**
     * Normalises the specified tokens.
     * 
     * @param tokens
     *            The tokens to normalise
     * 
     * @return The normalised tokens
     */
    public List<String> performNormalisation(List<String> tokens);

}
