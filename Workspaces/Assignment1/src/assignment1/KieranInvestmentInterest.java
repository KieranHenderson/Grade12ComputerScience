/* KieranInvesementInterest is a program which takes the users input and calculates how much money you would have at an inputed rate and term*/
/* Kieran Henderson */
/* 2/10/20 */

package assignment1;

import java.io.*;

public class KieranInvestmentInterest {

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		
			System.out.println("Please enter you principle, interest rate and the term sperating each by commas. Ex. 1000,10,5."); //print to the console asking the user for their principle, interest rate and term

			String userInput = input.readLine().replaceAll(" ", ""); //take the user input and remove all the white spaces from it
			String[] arrayInput = userInput.split(","); //separate all the value using a comma as the separator
			
			/* User inputed variables */
			double principle = Integer.parseInt(arrayInput[0]); //first item in array is equal to the principle
			double rate = Double.parseDouble(arrayInput[1])/100; //second item in the array is equal to the rate/100
			int term = Integer.parseInt(arrayInput[2]); //third item in the array is equal to the term
			
			/* Calculated variables */
			double interest = principle * rate; 
			double balance = principle + interest; 
			
			System.out.printf("%-15s%-16s%-16s%-16s%n", "Year", "Principle", "Interest", "Balance"); //print the header to the console using printf to format it
	
			for (int i = term; i >0; i--)
			{
				System.out.printf("%-15d$%-15.2f$%-15.2f$%-15.2f%n", term-(i-1), principle, interest, balance); //print the values to the console using printf to format
				
				principle = principle + interest;
				interest = interest + interest*rate; 
				balance = balance + interest; 
			}

}
}