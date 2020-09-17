package randomInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiceGame {

	public static void main(String[] args) throws IOException {
		BufferedReader myInput = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("welcome to the DiceGame");
		
		int point;
		int guess = 0;
		int rollNum = 0;
		point = (int)(Math.random() * 6)+1;
		System.out.println("the point to match is: " + point);
		
		while (guess != point){
			rollNum += 1;
			guess = (int)(Math.random() * 6)+1;
			System.out.println("roll " + rollNum + " is a " + guess);
		}
		System.out.println("it took the computer " + rollNum +" times to reach the point.");
		System.out.println("would you like to continue? 'y' or 'n'?");
		String input = myInput.readLine();
		input = input.trim();
		if (input.equals("y")) {
			System.out.println("");
			main(args);
		}
		else {
			System.out.println("goodbye");
		}

		
	}

}
