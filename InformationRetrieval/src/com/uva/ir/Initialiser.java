package com.uva.ir;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.model.Document;
import com.uva.ir.model.PostingsListing;
import com.uva.ir.preprocessing.StemmingPreprocessor;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.retrieval.Retriever;
import com.uva.ir.retrieval.models.BM25RetrievalModel;
import com.uva.ir.utils.FileManager;

/**
 * Prepares the search program and asks the user for input.
 */
public class Initialiser {

    /** The command to quit the search */
    private final static String CMD_QUIT = "/quit";

    /** The command to save basic statistics */
    private final static String CMD_SAVE = "/stats";

    public static void main(String[] args) {

        // Collect the files to index and search through
        final File[] files = new File(FileManager.COLLECTION_FOLDER).listFiles();

        // Prepare the inverted index and retriever
        final InvertedIndex invertedIndex = new InvertedIndex(new StemmingPreprocessor(), files);
        final Retriever retriever = new Retriever(invertedIndex, new BM25RetrievalModel());

        // Repeatedly ask the user for search queries
        final Scanner inputReader = new Scanner(System.in);
        while (true) {
            System.out.print("Lupti Search: ");
            final String query = inputReader.nextLine();

            // Decide what to do with the input
            if (query.equals("")) {
                System.err.println("KEIN INPUT!");
                System.out.println();

            } else if (query.equals(CMD_SAVE)) {
                // Find the total number of terms within the index
                int totalTermFrequency = 0;
                for (final Document document : invertedIndex.getDocuments()) {
                    totalTermFrequency += document.getDocumentSize();
                }
                System.out.println("The total number of tokens in the collection: "
                        + totalTermFrequency);

                // Print the amount of different terms in the index
                System.out.println("The number of unique terms: "
                        + invertedIndex.getNumberOfTerms());

                // Find the amount of times that the word 'of' occurs
                final PostingsListing ofPosting = invertedIndex.get("of");
                int totalCount = 0;
                for (final Document document : ofPosting.getDocuments()) {
                    totalCount += document.getTermFrequency("of");
                }
                System.out.println("Total count of the token \"of\": " + totalCount);

            } else if (query.equals(CMD_QUIT)) {
                break;

            } else {
                // Execute the specified query
                final List<QueryResultEntry> results = retriever.executeQuery(query);

                for (final QueryResultEntry result : results) {
                    System.out.println(result);
                }

                System.out.println("Amount of results: " + results.size());
                System.out.println();
            }
        }
        inputReader.close();
    }

}
