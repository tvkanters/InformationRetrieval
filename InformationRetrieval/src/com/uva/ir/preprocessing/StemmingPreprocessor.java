package com.uva.ir.preprocessing;

import java.util.List;

import com.uva.ir.preprocessing.norm.LowerCaseTermNormaliser;
import com.uva.ir.preprocessing.norm.TermNormaliser;
import com.uva.ir.preprocessing.stemma.PorterStemmatiser;
import com.uva.ir.preprocessing.stemma.Stemmatiser;
import com.uva.ir.preprocessing.tokens.SimpleTokeniser;
import com.uva.ir.preprocessing.tokens.Tokeniser;

/**
 * Performs the preprocessing tasks for indexing and retrieval.
 */
public class StemmingPreprocessor extends Preprocessor {

    /** The tokeniser used to turn the contents into tokens */
    private final Tokeniser mTokeniser = new SimpleTokeniser();

    /** The normaliser to convert tokens to terms */
    private final TermNormaliser mNormaliser = new LowerCaseTermNormaliser();

    /** The porterstemmer to convert tokens to terms */
    private final Stemmatiser mStemmer = new PorterStemmatiser();

    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTerms(final String contents) {
        final List<String> tokens = mTokeniser.retrieveTokens(contents);
        List<String> terms = mNormaliser.performNormalisation(tokens);
        terms = mStemmer.performStemmatisation(terms);

        return terms;
    }
}
