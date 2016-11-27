package View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Controller.Yzhtzee;



public class Game {
	public static final String FILE_PATH_STRING = "data.txt";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Yzhtzee yzhtzee;
		while (true) {
			System.out.println("You can do following things:");
			System.out.println("1:New a game");
			System.out.println("2:Resume a non finished game");
			System.out.println("3:Quit");
			char choice = getChar();
			switch (choice) {
			case '1':
				yzhtzee = new Yzhtzee(null);
				break;
			case '2':
				System.out.print("Please input the file name that you want to resume:");
				String fileName=getString();
				yzhtzee = new Yzhtzee(fileName);
				break;
			case '3':
				System.out.println("The game is exit.");
				System.exit(0);
				break;
			default:
				 System.out.println("Invalid enrty!");
				break;
			}
		}
	}

	public static String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}

	public static char getChar() throws IOException {
		String s = getString();
		return s.charAt(0);
	}

}
