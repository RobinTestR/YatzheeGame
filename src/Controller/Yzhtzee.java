package Controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Model.ComputerPlayer;
import Model.HumanPlayer;
import Model.Player;
import View.ScoreSheet;

import java.util.Random;

public class Yzhtzee {

	private ArrayList<Player> players;
	private Random random;
	private File file;
	private String fileName;

	public Yzhtzee(String fileName) throws IOException {
		this.fileName = fileName;
		players = new ArrayList<>();
		random = new Random();
		if (fileName != null) {
			file = new File(fileName);
		}
		boolean gameFlag = true;
		if (file != null && file.exists()) {
			System.out.println("You resume the game");
			gameFlag = loadData(fileName);
		}
		if (file == null || !file.exists() || !gameFlag) {
			if (file == null) {
				System.out.println("A new game:");
			} else if (file != null && !file.exists()) {
				System.out
						.println("The resume game doesn't exist,there will be a new game:");
			} else if (file != null && file.exists() && !gameFlag) {
				System.out
						.println("The resume game is over,there will be a new game:");
			}
			System.out.print("Please enter your name:");
			String name = getString();
			Player me = new HumanPlayer(name);
			System.out.print("Please select the number of players(1-5):");
			int num = getInt();
			boolean flag = true;
			while (flag) {
				flag = false;
				if (num > 5 || num <= 0) {
					flag = true;
					System.out
							.print("Please select the correct number of players(1-5):");
					num = getInt();
				}
			}
			ArrayList<Player> otherPlayers = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				System.out.print("The " + i + " player type(Human,Computer):");
				String type = getString();
				boolean typeFlag = true;
				while (typeFlag) {
					typeFlag = false;
					if (!type.equals("Human") && !type.equals("Computer")) {
						typeFlag = true;
						System.out
								.print("Please select the correct player type(Human,Computer):");
						type = getString();
					}
				}
				if (type.equals("Human")) {
					System.out.print("Please enter the human player name:");
					String humanName = getString();
					Player humanPlayer = new HumanPlayer(humanName);
					otherPlayers.add(humanPlayer);
				} else {
					System.out.print("Please enter the computer player name:");
					String computerName = getString();
					Player computerPlayer = new ComputerPlayer(computerName);
					otherPlayers.add(computerPlayer);
				}
			}
			System.out.println("Rolling dices for the order:");
			HashMap<Player, Integer> orderMap = new HashMap<>();
			System.out.print("Your five dices:");
			int dices = 0;
			for (int i = 0; i < 5; i++) {
				int dice = random.nextInt(6) + 1;
				System.out.print(dice + " ");
				dices += dice;
			}
			System.out.println(" sum of dices:" + dices);
			orderMap.put(me, dices);
			for (int i = 0; i < num; i++) {
				dices = 0;
				System.out.print(otherPlayers.get(i).getName()
						+ "' five dices:");
				for (int j = 0; j < 5; j++) {
					int dice = random.nextInt(6) + 1;
					System.out.print(dice + " ");
					dices += dice;
				}
				System.out.println(" sum of dices:" + dices);
				orderMap.put(otherPlayers.get(i), dices);
			}
			ArrayList<Map.Entry<Player, Integer>> infoIdsList = new ArrayList<Map.Entry<Player, Integer>>(
					orderMap.entrySet());
			Collections.sort(infoIdsList,
					new Comparator<Map.Entry<Player, Integer>>() {

						@Override
						public int compare(Entry<Player, Integer> o1,
								Entry<Player, Integer> o2) {
							if (o1.getValue() - o2.getValue() != 0) {
								return o1.getValue() - o2.getValue();
							}
							return o1.getKey().getName()
									.compareTo(o2.getKey().getName());
						}
					});
			for (int i = infoIdsList.size() - 1; i >= 0; i--) {
				players.add(infoIdsList.get(i).getKey());
			}
			System.out.print("The order is ");
			for (int i = 0; i < players.size(); i++) {
				System.out.print(players.get(i).getName() + " ");
			}
			System.out.println();
		}
		play(gameFlag);
	}

	public void play(boolean flag) throws IOException {
		while (flag) {
			for (int i = 0; i < players.size(); i++) {
				players.get(i).doDices();
			}
			if (players.get(players.size() - 1).getScoreSheet().gameOver()) {
				flag = false;
				System.out.println("Game Over!");
				int index = 0;
				int sumScore = players.get(0).getScoreSheet().getTotalSocres();
				for (int i = 1; i < players.size(); i++) {
					if (players.get(i).getScoreSheet().getTotalSocres() > sumScore) {
						sumScore = players.get(i).getScoreSheet()
								.getTotalSocres();
						index = i;
					}
				}
				System.out.println("The winer is "
						+ players.get(index).getName());
				System.out
						.print("Please input the file path to save the data:");
				String filePath = getString();
				saveData(filePath, true);
				System.out.println("The data has been saved.");
				System.out.println("The game is exit!");
				System.exit(0);
			}
			if (flag) {
				System.out.print("You can choose save and quit the game(Y/N)");
				String option = getString();
				boolean optionFlag = true;
				while (optionFlag) {
					optionFlag = false;
					if ("Y".equals(option)) {
						System.out.println("Save and quit the game!");
						if (file != null) {
							saveData(fileName, false);
						} else {
							System.out
									.print("Please input the file path to save the data:");
							String filePath = getString();
							saveData(filePath, false);
						}
						System.out.println("The data has been saved.");
						System.out.println("The game is exit!");
						System.exit(0);
					} else if ("N".equals(option)) {
						System.out.println("Go on!");
					} else {
						optionFlag = true;
						System.out
								.print("You need to input the correct option:");
						option = getString();
					}
				}

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

	public static int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}

	private boolean loadData(String fileName) {
		boolean flag = true;
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);
			String lineString = null;
			lineString = in.readLine();
			String[] oneArr = lineString.split("\\s+");
			String gameState = oneArr[0];
			int playerNum = Integer.parseInt(oneArr[1]);
			if (gameState.equals("T")) {
				System.out.println("The game is over!");
				flag = false;
			} else {
				for (int i = 0; i < playerNum; i++) {
					in.readLine();
				}
				while ((lineString = in.readLine()) != null) {
					String[] arr = new String[5];
					arr = lineString.split("\\|");
					String name = arr[1];
					boolean[] flags = new boolean[13];
					String[] flagsArr = arr[2].split(":");
					for (int i = 0; i < flagsArr.length; i++) {
						if (flagsArr[i].equals("T")) {
							flags[i] = true;
						} else {
							flags[i] = false;
						}
					}
					String[] scoresArr = arr[3].split(":");
					int[] scores = new int[13];
					int bonus = Integer.parseInt(scoresArr[13]);
					for (int i = 0; i < 13; i++) {
						scores[i] = Integer.parseInt(scoresArr[i]);
					}
					ScoreSheet scoreSheet = new ScoreSheet(scores, flags, bonus);
					Player player = null;
					if (arr[4].equals("Human")) {
						player = new HumanPlayer(name, scoreSheet);
					} else {
						player = new ComputerPlayer(name, scoreSheet);
					}
					players.add(player);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	private void saveData(String fileName, boolean flag) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		if (flag) {
			bw.write("T " + players.size() + "\n");
		} else {
			bw.write("F " + players.size() + "\n");
		}
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int i = 0; i < players.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(df.format(date) + "|");
			stringBuilder.append(players.get(i).getName() + "|");
			stringBuilder.append(players.get(i).getScoreSheet()
					.getTotalSocres()
					+ "\n");
			bw.write(stringBuilder.toString());
		}
		for (Player player : players) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(df.format(date) + "|");
			stringBuilder.append(player.getName() + "|");
			for (int i = 0; i < 12; i++) {
				stringBuilder
						.append((player.getScoreSheet().getFlag(i) == true ? "T"
								: "F")
								+ ":");
			}
			stringBuilder
					.append((player.getScoreSheet().getFlag(12) == true ? "T"
							: "F") + "|");
			for (int i = 0; i < 13; i++) {
				stringBuilder.append(player.getScoreSheet().getScore(i) + ":");
			}
			stringBuilder
					.append(player.getScoreSheet().getBonusYzhtzee() + "|");
			if (player instanceof HumanPlayer) {
				stringBuilder.append("Human\n");
			} else {
				stringBuilder.append("Computer\n");
			}
			bw.write(stringBuilder.toString());
		}
		bw.close();
	}
}
