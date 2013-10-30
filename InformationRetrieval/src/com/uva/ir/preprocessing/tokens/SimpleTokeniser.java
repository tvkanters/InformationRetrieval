package com.uva.ir.preprocessing.tokens;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleTokeniser implements Tokeniser {

    /**
     * Splits the contents based on spaces, turns accented characters into regular ones and strips
     * the tokens from non-alphanumerical characters.
     * 
     * @param query
     *            The query to split up
     * 
     * @return The tokens from within the query
     */
    @Override
    public List<String> retrieveTokens(final String contents) {
        String deaccentedContents = Normalizer.normalize(contents, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        deaccentedContents = pattern.matcher(deaccentedContents).replaceAll("");

        return Arrays.asList(deaccentedContents.replaceAll("[^\\w ]", "").split(" "));
    }

}
