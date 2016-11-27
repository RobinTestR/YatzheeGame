package View;
import java.util.ArrayList;

import Model.rules.Strategy;

/**
 * A class represents the score sheet.
 * 
 * @author Zeyuan Li
 * 
 */
public class ScoreSheet {
	private int counts;
	private int bonusYahtzee;

	private String[] description = { "Aces", "Twos", "Threes", "Fours",
			"Fives", "Sixs", "ThreeOfAKind", "FourOfAKind", "FullHouse",
			"SmallStraight", "Straight", "Yahtzee", "Change" };

	private int[] scores;
	private boolean[] flags;

	public ScoreSheet() {
		counts = 0;
		scores = new int[13];
		flags = new boolean[13];
	}

	public ScoreSheet(int[] scoreArr, boolean[] flagArr,int bonus) {
		counts = 0;
		scores = new int[13];
		flags = new boolean[13];
		for (int i = 0; i < flagArr.length; i++) {
			flags[i] = flagArr[i];
			if (flags[i]) {
				counts++;
				scores[i] = scoreArr[i];
			}
		}
		this.bonusYahtzee=bonus;
	}

	public void displayAvabileSheetBlank() {
		System.out.println("You can put your dice into following strategy:");
		for (int i = 0; i < flags.length; i++) {
			if (!flags[i]) {
				System.out.println((i + 1) + ":" + description[i]);
			}
		}
	}

	public boolean canInsert(int index) {
		if (index <= 0 || index > 13) {
			System.out.println("The insert index is out of boundary!");
			return false;
		}
		if (flags[index - 1]) {
			System.out
					.println("The insert index has already been occupied by a strategy!");
			return false;
		}
		return true;
	}

	public void insertStrategy(int index, ArrayList<Integer> dices) {
		int score = getScoreByStrategy(index, dices);
		flags[index - 1] = true;
		scores[index - 1] = score;
		counts++;
	}

	private int getScoreByStrategy(int index, ArrayList<Integer> dices) {
		Strategy strategy = new Strategy(dices);
		if (flags[11] && strategy.yahtzee()) {
			bonusYahtzee++;
		}
		return strategy.getScore(index);
	}

	public boolean gameOver() {
		return counts == 13;
	}

	public int getTotalSocres() {
		int totalScores = 0;
		for (int i = 0; i < 6; i++) {
			totalScores += scores[i];
		}
		if (totalScores >= 63) {
			totalScores += 35;
		}
		for (int i = 6; i < 13; i++) {
			totalScores += scores[i];
		}
		totalScores += bonusYahtzee * 100;
		return totalScores;
	}

	public void displayScoreSheet() {
		int aboveScore = 0;
		for (int i = 0; i < 6; i++) {
			aboveScore += scores[i];
			System.out.println(description[i] + ":" + scores[i]);
		}
		System.out.println("Above Score:" + aboveScore);
		if (aboveScore >= 63) {
			System.out.println("Bonus Score" + 35);
		}
		int belowScore = 0;
		for (int i = 6; i < 13; i++) {
			belowScore += scores[i];
			System.out.println(description[i] + ":" + scores[i]);
		}
		System.out.println("Below Score:" + belowScore);
		if (bonusYahtzee > 0) {
			System.out.println("Bonus Yahtzee Score:" + belowScore * 100);
		}
		System.out.println("Total socre:" + getTotalSocres());

	}
	
	public boolean getFlag(int index){
		return flags[index];
	}
	
	public int getScore(int index){
		return scores[index];
	}
	
	public int getBonusYzhtzee(){
		return bonusYahtzee;
	}
	

}
