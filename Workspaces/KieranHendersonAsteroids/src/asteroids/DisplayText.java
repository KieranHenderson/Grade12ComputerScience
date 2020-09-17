/* Display Text is a class that holds methods for displaying text on the screen for the score and high scores */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class DisplayText {
	
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	Font vector; //declare my custom font variable 

	
	public DisplayText (GraphicsContext gc, Canvas gameCanvas, int size) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		
		 vector = Font.loadFont(getClass().getResourceAsStream("/fonts/Vectorb.ttf"), size); //load my custom font at the specified size 
	}

	public void displayLeft(String text, int x, int y) { //display the string with left alignment 
		gc.setFont(vector); //set the font 
		gc.setFill(Color.WHITE); //set the color of the text
		gc.fillText(text, x, y); //draw the text
	}
	
	public void displayRight(String text, int x, int y) { //display the string with right alignment 
		gc.setFont(vector); //set the font of the text 
		gc.setFill(Color.WHITE); //set the color of the text
		gc.setTextAlign(TextAlignment.RIGHT); //set the alignment to right 
		gc.fillText(text, x, y); //draw the text 
	}
	
	public void displayCenter(String text, int x, int y) { //display the string with center alignment 
		gc.setFont(vector); //set the font of the text
		gc.setFill(Color.WHITE); //set the color of the text 
		gc.setTextAlign(TextAlignment.CENTER); //set text alignment to center 
		gc.fillText(text, x, y); //draw the text 
	}
	
	
}
