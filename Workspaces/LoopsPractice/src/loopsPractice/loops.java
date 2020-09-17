package loopsPractice;

public class loops {
	public static void main(String[] args) 
	{
		/*For loop practice */
		System.out.println("The numbers from 1 to 28 counting by threes are");
		for (int number = 1; number <= 28; number+=3)  //number starts at 1 and increment by 1 each time to the value of 10
		{
			System.out.println(number); //print sum
		}
		System.out.println("The numbers from 0 to 50 counting by fives are");
		for (int number = 0; number <=50; number+=5)  //number starts at 0 and increment by 5 each time to the value of 50
		{
			System.out.println(number); //print result 
		}
		System.out.println("The numbers from 24 to 0 conting down by twos are");
		for (int number = 24; number >=0 ; number-=2)  //number starts at 24 and increment by -2 each time to the value of 0
		{
			System.out.println(number); //print result 
		}
		
		System.out.println("The numbers from 0 to 99 are");
		int count = 0;
		for (int number = 0; number <=9; number++)  //number starts at 0 and increment by 1 each time to the value of 9 (runs 10 times)
		{
			for (int i = 0; i <=9; i++)  //number starts at 0 and increment by 1 each time to the value of 9 (runs 10 times) because the other loop runs this loop 10 times, the code in this loop is run 100 times
			{
				System.out.println(count); //print result 
				count++; //add one to the total count each time the loop runs
			}
		}
		
		/* While loop practice */		
		System.out.println("The numbers from 1 to 28 counting by threes are");
		int number = 1; //declare initial variable 
		while (number<=28) //while the variable is less than 29 run code below 
		{
			System.out.println(number); //print result 
			number+=3; //increment number by three 
		}
		
		System.out.println("The numbers from 0 to 50 counting by fives are");
		int number1 = 0; //declare initial variable 
		while (number1<=50) //while number is less than 51 
		{
			System.out.println(number1); //print result 
			number1+=5; //increment number by 5 each loop 
		}
		
		System.out.println("The numbers from 24 to 0 counting down by twos are");
		int number3 = 24; //declare initial variable 
		while (number3>=0) //while the variable is greater than 0
		{
			System.out.println(number3); //print result 
			number3-=2; //increment the number by -2
		}
		
		System.out.println("The numbers from 0 to 99 are");
		int count2 = 0; //declare initial variable 
		while (count2<=99) //while the total count is less than 99 
		{
			int number5 = 0; //declare second variable inside the first loop so that it gets reset every time the loop runs 
			while (number5 <=9) //while the number is less than 10 
			{
				System.out.println(count2); //print result 
				count2++; //increment total count by 1
				number5++; //increment number by 1
			}
		}
	}
}
