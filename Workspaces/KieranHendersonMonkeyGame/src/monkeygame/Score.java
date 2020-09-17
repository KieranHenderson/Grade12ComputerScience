/* Score is a class that holds methods for displaying text on the screen for the score, lives and death screen */
/* Kieran Henderson */
/* 5/1/20 */
package monkeygame;
//import the libraries 
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score {

	//initializing the graphics context and canvas
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	//constructor for the score object 
	public Score(GraphicsContext gc, Canvas gameCanvas) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
	}
	
	//method to display the lives and score on the screen during the game 
	public void display (Monkey monkey) {
		String scoreString = "Score: " + Banana.bananasEaten; //set the text to be displayed
		gc.setFont(Font.font("ComicSansMS", FontWeight.BOLD, 36)); //change the font of the text 
		gc.setFill(Color.DARKRED); //change the color of the text 
		gc.fillText(scoreString, 20, 50); //display text 
		
		String lives = "Lives: " + monkey.getLives(); //set the text to be displayed
		gc.setFill(Color.DARKRED); //set the color of the text 
		gc.fillText(lives, 20, 100); //display the text
	}
	
	//method to display the death screen when the monkey has no lives left
	public void deathScreen() {
		String deathScreen = "You died, better luck next time." + "\n" + "Press q to quit or p to play again." + "\n" + "Your final score was: " + Banana.bananasEaten; //set the text to be displayed
		gc.setFont(Font.font("ComicSansMS", FontWeight.BOLD, 36)); //change the font of the text 
		gc.setFill(Color.DARKRED); //change the color of the text 
		gc.fillText(deathScreen, 225, 300); //display the text 
	}
}