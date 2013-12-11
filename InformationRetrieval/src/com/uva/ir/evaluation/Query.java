package com.uva.ir.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.uva.ir.utils.FileManager;

public class Query {

    public enum QueryID {
        QUERY_6(1),
        QUERY_7(2);

        private final int id;

        QueryID(final int id) {
            this.id = id;
        }

        public int getValue() {
            return id;
        }
    }

    /** The file containing the provided evaluation information */
    private static final String EVALUATION_FILE = FileManager.ASSETS_FOLDER + "qrels.txt";

    private static final String QUERY_6_STRING = "sustainable ecosystems";

    private static final String QUERY_7_STRING = "air guitar textile sensors";

    /**
     * Gets the string for the given query ID
     * 
     * @param queryID
     *            The queryID for the given query
     * @return The query string or null if the given queryID does not exist
     */
    public static String getQueryForID(final QueryID queryID) {

        switch (queryID) {
        case QUERY_6:
            return QUERY_6_STRING;
        case QUERY_7:
            return QUERY_7_STRING;

        default:
            return null;
        }
    }

    /**
     * Get all relevant Documents for this QueryID
     * 
     * TODO figure out, if all files which are not contained in qrels.txt are actually unrelevant
     * 
     * @param queryID
     *            The QueryID
     * @return The relevant documents for this QueryID
     */
    public static Set<String> getRelevantDocuments(final QueryID queryID) {
        Set<String> relevantDocuments = new HashSet<>();

        final File[] files = new File(FileManager.COLLECTION_FOLDER).listFiles();
        Set<String> availableDocuments = new HashSet<>();

        for (File f : files) {
            // remove .txt suffix
            availableDocuments.add(f.getName().substring(0, f.getName().length() - 4));
        }

        final File evaluationFile = new File(EVALUATION_FILE);
        try {
            String evaluationFileContent = FileManager.getFileContents(evaluationFile);
            String[] fileContentSplit = evaluationFileContent.split("\n");

            for (String s : fileContentSplit) {
                String[] fileSplit = s.split(" ");
                if (fileSplit[3] != "0") {
                    String documentName = fileSplit[2];

                    if (fileSplit[0].equals("" + queryID.getValue())) {
                        relevantDocuments.add(documentName + ".txt");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return relevantDocuments;
    }

}
