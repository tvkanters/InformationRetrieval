package com.uva.ir.model;

/**
 * The structure for a term, based on tokens.
 */
public class Term {

    /** The string representation of the term */
    private final String mTerm;

    /**
     * Prepares a new term based on the string it should represent.
     * 
     * @param term
     *            The actual term
     */
    public Term(final String term) {
        mTerm = term;
    }

    /**
     * Retrieves the string representation for the term.
     * 
     * @return The term's string content
     */
    public String getTerm() {
        return mTerm;
    }

}
