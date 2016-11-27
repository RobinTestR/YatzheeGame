package Model.rules;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Strategy {
	private ArrayList<Integer> dices;

	public Strategy(ArrayList<Integer> diceArrayList) {
		dices = diceArrayList;
	}

	public int getScore(int index) {
		int score = 0;
		switch (index) {
		case 1:
			for (int num : dices) {
				if (num == 1) {
					score++;
				}
			}
			break;
		case 2:
			for (int num : dices) {
				if (num == 2) {
					score += 2;
				}
			}
			break;
		case 3:
			for (int num : dices) {
				if (num == 3) {
					score += 3;
				}
			}
			break;
		case 4:
			for (int num : dices) {
				if (num == 4) {
					score += 4;
				}
			}
			break;
		case 5:
			for (int num : dices) {
				if (num == 5) {
					score += 5;
				}
			}
			break;
		case 6:
			for (int num : dices) {
				if (num == 6) {
					score += 6;
				}
			}
			break;
		case 7:
			if (threeOfAKind()) {
				score = getSum();
			}
			break;
		case 8:
			if (fourOfAKind()) {
				score = getSum();
			}
			break;
		case 9:
			if (fullHouse()) {
				score = 25;
			}
			break;
		case 10:
			if (smallStraight()) {
				score = 30;
			}
			break;
		case 11:
			if (straight()) {
				score = 40;
			}
			break;
		case 12:
			if (yahtzee()) {
				score = 50;
			}
			break;
		case 13:
			score = getSum();
		default:
			break;
		}
		return score;
	}

	public boolean threeOfAKind() {
		int[] array = new int[6];
		for (int i = 0; i < dices.size(); i++) {
			array[dices.get(i) - 1]++;
		}
		for (int num : array) {
			if (num == 3) {
				return true;
			}
		}
		return false;
	}

	public boolean fourOfAKind() {
		int[] array = new int[6];
		for (int i = 0; i < dices.size(); i++) {
			array[dices.get(i) - 1]++;
		}
		for (int num : array) {
			if (num == 4) {
				return true;
			}
		}
		return false;
	}

	public boolean fullHouse() {
		int[] array = new int[6];
		for (int i = 0; i < dices.size(); i++) {
			array[dices.get(i) - 1]++;
		}
		boolean flag = false;
		int count = 0;
		for (int num : array) {
			if (num == 3) {
				flag = true;
			} else if (num == 0) {
				count++;
			}
		}
		if (flag && count == 4) {
			return true;
		}
		return false;
	}

	public boolean smallStraight() {
		Collections.sort(dices);
		HashSet<Integer> set = new HashSet<>(dices);
		ArrayList<Integer> tempArrayList = new ArrayList<>(set);
		boolean flag = true;
		for (int i = 0; i < tempArrayList.size() - 2; i++) {
			if (tempArrayList.get(i + 1) - tempArrayList.get(i) != 1) {
				flag = false;
			}
		}
		if (flag) {
			return true;
		}
		flag = true;
		for (int i = 1; i < tempArrayList.size() - 1; i++) {
			if (tempArrayList.get(i + 1) - tempArrayList.get(i) != 1) {
				flag = false;
			}
		}
		return flag;
	}

	public boolean straight() {
		Collections.sort(dices);
		for (int i = 0; i < dices.size() - 1; i++) {
			if (dices.get(i + 1) - dices.get(i) != 1) {
				return false;
			}
		}
		return true;
	}

	public boolean yahtzee() {
		Collections.sort(dices);
		if (dices.get(dices.size() - 1) == dices.get(0)) {
			return true;
		}
		return false;
	}

	public int getSum() {
		int sum = 0;
		for (int num : dices) {
			sum += num;
		}
		return sum;
	}
}
