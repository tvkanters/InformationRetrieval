package com.uva.ir;

import java.io.File;
import java.io.FileNotFoundException;
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
import com.uva.ir.retrieval.models.RetrievalModel;
import com.uva.ir.retrieval.models.TfIdfRetrievalModel;
import com.uva.ir.utils.FileManager;

public class Evaluator {

    
    
    public static void main(final String[] args) {
        try {
            evaluateTRAC();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private static void evaluateTRAC() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("trec_eval.9.0/result.txt", "UTF-8");
        
        RetrievalModel rtModel = new BM25RetrievalModel();
        Retriever retriever = generateRetriever(new StemmingPreprocessor(), rtModel);

        
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
//        rtModel = new TfIdfRetrievalModel();
//        retriever = generateRetriever(new SimplePreprocessor(), rtModel);
//        retriever.executeQuery(Query.getQueryForID(QueryID.QUERY_6));
//        retriever.executeQuery(Query.getQueryForID(QueryID.QUERY_7));
        
        writer.close();
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
