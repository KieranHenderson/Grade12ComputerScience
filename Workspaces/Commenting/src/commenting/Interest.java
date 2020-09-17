/* Interest is a program which takes the users input and calculates how much money you would have at an inputed rate after one term */
/* Kieran Henderson */
/* 2/25/20 */
package commenting;

import java.io.*;

public class Interest
{
	public static void main (String [] args) throws IOException
	{
		// Declaring variables
		double principle, rate, interest;
		String inBalance, inRate;
		
		BufferedReader myInput = new BufferedReader (new InputStreamReader (System.in));
		
		//get user input to see how much money they are starting with and then parse it to an integer and assign it to a variable 
		System.out.println ("Please enter the amount of money you are starting with: ");
		inBalance = myInput.readLine ();
		principle = Integer.parseInt (inBalance);
		
		// Get user input to see what their rate in and then parse it to an integer and assign it to a variable 
		System.out.println ("Enter current interest rate, as a percent: ");
		inRate = myInput.readLine ();
		rate = Integer.parseInt (inRate);
		
		// Calculations 
		interest = principle * (convertToDec (rate));
		principle += interest;
		
		// Print the results to the user
		System.out.println ("New Balance = " + principle);
		System.out.println ("Interest = " + interest);
	}
	
	// Method to simplify code that converts an inputed  to a decimal
	public static double convertToDec (double amount)
	{
		return amount / 100;
	}
}