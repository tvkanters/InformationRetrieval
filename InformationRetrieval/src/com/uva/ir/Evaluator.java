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
import com.uva.ir.retrieval.models.VectorSpaceRetrievalModel;
import com.uva.ir.utils.FileManager;

public class Evaluator {

    
    
    public static void main(final String[] args) {
        RetrievalModel rtModel = null;
        rtModel = new IntersectionRetrievalModel();
        Retriever retriever = generateRetriever(new StemmingPreprocessor(), rtModel);
        
        createTrecFile(retriever, rtModel);
        System.out.println(evaluateWithOfflineTrec());
//        evaluateTRAC();
    }
    
//    BM25 with B:0.4 K: 4.0
//    0.7697
    
//    BEST
//    BM25 with B:0.6 K: 10.0
//    0.7871
    
//    BEST
//    BM25 with B:0.45 K: 11.0
//    0.7871
    private static void evaluateTRAC() {
        
        double bestResult = 0;
        RetrievalModel best = null;
        double worstResult = Double.MAX_VALUE;
        RetrievalModel worst = null;
        RetrievalModel rtModel = null;
        
        double stepSizeB = 0.05;
        int stepSizeK = 1;
        for(double b = stepSizeB; b <= 1; b+=stepSizeB) {
            System.out.println("----- " +b + " ------");
            for(int k = 1; k <= 20; k+=stepSizeK) {

                rtModel = new BM25RetrievalModel(b, k);
                Retriever retriever = generateRetriever(new StemmingPreprocessor(), rtModel);
                
                createTrecFile(retriever, rtModel);
                double result = evaluateWithOfflineTrec();
                
                System.out.println("& B: " +b + " K: " +k +" & " +result + " \\\\");
                
                if(result > bestResult) {
                    bestResult = result;
                    best = rtModel;
                }
                
                if(result < worstResult) {
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
    
    private static void createTrecFile(Retriever retriever, RetrievalModel rtModel) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("trec_eval.9.0/result.txt", "UTF-8");

            List<QueryResultEntry> resultList = retriever.executeQuery(Query.getQueryForID(QueryID.QUERY_6));
            List<String> result = generateTracData(resultList, QueryID.QUERY_6);
            for(String s : result) {
                writer.println(s);
            }
            
            resultList = retriever.executeQuery(Query.getQueryForID(QueryID.QUERY_7));
            result = generateTracData(resultList, QueryID.QUERY_7);
            for(String s : result) {
                writer.println(s);
            }
            writer.close();
        
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static double evaluateWithOfflineTrec() {
        double result = 0;
        try
        {
           Runtime rt = Runtime.getRuntime();
           String cmdString = "trec_eval.9.0/trec_eval -q -c -M1000 trec_eval.9.0/qrels.txt trec_eval.9.0/result.txt";
           Process pr = rt.exec(cmdString);

           BufferedReader input = new BufferedReader(new InputStreamReader(
                 pr.getInputStream()));

           String line = null;

           while ((line = input.readLine()) != null)
           {
              if(line.startsWith("map")) {
                  String[] r = line.replaceAll("\\s+", " ").split(" ");
                  result = Double.parseDouble(r[2]);
              }
           }

           int exitVal = pr.waitFor();
        }
        catch (Exception e)
        {
           System.out.println(e.toString());
           e.printStackTrace();
        }
        
        return result;
    }
    
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
     * Generate a retriever model with the given preprocessor and retrievalmodel
     * 
     * @param preprocessor
     *            The preprocessor to be used
     * @param retrievalModel
     *            The retrievalmodel to be used
     * @return The generated retriever model
     */
    private static Retriever generateRetriever(final Preprocessor preprocessor,
            final RetrievalModel retrievalModel) {
        final File[] files = new File(FileManager.COLLECTION_FOLDER).listFiles();
        final InvertedIndex invertedIndex = new InvertedIndex(preprocessor, files);
        return new Retriever(invertedIndex, retrievalModel);
    }
    
    //<queryID> Q0 <documentID> <rank> <score> <runID>
    private static List<String> generateTracData(List<QueryResultEntry> queryResults, QueryID queryID) {
        List<String> result = new ArrayList<String>();
        
        int rank = 0;
        for(QueryResultEntry q : queryResults) {
            result.add(queryID.getValue() +" Q0 " +q.getDocument().getBaseName() +" " +rank++ +" " +q.getScore() +" 1");
        }
        
        return result;
    }
}
