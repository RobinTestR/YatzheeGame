package Model;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import View.ScoreSheet;


/**
 * @author Zeyuan Li
 *
 */
public class ComputerPlayer extends Player {
	@Override
	public void doDices() {
		System.out.println("Computer Player "+super.getName()+" rolling dices:");
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
			
			int reRollingNum=1+random.nextInt(5);
			TreeSet<Integer> indexsSet=new TreeSet<>();
			while(indexsSet.size()!=reRollingNum){
				indexsSet.add(1+random.nextInt(5));
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
		
		int index=-1;
		while(true){
			index=random.nextInt(13);
			if (super.getScoreSheet().canInsert(index)) {
				break;
			}
		}
		System.out.println(super.getName()+" select the "+(index+1)+" strategy!" );
		super.getScoreSheet().insertStrategy(index+1, dicesArrayList);
		System.out.println("Robot "+super.getName()+"' current ScoreSheet:");
		super.getScoreSheet().displayScoreSheet();
	}

	public ComputerPlayer(String name) {
		super(name);
	}
	
	public ComputerPlayer(String name, ScoreSheet scoreSheet) {
		super(name, scoreSheet);
	}
}
