package com.uva.ir.preprocessing;

import java.util.List;

import com.uva.ir.preprocessing.norm.LowerCaseTermNormaliser;
import com.uva.ir.preprocessing.norm.TermNormaliser;
import com.uva.ir.preprocessing.tokens.SimpleTokeniser;
import com.uva.ir.preprocessing.tokens.Tokeniser;

/**
 * Performs the preprocessing tasks for indexing and retrieval
 */
public class Preprocessor {

    /** The tokeniser used to turn the contents into tokens */
    private final static Tokeniser sTokeniser = new SimpleTokeniser();

    /** The normaliser to convert tokens to terms */
    private final static TermNormaliser sNormaliser = new LowerCaseTermNormaliser();

    /**
     * Retrieves the terms from the specified contents or queries.
     * 
     * @param contents
     *            The contents to turn into terms
     * 
     * @return The terms found in the contents
     */
    public static List<String> getTerms(final String contents) {
        final List<String> tokens = sTokeniser.retrieveTokens(contents);
        final List<String> terms = sNormaliser.performNormalisation(tokens);

        return terms;
    }
}
