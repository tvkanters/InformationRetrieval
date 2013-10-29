package com.uva.ir.indexing;

import java.util.HashMap;
import java.util.Map;

import com.uva.ir.model.PostingsListing;
import com.uva.ir.model.Term;

/**
 * The index for the dictionary and postings that is created as a result of using an indexer.
 */
public class InvertedIndex {

    /** The mapping from each term to its postings information */
    public final Map<Term, PostingsListing> mTermMapping = new HashMap<>();

}
