package main;

import compiler.generated.Parser;
import compiler.generated.Scanner;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainTest {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Parser parser = new Parser(scanner);
		Symbol s = null;

		System.out.println( ANSI_PURPLE + "Compilando o programa: " + filePath + ANSI_RESET );
		try {
			System.out.println( ANSI_WHITE_BACKGROUND + " " + ANSI_BLUE);
			s = parser.parse();
			System.out.println( ANSI_RESET + " " + ANSI_RESET);
			System.out.println(ANSI_GREEN + "The compilation process was successfully finished!" + ANSI_RESET);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
