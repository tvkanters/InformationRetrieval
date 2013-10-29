package com.uva.ir.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The list of postings that is mapped to a term in the inverted index.
 */
public class PostingsListing {

    /** The list of postings (i.e., documents) for this postings list */
    final List<Document> mPostings = new LinkedList<>();

}
