package compiler.util;

public class Logger {

	public static boolean debugging = true;

	public static void print(String value) {
		if(debugging) {
			System.out.println(value);
		}
	}
}
