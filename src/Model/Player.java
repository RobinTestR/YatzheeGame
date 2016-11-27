package Model;
import java.io.IOException;

import View.ScoreSheet;

public class Player {
	private String name;
	private ScoreSheet scoreSheet;

	public Player() {

	}
	
	public void doDices() throws IOException{
		
	}

	public Player(String name) {
		this.name = name;
		scoreSheet = new ScoreSheet();
	}

	public Player(String name, ScoreSheet scoreSheet) {
		this.name = name;
		this.scoreSheet = scoreSheet;
	}

	public ScoreSheet getScoreSheet() {
		return scoreSheet;
	}

	public String getName() {
		return name;
	}
}
