package com.uva.ir.preprocessing;

import java.util.List;

import com.uva.ir.preprocessing.norm.LowerCaseTermNormaliser;
import com.uva.ir.preprocessing.norm.TermNormaliser;
import com.uva.ir.preprocessing.tokens.SimpleTokeniser;
import com.uva.ir.preprocessing.tokens.Tokeniser;

/**
 * Performs the preprocessing tasks for indexing and retrieval.
 */
public class SimplePreprocessor extends Preprocessor {

    /** The tokeniser used to turn the contents into tokens */
    private final Tokeniser mTokeniser = new SimpleTokeniser();

    /** The normaliser to convert tokens to terms */
    private final TermNormaliser mNormaliser = new LowerCaseTermNormaliser();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTerms(final String contents) {
        final List<String> tokens = mTokeniser.retrieveTokens(contents);
        final List<String> terms = mNormaliser.performNormalisation(tokens);

        return terms;
    }
}
