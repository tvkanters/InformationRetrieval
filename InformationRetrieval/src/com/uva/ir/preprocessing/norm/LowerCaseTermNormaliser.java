package com.uva.ir.preprocessing.norm;

import java.util.LinkedList;
import java.util.List;

/**
 * A normaliser that makes the terms lower case.
 */
public class LowerCaseTermNormaliser implements TermNormaliser {

    /**
     * Normalises the specified tokens by making the tokens lower case.
     * 
     * @param tokens
     *            The tokens to normalise
     * 
     * @return The normalised tokens
     */
    @Override
    public List<String> performNormalisation(final List<String> tokens) {
        final List<String> newTokens = new LinkedList<>();
        for (final String token : tokens) {
            newTokens.add(token.toLowerCase());
        }
        return newTokens;
    }

}
