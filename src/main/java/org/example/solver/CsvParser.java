package org.example.solver;

public class CsvParser {
	private final static String DELIMITER = ",";

	public static String[] parseCsvString(String stringToParse) {
		return stringToParse.split(DELIMITER);
	}
}
