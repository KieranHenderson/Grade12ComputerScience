/* RockPaperScissorsLizardSpock is a program which takes the users input and calculates how much money you would have at an inputed rate after one term */
/* Kieran Henderson */
/* 2/2/20 */
package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RockPaperScissorsLizardSpock {
	
	// scores
	public static int tie=0;
	public static int win = 0;
	public static int loss=0;
	
	//stats (rock, paper, scissors, lizard, spock)
	static int[] playerChoices = {0,0,0,0,0};
	static int[] computerChoices = {0,0,0,0,0};

	//lose to arrays which contain what each choice will lose to and the reaction that occurs because of it
	static String[][] outcome = {
			{"rock loves rock","rock get vaporised by spock","rock gets covered by paper","rock crushes lizard","rock crushes scissors"},
			{"spock vaporises rock","spock loves spock","spock get disproved by paper","spock gets poisoned by lizard","spock smashes scissors"},
			{"paper covers rock","paper disproves spock", "paper loves paper", "paper get eaten by lizard","paper cut by scissors"},
			{"lizard get crushed by rock","lizard poisons spock","lizard eats paper","lizard loves lizard","lizard decapitated by scissors"},
			{"scissors get crushed by rock","scissors get crushed by spock","scissors cut paper","scissors decapitates lizard", "scissors loves scissors"}};
	
	static String[] choices = {"rock","spock","paper","lizard","scissors"};//possible choices the user could make

	// stats method which displays all the different stats using printf
	public static void stats() throws IOException {
		BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.printf("%-15s%-15s%-15s%n","","Player", "Computer");
		
		for(int i=0;i<=4;i++) {
			System.out.printf("%-15s%-15d%-15d%n",choices[i], playerChoices[i],computerChoices[i]);
		}
		System.out.println("");
		System.out.println("Would you like to play more? 'yes' to play again, anything else to exit.");
		String input = myInput.readLine().trim();
		if (input.equals("yes")) {
			main(null);
		}else {
			System.out.println("Goodbye");
		}
	}
	

	public static void main(String[] args)throws IOException 
	{	
		int playerNum = 10; //number for choice 
		BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("----------------------------------------------------------------");
		System.out.println("Welcome to Rock Paper Scissors Lizard Spock");
		System.out.println("Please select either 'rock', 'paper', 'scissors', 'lizard', or 'spock'");
		String input = myInput.readLine().trim();
		System.out.println("");
	
		for (int i =0;i<=4;i++)	{			//check input to verify that the players input is valid
			if (input.equals(choices[i])) {	//cycle through all of the potential values and see if they line up
				playerNum=i;
				playerChoices[i]+=1;
			}
		}
			if (playerNum==10) {					//if they don't (playerNum still = the default 10) then ask for the user to input another play
				System.out.println("Sorry that's an invalid input, please try to use: 'rock', 'paper', 'scissors', 'lizard', 'spock' and try again");
				input = myInput.readLine().trim();
				for (int j = 0;j<=4;j++) {
					if (input.equals(choices[j])) {
						playerNum=j;
						playerChoices[j]+=1;
					}
				}
					if (playerNum==10){			//if they still mess it up then the program quits and outputs what happened 
						System.out.println("Sorry, this input is also invalid please restart the program and try again");
						System.exit(0);
					}
					
			}
		
		int compNum = (int)(Math.random()*5);	//select a random number between zero and 4, each representing one of the possible choices 
		for (int i =0;i<=4;i++)	{			//depending on the random number input is assigned accordingly and the correct stat is increased 
			if (compNum == i){
				computerChoices[i]+=1;
				System.out.println("The computer chose "+choices[i]);
				System.out.println("");
			}
		}

		int total = compNum-playerNum;				//choosing winner math 
		int newTotal=Math.floorMod(total, 5);
		if (newTotal == 0) {
			tie+=1;
			System.out.println("There was a tie, " + outcome[compNum][playerNum]);
		} else if(newTotal==3 || newTotal ==4) {
			win+=1;
			System.out.println("You win! "+ outcome[compNum][playerNum]);
		}else {
			loss+=1;
			System.out.println("You lose! "+ outcome[compNum][playerNum]);
		}
		System.out.println("");
		System.out.println("The current score is, " + win +" win, " + loss + " loss and " + tie +" tie");	//print curent score
		System.out.println("Would you like to proceed? Input 'yes' to continue, 'stats' to view your game stats and anything will stop the game");//ask if they would like to replay the game or look at the stats page
		String answer= myInput.readLine().trim();
		
		if (answer.equals("yes")) {
			main(args);
		}else if(answer.contentEquals("stats")) {
			System.out.println("----------------------------------------------------------------");
			stats();
		}else {
			System.out.println("Goodbye");
		}
	}
}
/*
package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RockPaperScissorsLizardSpock {
	
	// scores
	public static int tie=0;
	public static int win = 0;
	public static int loss=0;
	
	//stats (rock, paper, scissors, lizard, spock)
	static int[] playerChoices = {0,0,0,0,0};
	static int[] computerChoices = {0,0,0,0,0};

	//lose to arrays which contain what each choice will lose to and the reaction that occurs because of it
	static String[][] loseTo = {
			{"rock loves rock","rock get vaporised by spock","rock gets covered by paper","rock crushes lizard","rock crushes scissors"},
			{"spock vaporises rock","spock loves spock","spock get disproved by paper","spock gets poisoned by lizard","spock smashes scissors"},
			{"paper covers rock",""},
			{"scissors","Scissors decapitates Lizard","rock","Rock crushes Lizard"},
			{"paper","Paper disproves Spock","lizard","Lizard poisons Spock"}};
	
	//A method that returns the correct array depending on the input as well as count stats for the player only
	public static String[] selectList(String input, int counter) {
		if (input.equals("lizard")) {
			if (counter % 2==0) {
				playerChoices[3]+=1;
			}
			return lizardL;
		}else if(input.equals("rock")){
			if (counter % 2==0) {
				playerChoices[0]+=1;
			}
			return rockL;
		}else if(input.equals("paper")){
			if (counter % 2==0) {
				playerChoices[1]+=1;
			}
			return paperL;
		}else if(input.equals("scissors")){
			if (counter % 2==0) {
				playerChoices[2]+=1;
			}
			return scissorsL;
		}else if(input.equals("spock")){
			if (counter % 2==0) {
				playerChoices[4]+=1;
			}
			return spockL;
		}else{
			return null;
		}
	}
	
	// stats method which displays all the different stats using printf
	public static void stats() throws IOException {
		BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
		String[] choices = {"Rock","Paper","Scissors","Lizard","Spock"};//possible choices the user could make
		System.out.printf("%-15s%-15s%-15s%n","","Player", "Computer");
		
		for(int i=0;i<=4;i++) {
			System.out.printf("%-15s%-15d%-15d%n",choices[i], playerChoices[i],computerChoices[i]);
		}
		System.out.println("");
		System.out.println("Would you like to play more? yes to play again, anything else to exit.");
		String input = myInput.readLine().trim();
		if (input.equals("yes")) {
			main(null);
		}else {
			System.out.println("Goodbye");
		}
	}
	
	public static void main(String[] args)throws IOException 
	{	
		int inputCounter=0; //counter of how many times the selectList method is called 
		BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Rock Paper Scissors Lizard Spock");
		System.out.println("Please select either rock, paper, scissors, lizard, or spock");
		String input = myInput.readLine().trim();
		String input2;
		String[] inputList = selectList(input,inputCounter);
		//check for correct input
		if (inputList==null) {
			System.out.println("Sorry that's an invalid input, please try to use: rock, paper, scissors, lizard, spock and try again");
			input = myInput.readLine().trim();
			inputList = selectList(input,inputCounter);
			//check for correct input again
			if (inputList==null) {
				System.out.println("Another invalid input, if you would like to try again please restart the program.");
				System.exit(0) ;
			}
		}
		inputCounter+=1;
		int num = (int)(Math.random()*5);//select a random number between zero and 4, each representing one of the possible choices 
		//depending on the random number input is assigned accordingly and the correct stat is increased 
		if (num ==0) {
			input2 = "rock";
			computerChoices[0]+=1;
		}else if(num ==1) {
			input2 = "paper";
			computerChoices[1]+=1;
		}else if(num ==2) {
			input2 = "scissors";
			computerChoices[2]+=1;
		}else if(num ==3) {
			input2 = "lizard";
			computerChoices[3]+=1;
		}else{
			input2 = "spock";
			computerChoices[4]+=1;
		}
		String[] inputList2 = selectList(input2,inputCounter);
		inputCounter+=1;

		int nothing = 0;//count to see how many times the loop goes through without triggering a win or loss
		for (int i =0;i<=3;i+=2) {
			if (inputList[i].equals(input2)) {
				System.out.println("You chose "+ input+", the computer chose "+ input2+", "+ inputList[i+1]+ ". Better luck next time.");
				loss+=1;
			}else if(inputList2[i].equals(input)) {
				System.out.println("You chose "+ input+", the computer chose "+ input2+", "+ inputList2[i+1]+ ". Congratulations you win!");
				win+=1;
			}else {
				nothing+=1;
			}
			if (nothing ==2) { // if it loops through both possibilities for both and nothing is triggered (nothing = 2) then it is a tie
				System.out.println("You chose "+ input+" the computer chose "+ input2+". It's a tie!");
				tie+=1;
			}
		}
		System.out.println("The current score is, " + win +" win, " + loss + " loss and " + tie +" tie");
		System.out.println("Would you like to proceed? Input yes to continue, stats to view your game stats and anything will stop the game");
		String answer= myInput.readLine().trim();
		
		if (answer.equals("yes")) {
			main(args);
		}else if(answer.contentEquals("stats")) {
			stats();
		}else {
			System.out.println("Goodbye");
		}
	}
}





 */
