package Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import Controller.Yzhtzee;
import View.ScoreSheet;


/**
 * @author Zeyuan Li
 *
 */
public class HumanPlayer extends Player {
	@Override
	public void doDices() throws IOException {
		System.out.println("Human Player "+super.getName()+" rolling dices:");
		ArrayList<Integer> dicesArrayList=new ArrayList<>();
		Random random=new Random();
		//the first rolling dices
		System.out.print(1+" rolling:");
		for(int i=0;i<5;i++){
			int dice=1+random.nextInt(6);
			System.out.print(" "+dice);
			dicesArrayList.add(dice);
		}
		System.out.println();
		//the second and the thrid rolling dices
		for(int i=0;i<2;i++){
			
			System.out.print("Please input the index you want to reroll(1-5):");
			String indexString=Yzhtzee.getString();
			String[] indexStringArray=indexString.split("\\s+");
			
			TreeSet<Integer> indexsSet=new TreeSet<>();
			
			boolean flag=true;
			while(flag){
				flag=false;
				for(String str:indexStringArray){
					if (isNum(str)&&Integer.parseInt(str)>=1&&Integer.parseInt(str)<=5) {
						indexsSet.add(Integer.parseInt(str));
					}else {
						flag=true;
					}
				}
				if (flag) {
					System.out.print("Please input the correct index you wannt reroll(1-5):");
					indexString=Yzhtzee.getString();
					indexStringArray=indexString.split("\\s+");
					indexsSet.clear();
				}
			}
			System.out.print("Reroll the following index:");
			for(int num:indexsSet){
				System.out.print(" "+num);
			}
			System.out.println();
			for(int num:indexsSet){
				int newDice=1+random.nextInt(6);
				dicesArrayList.set(num-1, newDice);
			}
			System.out.print((i+2)+" rolling:");
			for(int dice:dicesArrayList){
				System.out.print(" "+dice);
			}
			System.out.println();
		}
		
		super.getScoreSheet().displayAvabileSheetBlank();
		System.out.println("Please input the index of strategy:");
		int index=Yzhtzee.getInt();
		
		while(true){
			if (super.getScoreSheet().canInsert(index)) {
				break;
			}
			System.out.print("Please input the correct index of strategy:");
			index=Yzhtzee.getInt();
		}
		
		System.out.println(super.getName()+" select the "+(index)+" strategy!" );
		super.getScoreSheet().insertStrategy(index, dicesArrayList);
		System.out.println("Human "+super.getName()+"' current ScoreSheet:");
		super.getScoreSheet().displayScoreSheet();
	}
	
	public boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"); 
	}

	public HumanPlayer(String name) {
		super(name);
	}
	
	public HumanPlayer(String name, ScoreSheet scoreSheet) {
		super(name, scoreSheet);
	}
}
