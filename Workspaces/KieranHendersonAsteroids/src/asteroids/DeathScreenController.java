/* DeathScreenController is a controller class that is in charge of everything that happens once you die in the game until you arrive at the main menu again
 * It extends screens as it is one of the four screen controllers and implements Initializable so that it can be initialized when it is loaded and implements 
 * controlled screen so that it can be used when switching screens */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class DeathScreenController extends Screens implements Initializable, ControlledScreen{
	
	//declaring the three different types of text that need to be displayed on the screen
	DisplayText gameOver;
	DisplayText newHighscore;
	DisplayText displayName;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initialize all the variables 
		gc = gameCanvas.getGraphicsContext2D();

		topScores = new ArrayList<Integer>();
		lines = Collections.emptyList();
		newLines = new ArrayList<String>();
		name = "AAA";
		
		//initialize the display text
		gameOver = new DisplayText(gc, gameCanvas, 100);
		newHighscore = new DisplayText(gc, gameCanvas, 50);
		displayName = new DisplayText(gc, gameCanvas, 40);
		
		//get the current list of high scores 
		getHighscores();

		//start the main loop of the class 
		deathLoop();
	}
	
	public void deathLoop() {		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {	
				
				if(isDeathPause() == false) { //if the screen is not "paused"
					if(gameScene != null) { //check to make sure the scene is not null before checking the input 
						checkInput();
					} else { //if the scene is null then I need to set the scene 
						getScene(stage);
					}
					
					//draw everything 
					draw();
				}
			}
		}.start();
	}
	
	public void checkInput() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>(){ //when a key is pressed 
			@Override
			public void handle(KeyEvent e) {

				String code = e.getCode().toString();
				
				if ("BACK_SPACE".equals(code)) { //if the button pressed is the back space then subtract the last letter of the name string 
					if(name.length() == 3) {
						name = name.substring(0, 2);
					} else if (name.length() == 2) {
						name = name.substring(0, 1);
					} else if (name.length() == 1){
						name = "";
					}
				} 
				
				else if (Character.isLetter(code.charAt(0)) && code.length() == 1) { //if the button pushed is a letter then add it to the name as long as the name length is less than 3
					if(name.length() < 3) {
						name +=code;
					}
				}
			}
		});
	}
	
	public void draw() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); //cover the screen in black so it "refreshes" 
		
		gameOver.displayCenter("GAMEOVER", 500, 300); //display game over 
		
		for (int i = 0; i < topScores.size(); i++) { //check all of the top scores
			
			if(SpaceObject.getScore() > topScores.get(0)) { //if you have the top score 
				newHighscore.displayCenter("NEW HIGHSCORE: " + SpaceObject.score, 500, 400); //display new high score
				displayName.displayCenter(name, 500, 500); //display the name enter area
				newTopScore = true; 
			} 
			
			else if (SpaceObject.score > topScores.get(i)) { //if you have not the top score but are in the top 10
				newHighscore.displayCenter("NEW TOP SCORE: " + SpaceObject.score, 500, 400); //display new top score
				displayName.displayCenter(name, 500, 500); //display the name enter area
				newTopScore = true;
			} 
			
			else {
				newTopScore = false;
			}
		}	
	}
	
	public void getHighscores() {
		
		try {
			lines = Files.readAllLines(Paths.get("highscores.txt"), StandardCharsets.UTF_8); //update the lines variable by reading each line of the high scores variable 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Integer> topTen = new ArrayList<Integer>(); //the top ten scores = the 10 numbers in the high scores file 
		for(int i = 5; i < lines.size(); i+=3) {
			int num = Integer.parseInt(lines.get(i));
			topTen.add(num);
		}
		
		topScores = topTen; //set top scores to top ten
		
		for(int i = 0; i < lines.size(); i++) { //update the new lines variable
			newLines.add(lines.get(i));
		}
		
	}

		
}
