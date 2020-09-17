/* Screens is a class that hold the general information and methods for each controller such as hyperlink handlers and it also does the high score updating */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class Screens extends ScreenController {
	
	//import game canvas, scene and graphics context
	@FXML
	Canvas gameCanvas;
	
	Scene gameScene;
	
	GraphicsContext gc;
		
	//static variables that track everything to do with high scores 
	static ArrayList<Integer> topScores; //array list with the top 10 scores
	static ArrayList<String> newLines;//array list with the updated list of the lines in the high score file
	static List<String> lines; //list from the high scores file when it is read
	static String name; //name inputed by the player that will be stored along side the score
	static boolean newTopScore = false; //if there has been a new top score

	ScreenController controller; //my controller object that controls all the screens 
	
	// booleans that tell each of the individual controllers what they should be running and what should be paused to reduce the amount of lag in the game 
	static boolean mainPause = false;
	static boolean gamePause = true;
	static boolean deathPause = true;
	static boolean highPause = true;

	
	public void hyperlinkClickHandler(ActionEvent e) { //method that handles all the hyper link clicks throughout the game 
		Hyperlink clickedButton = (Hyperlink) e.getTarget(); //gets the hyperlink that was clicked 
		String buttonLable = clickedButton.getText(); //get the text of the clicked hyperlink 

		if ("PLAY".equals(buttonLable)) { //if the hyperlink says play
			setGamePause(false); //unpause the game loop
			setMainPause(true); //pause the main menu loop
			
			controller.setScreen(Main.gameName, Main.gameFile); //set the screen to the game scene
		}
		
		 if ("HIGHSCORES".equals(buttonLable)) { //if the hyperlink says play
			 setMainPause(true); //pause the main menu loop
			 setHighPause(false); //unpause the high score loop
			 
			controller.setScreen(Main.highName, Main.highFile); //set the screen to the high score scene
		} 
		
		else if ("BACK".equals(buttonLable)) { //if the hyperlink says play
			setMainPause(false); //unpause the main menu loop
			setHighPause(true); //pause the high score loop
			
			controller.setScreen(Main.mainName, Main.mainFile); //set the screen to the main menu screen
		}
		
		else if ("CONTINUE".equals(buttonLable)) { //if the hyperlink says play
			if (newTopScore) { //if there has been a new high score 
				updateHighscore(); //update teh high scores 
			}
			
			setDeathPause(true); //pause the death screen loop
			setMainPause(false); //unpause the main menu loop
			
			SpaceObject.setScore(0); //reset the score to 0 after the score has been added to the high score list 
			
			controller.setScreen(Main.mainName, Main.mainFile); //set the screen to the main menu screen
		} 
		
		else if ("QUIT".equals(buttonLable)) { //if the hyperlink says quit
			System.exit(1); //close the game 
		}
	}
	
	public void updateHighscore() {
		int score = SpaceObject.getScore();

		for(int i = 0, j = 5; i < topScores.size(); i++, j+=3) { //for each score in top scores 
			if (score > topScores.get(i)) { //if the score is greater than the score
				int oldScore = topScores.get(i); //store the old score in a temporary variable 
				String oldName = newLines.get(j-1); //store the old name in a temporary variable

				topScores.set(i, score); //update the top score 
				newLines.set(j, Integer.toString(score)); //update the score in new lines 
				newLines.set(j-1, name); //update the name in new lines 
				
				name = oldName; //for the next loop use the old name and the old score so that the score gets bumped down
				score = oldScore;
			}
		}

		PrintWriter newHighscores = null; // new print writer object 
		try {
			newHighscores = new PrintWriter (new FileWriter("highscores.txt")); //try to open the high scores file 
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		for(int i = 0; i < lines.size(); i++) { //for each element in lines 
			newHighscores.println(newLines.get(i)); //write each element of new lines to the high scores file 
		}
		newHighscores.close(); //close the file 
	}
	
	
	/* getters and setters */
	public void getScene(Stage primaryStage) {
		gameScene = primaryStage.getScene();
	}
	
	public void setScreenParent(ScreenController screenPage) {
		controller = screenPage;
	}
	
	public static boolean isMainPause() {
		return mainPause;
	}

	public static void setMainPause(boolean mainPause) {
		Screens.mainPause = mainPause;
	}

	public static boolean isGamePause() {
		return gamePause;
	}

	public static void setGamePause(boolean gamePause) {
		Screens.gamePause = gamePause;
	}

	public static boolean isDeathPause() {
		return deathPause;
	}

	public static void setDeathPause(boolean deathPause) {
		Screens.deathPause = deathPause;
	}

	public static boolean isHighPause() {
		return highPause;
	}

	public static void setHighPause(boolean highPause) {
		Screens.highPause = highPause;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Screens.name = name;
	}

}