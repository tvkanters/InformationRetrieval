package com.uva.ir;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.uva.ir.indexing.Indexer;
import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.model.Document;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.retrieval.Retriever;
import com.uva.ir.retrieval.models.IntersectionRetrievalModel;

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
        // Collect the documents
        final List<Document> documentList = new LinkedList<>();
        for (final File fileEntry : new File(COLLECTION_FOLDER).listFiles()) {
            try {
                documentList.add(new Document(fileEntry));
            } catch (IOException ex) {
                System.err.println("Failed to load: " + fileEntry);
            }
        }

        // Prepare the inverted index and retriever
        final InvertedIndex invertedIndex = Indexer.createInvertedIndex(documentList);
        final Retriever retriever = new Retriever(invertedIndex, new IntersectionRetrievalModel());

        // Repeatedly ask the user for search queries
        final Scanner inputReader = new Scanner(System.in);
        while (true) {
            System.out.print("Lupti Search: ");
            final String query = inputReader.nextLine();

            // Decide what to do with the input
            if (query.equals("")) {
                System.err.println("KEINE INPUT!");
                System.out.println();

            } else if (query.equals(CMD_QUIT)) {
                break;

            } else {
                // Execute the specified query
                final List<QueryResultEntry> results = retriever.executeQuery(query);
                System.out.println(results.size());
                System.out.println();

                break;
            }
        }
        inputReader.close();
    }

}
