/* mainConvert a class in the KieranRadixConversions project which uses the methods from the RadixConversions class 
 * and takes the users input to correctly convert the numbers and display output */
/* Kieran Henderson */
/* 3/3/20 */
package radixConversions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainConvert {

	public static void main(String[] args) throws IOException {
		//printing title 
		System.out.println("RADIX CONVERTER");
		System.out.println("*When inputting hexadecimal, please use lowercase*");
		
		//initializing variables
		boolean run = true;
		int conversionMode = 0;
		String number = null;
		String convertedNum = null;
		//array of strings that contains all of the options you can choose from at the start of the program
		String[] options = {"to  Exit","for Decimal to Binary", "for Binary to Decimal", "for Decimal to Hexadecimal", "for Hexadecimal to Decimal", "for Binary to Hexadecimal", "for Hexadecimal to Binary"};
		//array of strings that contains all of the possible outputs f the program 
		String[] output = {"Program Closed", " in decimal converted to binary is ", " in binary converted to decimal is ", " in decimal converted to hexadecimal is ", " in hexadecimal converted to decimal is ", " in binary converted to hexadecimal is ", " in hexadecimal converted to binary is "};
		
		//main loop of the project that will continuously loop
		while (run == true){
			System.out.println("----------------------------------");
			BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
			
			//printing options 
			for (int i = 0; i<=6;i++) {
				System.out.printf("%-6s%-2s%-25s%n", "Press", i, options[i]);
			}
			
			//try-catch for number format exceptions 
			try {
				conversionMode = Integer.parseInt(myInput.readLine());
			} catch(Exception NumberFormatException) {
				System.out.println("Sorry your input was not a number, please restart the program and try again");
				run = false;
				System.exit(0);
			}
			
			//depending on the input call a different method unless it is 0 which means you are trying to quit the program 
			if (conversionMode == 0){
				System.out.println(output[conversionMode]);
				System.exit(0);
				
			//making sure that the inputed option is valid 
			} else if (conversionMode > 6) {
				System.out.println("Sorry, that is not a valid choice, please restart the program and try again");
			System.exit(0);
			}
			
			//asking for users number and converting it into and integer when needed 
			System.out.println("Please enter the number you wish to convert");
			number = myInput.readLine();
			
			
			//selecting correct method
			if (conversionMode == 1) {
				convertedNum = RadixConversions.decToBin(Integer.parseInt(number));
			} else if (conversionMode == 2) {
				convertedNum = Integer.toString(RadixConversions.binToDec(number));
			} else if (conversionMode == 3) {
				convertedNum = RadixConversions.decToHex(Integer.parseInt(number));
			} else if (conversionMode == 4) {
				convertedNum = Integer.toString(RadixConversions.hexToDec(number));
			} else if (conversionMode == 5) {
				convertedNum = RadixConversions.binToHex(number);
			} else if (conversionMode == 6) {
				convertedNum = RadixConversions.hexToBin(number);
			}
			
			//printing the output 
			System.out.println(number+ output[conversionMode] + convertedNum);
		}
		

	}

}
