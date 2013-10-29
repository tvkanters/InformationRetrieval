package com.uva.ir;

import java.util.Scanner;

/**
 * Prepares the search program and asks the user for input.
 */
public class Initialiser {

    /** THe command to quit the search */
    private final static String CMD_QUIT = "quit";

    public static void main(String[] args) {
        
        // Repeatedly ask the user for search queries
        final Scanner inputReader = new Scanner(System.in);
        while (true) {
            System.out.print("Google Search: ");
            final String query = inputReader.next();

            // Decide what to do with the input
            if (query.equals(CMD_QUIT)) {
                break;
            } else {
                System.out.println("Your query: " + query);
            }
        }
        inputReader.close();
    }

}
