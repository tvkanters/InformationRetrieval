package com.uva.ir;

import java.io.File;

import com.uva.ir.evaluation.EvaluationResult;
import com.uva.ir.evaluation.Query;
import com.uva.ir.evaluation.Query.QueryID;
import com.uva.ir.evaluation.RetrievalModelEvaluator;
import com.uva.ir.indexing.InvertedIndex;
import com.uva.ir.preprocessing.Preprocessor;
import com.uva.ir.preprocessing.SimplePreprocessor;
import com.uva.ir.retrieval.Retriever;
import com.uva.ir.retrieval.models.BM25RetrievalModel;
import com.uva.ir.retrieval.models.RetrievalModel;
import com.uva.ir.retrieval.models.TfIdfRetrievalModel;
import com.uva.ir.utils.FileManager;

public class Evaluator {

    public static void main(final String[] args) {
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
}
