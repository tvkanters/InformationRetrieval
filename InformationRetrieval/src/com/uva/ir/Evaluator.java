package com.uva.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.uva.ir.evaluation.EvaluationResult;
import com.uva.ir.evaluation.Query;
import com.uva.ir.evaluation.Query.QueryID;
import com.uva.ir.evaluation.RetrievalModelEvaluator;
import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.preprocessing.Preprocessor;
import com.uva.ir.preprocessing.SimplePreprocessor;
import com.uva.ir.preprocessing.StemmingPreprocessor;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.retrieval.Retriever;
import com.uva.ir.retrieval.models.BM25RetrievalModel;
import com.uva.ir.retrieval.models.IntersectionRetrievalModel;
import com.uva.ir.retrieval.models.RetrievalModel;
import com.uva.ir.retrieval.models.TfIdfRetrievalModel;
import com.uva.ir.utils.FileManager;

/**
 * A class used for evaluating the results of retrieval models through our own test and TREC's
 * tests.
 */
public class Evaluator {

    public static void main(final String[] args) {
        RetrievalModel rtModel = null;
        rtModel = new IntersectionRetrievalModel();
        Retriever retriever = generateRetriever(new StemmingPreprocessor(), rtModel);

        createTrecFile(retriever);
        System.out.println(evaluateWithOfflineTrec());
        // evaluateTrec();
    }

    /**
     * Use TREC to evaluate the scores of different b and k values in a BM25 search.
     */
    private static void evaluateTrec() {

        double bestResult = 0;
        RetrievalModel best = null;
        double worstResult = Double.MAX_VALUE;
        RetrievalModel worst = null;
        RetrievalModel rtModel = null;

        double stepSizeB = 0.05;
        int stepSizeK = 1;
        for (double b = stepSizeB; b <= 1; b += stepSizeB) {
            System.out.println("----- " + b + " ------");
            for (int k = 1; k <= 20; k += stepSizeK) {

                rtModel = new BM25RetrievalModel(b, k);
                Retriever retriever = generateRetriever(new StemmingPreprocessor(), rtModel);

                createTrecFile(retriever);
                double result = evaluateWithOfflineTrec();

                System.out.println("& B: " + b + " K: " + k + " & " + result + " \\\\");

                if (result > bestResult) {
                    bestResult = result;
                    best = rtModel;
                }

                if (result < worstResult) {
                    worstResult = result;
                    worst = rtModel;
                }
            }
        }

        System.out.println("BEST");
        System.out.println(best.getModelName());
        System.out.println(bestResult);

        System.out.println();
        System.out.println("WORST");
        System.out.println(worst.getModelName());
        System.out.println(worstResult);
    }

    /**
     * Perform TREC and save the results to a file.
     * 
     * @param retriever
     *            The retriever model to use
     */
    private static void createTrecFile(Retriever retriever) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("trec_eval.9.0/result.txt", "UTF-8");

            List<QueryResultEntry> resultList = retriever.executeQuery(Query
                    .getQueryForID(QueryID.QUERY_6));
            List<String> result = generateTrecData(resultList, QueryID.QUERY_6);
            for (String s : result) {
                writer.println(s);
            }

            resultList = retriever.executeQuery(Query.getQueryForID(QueryID.QUERY_7));
            result = generateTrecData(resultList, QueryID.QUERY_7);
            for (String s : result) {
                writer.println(s);
            }
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs TREC evaluation based on offline results.
     * 
     * @return The MAP score of the results
     */
    private static double evaluateWithOfflineTrec() {
        double result = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            String cmdString = "trec_eval.9.0/trec_eval -q -c -M1000 trec_eval.9.0/qrels.txt trec_eval.9.0/result.txt";
            Process pr = rt.exec(cmdString);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line = null;

            while ((line = input.readLine()) != null) {
                if (line.startsWith("map")) {
                    String[] r = line.replaceAll("\\s+", " ").split(" ");
                    result = Double.parseDouble(r[2]);
                }
            }

            int exitVal = pr.waitFor();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Use our own evaluation method to determine the performance of retrieval methods.
     */
    private static void evaluateLupti() {
        RetrievalModel rtModel = new BM25RetrievalModel();
        Retriever retriever = generateRetriever(new SimplePreprocessor(), rtModel);

        System.out.println("---- " + rtModel.getModelName() + " ----");
        EvaluationResult result = RetrievalModelEvaluator
                .evaluateTop100(retriever, QueryID.QUERY_6);

        System.out.println("Query: " + Query.getQueryForID(result.getQueryID()));
        System.out.println(result);
        System.out.println();

        result = RetrievalModelEvaluator.evaluateTop100(retriever, QueryID.QUERY_7);
        System.out.println("Query: " + Query.getQueryForID(result.getQueryID()));
        System.out.println(result);
        System.out.println();

        rtModel = new TfIdfRetrievalModel();
        retriever = generateRetriever(new SimplePreprocessor(), rtModel);

        System.out.println("---- " + rtModel.getModelName() + " ----");
        result = RetrievalModelEvaluator.evaluateTop100(retriever, QueryID.QUERY_6);
        System.out.println("Query: " + Query.getQueryForID(result.getQueryID()));
        System.out.println(result);
        System.out.println();

        result = RetrievalModelEvaluator.evaluateTop100(retriever, QueryID.QUERY_7);
        System.out.println("Query: " + Query.getQueryForID(result.getQueryID()));
        System.out.println(result);
        System.out.println();
    }

    /**
     * Generate a retriever model with the given preprocessor and retrieval model.
     * 
     * @param preprocessor
     *            The preprocessor to be used
     * @param retrievalModel
     *            The retrieval model to be used
     * 
     * @return The generated retriever model
     */
    private static Retriever generateRetriever(final Preprocessor preprocessor,
            final RetrievalModel retrievalModel) {
        final File[] files = new File(FileManager.COLLECTION_FOLDER).listFiles();
        final InvertedIndex invertedIndex = new InvertedIndex(preprocessor, files);
        return new Retriever(invertedIndex, retrievalModel);
    }

    /**
     * Generate results in a way that TREC can parse it.
     * 
     * @param queryResults
     *            The results of the query and retrieval model
     * @param queryID
     *            The ID of the query that was executed
     * 
     * @return A list of the results in TREC format
     */
    private static List<String> generateTrecData(List<QueryResultEntry> queryResults,
            QueryID queryID) {
        List<String> result = new ArrayList<String>();

        int rank = 0;
        for (QueryResultEntry q : queryResults) {
            // <queryID> Q0 <documentID> <rank> <score> <runID>
            result.add(queryID.getValue() + " Q0 " + q.getDocument().getBaseName() + " " + rank++
                    + " " + q.getScore() + " 1");
        }

        return result;
    }
}
