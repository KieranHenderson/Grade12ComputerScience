/* KieranCompoundComparison is a program which takes the users input and calculates how much money you would have at an inputed rate and term if you used yearly, monthly and bi-weekly in order to easily compare them */
/* Kieran Henderson */
/* 2/10/20 */
package assignment1;

import java.io.*;

public class KieranCompoundComparison {

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Please enter your principle, your term and your intrest rate all seperated by commas. Ex. 1000,5,10."); //asking the user to input their principle, term and interest rate 
		
		String userInput = input.readLine().replaceAll(" ", ""); //remove all the white spaces from the string inputed to allow for separation into an array
		String[] arrayInput = userInput.split(","); //separate all the value using a comma as the separator
		
		/* user input variables */
		double principle = Integer.parseInt(arrayInput[0]); //first item in array is equal to the principle 
		double rate = Double.parseDouble(arrayInput[2])/100; //third item in the array is equal to the rate/100
		int term = Integer.parseInt(arrayInput[1]); //second item in the array is equal to the term
		
		/* Interest variables */
		double interest = principle * rate;
		double monthInterest = interest/12;
		double biweekInterest = interest/26;
		
		/* Balance variables */
		double yearly = principle; //all starts at the principle that the user inputed 
		double monthly = principle;
		double biweekly = principle;
		

		for (int i = term; i > 0; i--) //runs as many times as the term dictates 
		{
			yearly+=interest; 
			interest = interest + interest*rate; //recalculate the interest
			
			for (int j = 12; j > 0; j--) //runs 12 (12 months in a year) times
			{
				monthly+=monthInterest;
				monthInterest=monthInterest+monthInterest*(rate/12); //recalculate the months interest 
			}
			
			for (int k = 26; k>0;k--) //runs 26 (26 2 week segments in a year) times
			{
				biweekly+=biweekInterest;
				biweekInterest = biweekInterest + biweekInterest*(rate/26); //recalculate the months interest
			}
		}
		
		System.out.printf("%-16s%-15s%-16s%-16s%-16s%-15s%n", "Principle", "Term", "Intrest", "Yearly", "Monthly", "Bi-Weekly"); //use printf to print the header to the screen
		System.out.printf("$%-15.2f%-15d%%%-15.0f$%-15.2f$%-15.2f$%-15.2f%n", principle, term, rate*100, yearly, monthly, biweekly); //use printf to print the final results to the console in line with the headers

}
}
