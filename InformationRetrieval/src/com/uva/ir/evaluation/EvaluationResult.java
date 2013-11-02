package com.uva.ir.evaluation;

import com.uva.ir.evaluation.Query.QueryID;

public class EvaluationResult {

    /** The number of results, starting from the highest ranked, to be evaluated */
    private final int mEvaluationNumber;

    /** The used queryID */
    private final QueryID mQueryID;

    /** The amount of relevant documents found in the first mEvaluationNumber positions */
    private final int mRelevantDocuments;

    /** The first position where an unrelevant document is ranked higher than a relevant document */
    private final int mFirstMistake;

    public EvaluationResult(final int evaluationNumber, final int relevantDocuments,
            final int firstMistake, final QueryID queryID) {
        mEvaluationNumber = evaluationNumber;
        mRelevantDocuments = relevantDocuments;
        mFirstMistake = firstMistake;
        mQueryID = queryID;
    }

    /**
     * @return The amount of relevant documents found in the first evaluationNumber positions
     */
    public int getRelevantDocuments() {
        return mRelevantDocuments;
    }

    /**
     * @return The first position where an unrelevant document is ranked higher than a relevant
     *         document
     */
    public int getFirstMistake() {
        return mFirstMistake;
    }

    /**
     * @return The number of results, starting from the highest ranked, to be evaluated
     */
    public int getEvaluationNumber() {
        return mEvaluationNumber;
    }

    /**
     * @return the queryID
     */
    public QueryID getQueryID() {
        return mQueryID;
    }

    @Override
    public String toString() {
        return "Relevant documents: " + mRelevantDocuments + "/" + mEvaluationNumber + "\n"
                + "First Mistake: " + mFirstMistake + "\n" + "Ratio: "
                + ((double) mRelevantDocuments / mEvaluationNumber);
    }
}
