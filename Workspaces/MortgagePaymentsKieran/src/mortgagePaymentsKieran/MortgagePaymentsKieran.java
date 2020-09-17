/* mortgagePaymentsKieran is a program which takes the users input and calculates how much money you would have to pay each month and much total interest you would have to pay at a given principle, interest rate and term */
/* Kieran Henderson */
/* 3/3/20 */
package mortgagePaymentsKieran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MortgagePaymentsKieran {
	
	static double principle;
	static double interestRate;
	static double term;
	
	public static void main(String[] args) throws IOException {
		
		// setting up an input reader
		BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
		
		// receiving user input for their principle and checking to make sure it is a valid input
		System.out.println("Please input your principle");
		try {
			principle = Integer.parseInt(myInput.readLine());
		} catch(Exception NumberFormatException){
			System.out.println("sorry your principle is not a number, please restart the program");
			System.exit(0); //quit program if the user inputs a wrong input
		}
		
		// receiving user input for their interest rate and checking to make sure it is a valid input
		System.out.println("Please input you interest rate");
		try {
			interestRate = Integer.parseInt(myInput.readLine());
			interestRate = interestRate/1200;
		} catch(Exception NumberFormatException){
			System.out.println("Sorry your interestRate is not a number, please restart the program");
			System.exit(0); //quit program if the user inputs a wrong input
		}
		
		// receiving player input for their term and checking to make sure it is a valid input
		System.out.println("Please input your term");
		try {
			term = Integer.parseInt(myInput.readLine());
			term = term*12;
		} catch(Exception NumberFormatException){
			System.out.println("Sorry your interestRate is not a number, please restart the program");
			System.exit(0); //quit program if the user inputs a wrong input
		}
		
		
		//using the created methods to calculate the required results
		double mortgagePayment = mortgagePayment(interestRate,principle,term);
		double interestPaid = interestPaid(principle,mortgagePayment,term);
		
		//printing the correct calculations 
		System.out.println("If you have a $"+ principle+ " mortgage at "+ interestRate*1200+"%, over " +term/12+" years you will pay $"+Math.round(mortgagePayment)+ " per month and "
				+ "it will cost you $"+ Math.round(interestPaid)+" in interest over the lifetime of your mortgage");

	}
	
	//method for calculating the monthly mortgage payments
	public static double mortgagePayment (double interestRate, double principle, double term) {
		//uses provided formula to calculate monthly mortgage payments
		double mortgagePayment = (interestRate*principle)/(1-(Math.pow((1+interestRate), -term)));
		return mortgagePayment;
	}
	
	//method for calculating the total interest paid over the lifetime of the mortgage
	public static double interestPaid(double principle, double mortgagePayment, double term) {
		//uses provided formula to calculate the total interest paid
		double interestPaid = mortgagePayment*term - principle;
		return interestPaid;
	}

}
