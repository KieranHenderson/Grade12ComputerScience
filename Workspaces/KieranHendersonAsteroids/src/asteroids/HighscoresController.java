/* Display Text is a class that holds methods for displaying text on the screen for the score and high scores as well as an animation timer to update and draw the high scores */
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
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

public class HighscoresController extends Screens implements Initializable, ControlledScreen {
	
	//initializing the display text variables 
	DisplayText highscores;
	DisplayText title;
		
	public void loop() { //main loop for the high scores controller 
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {	
				if(isHighPause() == false) { //if the high score screen is not "paused"
					gc.setFill(Color.BLACK); //refresh the screen
					gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
					
					//update everything
					update();
					
					//draw everything
					draw();
				}
			}			
		}.start();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//size of the text
		int textSize = 30;
		int titleSize = 60;
		
		gc = gameCanvas.getGraphicsContext2D();
		
		lines = Collections.emptyList(); //declare the lines list 
		
		highscores = new DisplayText(gc, gameCanvas, textSize); //set the display text objects 
		title = new DisplayText(gc, gameCanvas, titleSize);
	
		loop();	
	}
	
	public void draw(){ //method that draws everything 

		title.displayCenter("HIGHSCORES", (int) gameCanvas.getWidth()/2, 100); //display the title 

		//display the 3 titles that never change 
		highscores.displayRight(lines.get(0), 300, 200); //rank
		highscores.displayRight(lines.get(1), 530, 200); //name
		highscores.displayRight(lines.get(2), 750, 200); //score
		
		int row = 0; //keep track of what row the program is displaying 
		int col = 0; //keep track of what row the program is displaying 
		int xDist = 230; //the x distance between each column
		int yDist = 35; //the y distance between each row 
		for (String s: lines) { //for each string in lines 
			if (lines.indexOf(s) > 2 && row < 10) {
				highscores.displayRight(s, col * xDist+300, row*yDist+250); //display the high scores 
				col++; //increase the columns
				if(col == 3) {
					row++; //increase the row 
					col = 0; //reset the columns 
				}
			}
		}
	}
	
	public void update() { //method that updates the lines by reading the high scores file 
		try {
			lines = Files.readAllLines(Paths.get("highscores.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
