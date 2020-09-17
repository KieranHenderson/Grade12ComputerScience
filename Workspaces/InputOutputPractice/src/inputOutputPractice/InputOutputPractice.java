package inputOutputPractice;

import java.io.*;
import java.util.Scanner;

public class InputOutputPractice {
	/* BufferReader input */
	public static void main(String[] args) throws IOException {
		BufferedReader myInput = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Please enter the in put type you would like to use (1,2,3): ");
		String input = myInput.readLine();
		int Input = Integer.parseInt(input);
		if (Input == 1)
		{
			System.out.println("Please enter your name");
			String myName = myInput.readLine();
			System.out.println("Hello " + myName + ", how are you?");
		}else if (Input == 2){
			String stringNum;
			int convertToNum, square;
			
			System.out.println("Please entre the side length you wish to square: ");
			stringNum = myInput.readLine();
			
			convertToNum = Integer.parseInt(stringNum);
			square = convertToNum * convertToNum;
			
			System.out.println("the square of your side is: " + square);
		}else if (Input == 3) {
			int radius;
			final double PI = 3.14159;
			double area;
			Scanner scannerInput = new Scanner(System.in);
			
			System.out.println("Please enter the Radius: ");
			radius = scannerInput.nextInt();
			
			scannerInput.close();
			area = PI * (radius*radius);
			System.out.println("The area of your circle is: " + area);
		}else {
			System.out.println("You did not enter a valid input, please restart the program and try again");
		}
	}

}
