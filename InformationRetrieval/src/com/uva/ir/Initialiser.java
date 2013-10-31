package com.uva.ir;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.preprocessing.SimplePreprocessor;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.retrieval.Retriever;
import com.uva.ir.retrieval.models.BM25RetrievalModel;

/**
 * Prepares the search program and asks the user for input.
 */
public class Initialiser {

    /** The folder containing all the assets */
    private final static String ASSETS_FOLDER = "assets/";

    /** The folder containing the sample documents */
    private final static String COLLECTION_FOLDER = ASSETS_FOLDER + "collection/";

    /** The command to quit the search */
    private final static String CMD_QUIT = "quit";

    public static void main(String[] args) {

        // Collect the files to index and search through
        final File[] files = new File(COLLECTION_FOLDER).listFiles();

        // Prepare the inverted index and retriever
        final InvertedIndex invertedIndex = new InvertedIndex(new SimplePreprocessor(), files);
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

                break;
            }
        }
        inputReader.close();
    }

}
