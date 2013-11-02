package com.uva.ir.evaluation;

import java.util.List;
import java.util.Set;

import com.uva.ir.evaluation.Query.QueryID;
import com.uva.ir.retrieval.QueryResultEntry;
import com.uva.ir.retrieval.Retriever;

public class RetrievalModelEvaluator {

    /**
     * Evaluate only the top 10 results for the given queryID
     * 
     * @param preprocessor
     * @param retrievalModel
     * @param queryID
     * @return
     */
    public static EvaluationResult evaluateTop10(final Retriever retriever, final QueryID queryID) {
        return evaluate(retriever, queryID, 10);
    }

    /**
     * Evaluate only the top 10 results for the given queryID
     * 
     * @param preprocessor
     * @param retrievalModel
     * @param queryID
     * @return
     */
    public static EvaluationResult evaluateTop100(final Retriever retriever, final QueryID queryID) {
        return evaluate(retriever, queryID, 100);
    }

    /**
     * Evaluate the Top evaluationNumber results for the given queryID
     * 
     * @param preprocessor
     * @param retrievalModel
     * @param queryID
     * @param evaluationNumber
     * @return
     */
    public static EvaluationResult evaluate(final Retriever retriever, final QueryID queryID,
            int evaluationNumber) {

        Set<String> relevantDocuments = Query.getRelevantDocuments(queryID);

        List<QueryResultEntry> results = retriever.executeQuery(Query.getQueryForID(queryID));

        if (results.size() < evaluationNumber) {
            evaluationNumber = results.size();
        }

        int firstMistake = -1;
        int amountOfRelevance = 0;
        for (int i = 0; i < evaluationNumber; ++i) {
            if (relevantDocuments.contains(results.get(i).getDocument().getName())) {
                ++amountOfRelevance;
            } else if (firstMistake == -1) {
                firstMistake = i;
            }
        }

        return new EvaluationResult(evaluationNumber, amountOfRelevance, firstMistake, queryID);
    }
}
