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
  public static final String ANSI_GREEN = "\u001B[32m";

	public static void main(String[] args) throws IOException {
		String filePath = "test/Test.pas";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(args[0])));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Parser parser = new Parser(scanner);
		Symbol s = null;
		try {
			s = parser.parse();
			System.out.println(ANSI_GREEN + "The compilation process was successfully finished!" + ANSI_RESET);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
