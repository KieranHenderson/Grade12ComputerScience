/* TicTacTowController is a class that is in charge of controlling all the actions and events that may occur while running the TicTacToe application as well as the creation of the main game stage and handling the AI */
/* Kieran Henderson */
/* 4/15/20 */
package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

public class TicTacToeController {
	
	@FXML TextField gridSizeText;
	static Stage primaryStage;
	static Stage secondaryStage;
	static Stage popUps;
	Text score = new Text();
		
	//size of the grid 
	public String gridSize = "3x3";
	
	Button[] btns;
	GridPane gridPane;
	//Score array X win, tie, O win
	int[] scoreNum = {0,0,0};
	private boolean isFirstPlayer = true;
	//variable to track the open number of tiles 
	int openNum = 0;
	String winner = "";
	public boolean singlePlayer = false;
	boolean hardAi = false;
	private static boolean firstGame = true;

	/* method that takes an action event for handling button clicks */
	public void buttonClickHandler(ActionEvent e) {
		Button clickedButton = (Button) e.getTarget();
		String buttonLable = clickedButton.getText();
		if ("".equals(buttonLable) && isFirstPlayer && winner == "") { // if the button is blank and it is x's turn and a winner has not been found then set the clicked buttons text to X,
			clickedButton.setText("X");									//isFirstPlayer to false so O will go after, check for a win and if it is single player have the AI function called to have the AI take their turn
			isFirstPlayer = false;
			find3InARow(true);
			if (winner == "" && singlePlayer == true && hardAi == true) {
				hardAI();
			}else if (winner == "" && singlePlayer == true && hardAi == false) {
				easyAi();
			}
		} 
		
		else if ("".equals(buttonLable) && !isFirstPlayer && winner == "") {
			clickedButton.setText("O"); //if the button is blank, it is O's turn and a winner has not been found then set the button text to O, set isFirstPlayer to true so that X will go next, check win
			isFirstPlayer = true;
			find3InARow(true);
			}
		
		else if ("OK".equals(buttonLable)) { //close the current window 
			closeCurrentWindow(e);	
		} 
		
		else if("Single Player".equals(buttonLable) && "3x3".equals(gridSize) == false) {
			openWarningWindow();
		}
		
		else if("Single Player".equals(buttonLable) && hardAi == false ||"Single Player".equals(buttonLable) && "3x3".equals(gridSize)){ //Makes sure that the player doesn't select hard AI and single player 
			singlePlayer = true;
			this.closeCurrentWindow(e); //close the opening window and start the game
			startGame();
			if (firstGame == true) {
				firstGame = false; //if it is the first time the player is starting the game then display how to play
				openHowToWindow();
			}
		}
		
		else if ("Two Player".equals(buttonLable)) { // if it is two player then start the game and close the opening window
			this.closeCurrentWindow(e);
			startGame();
			if (firstGame == true) {
				firstGame = false; //if it is the first time the player is starting the game then display how to play
				openHowToWindow();
			}		
		}
		
		else if ("▲".equals(buttonLable)) { //button for increasing grid size
			int size = Integer.parseInt(gridSize.substring(0,1)); //the width of the grid by taking the first letter of the size string and converting it to an int 
			if (size<9) {
				size++; //increase the size as long as it is less than than 9
			}
			gridSize=Integer.toString(size)+"x"+Integer.toString(size); //update the string 
			gridSizeText.setText(gridSize); // update the text on screen
		}
		
		else if ("▼".equals(buttonLable)) { //button for decreasing grid size 
			int size = Integer.parseInt(gridSize.substring(0,1)); //the width of the grid by taking the first letter of the size string and converting it to an int 
			if (size>3) {	//decrease the size as long as it is greater than than 3
				size--;
			}
			gridSize=Integer.toString(size)+"x"+Integer.toString(size); //update the string
			gridSizeText.setText(gridSize); // update the text on screen
		}
		
		else if ("Easy".equals(buttonLable)) { //if the button says easy then change the AI level to hard and the text to Hard
			hardAi = true;
			clickedButton.setText("Hard");
		} 
		
		else if ("Hard".equals(buttonLable)) { //if the button says hard then change the AI difficulty to easy and the text Easy
			hardAi = false;
			clickedButton.setText("Easy");
		}
	}
	
	/* method that takes an action event and handles menu clicks */
	public void menuClickHandler(ActionEvent e) {
		MenuItem clickedMenu = (MenuItem) e.getTarget();
		String menuLable = clickedMenu.getText();

		if ("Play".equals(menuLable)) { //if the button says play then rest all the buttons and the variables for a new game 
			for (int i = 0; i < btns.length; i++) {
				btns[i].getStyleClass().remove("winning-button");
				btns[i].setText("");
			}
			openNum = 0;
			winner = "";
			isFirstPlayer = true; // new game starts with X
		} 
		
		else if ("Quit".equals(menuLable)) { //quit the game
			System.exit(0);
		} 
		
		else if ("How To Play".equals(menuLable)) { //open the how to play menu
			openHowToWindow();
		} 
		
		else if ("About".equals(menuLable)) { //open the about window
			openAboutWindow();
		} 
		
		else if ("Controls".equals(menuLable)) { //open the controls menu
			openControlsWindow();
		} 
		
		else if ("Back".equals(menuLable)) { //go back to the start screen
			openStartWindow();
		}
	}
	
	public boolean find3InARow(boolean findWinner) { //method for checking if a win has occurred which takes the button that was clicked as a parameter and a boolean that dictates weather to highlight the win or not
		System.out.println("x");	
		winner = "";
			openNum=0;
			int size = btns.length; //total amount of buttons 
			//open tile calculation
			for (Button b: btns) {
				if(b.getText() == "") { //if the button text is "" then add one to openNum
					openNum++;
				}
			}

			int winAmount; //deciding the amount of tiles needed to win on each grid size
			if (size == 9) { //if the grid contains 9 buttons then you need 3 in a row to win
				winAmount = 3;
			} else {
				winAmount = 4; //else you need 4 in a row
			}
			
			if (winAmount == 3) {
				
				//check horizontal win
				for (int i = 0; i < btns.length-2;i++) { // loops as many times as there are buttons -2 to prevent over looping 
					//three in a row are detected if the buttons are all in a row and have the same text on them and that text is not ""
					if (btns[i].getText()==btns[i+1].getText() && btns[i].getText() == btns[i+2].getText() && btns[i].getText() != "" && btns[i].getLayoutY() == btns[i+1].getLayoutY() && btns[i].getLayoutY() == btns[i+2].getLayoutY()) {
						if (findWinner) { //a variable that is given to the method that tells it if the game should end or not so I can reuse this function when doing calculations with the AI
							highlightWinningCombo(btns[i],btns[i+1],btns[i+2],null);
						}
						return true;
					}
				}
				
				//check vertical win
				for (int i = 0; i < btns.length-(Math.sqrt(size)*2);i++){ //loops as many times as there are columns -1
					//if all the buttons in the row are in the same column and have the same text and the text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size))].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size))].getText() && btns[i].getText() != "") {
						if (findWinner) {
							highlightWinningCombo(btns[i],btns[(int) (i+Math.sqrt(size))],btns[(int) (i+2*Math.sqrt(size))],null);
						}
						return true;
					}
				}
				
				//check right diagonal win (top left to bottom right)
				for (int i = 0; i < btns.length-(2+(Math.sqrt(size)*2));i++){ //loops as many times as there are buttons - the two right most columns as those are impossible to win like this and the bottom two rows 
					//if all the buttons are in a diagonal from top left to bottom right and all the text is the same and the text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size)+1)].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size)+2)].getText() && btns[i].getText() != "") {
						if (findWinner) {
							highlightWinningCombo(btns[i],btns[(int) (i+Math.sqrt(size)+1)],btns[(int) (i+2*Math.sqrt(size)+2)],null);
						}
						return true;
					}
				}
				
				//check left Diagonal win (top right to bottom left)
				for (int i = 0; i < btns.length-(Math.sqrt(size)*2);i++){ //loops as many times as there are buttons minus the bottom two rows 
					//if all the buttons are in a diagonal from top right to bottom left and have the same text and that text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size)-1)].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size)-2)].getText() && btns[i].getText() != "") {
						if (findWinner) {
							highlightWinningCombo(btns[i],btns[(int) (i+Math.sqrt(size)-1)],btns[(int) (i+2*Math.sqrt(size)-2)],null);
						}
						return true;
					}
				}
			}
			
			if (winAmount == 4) {
				
				//check horizontal win
				for (int i = 0; i < btns.length-3;i++) { //loops as many times as there are buttons -3
					//four in a row are detected if the buttons are all in a row and have the same text on them and that text is not ""
					if (btns[i].getText()==btns[i+1].getText() && btns[i].getText() == btns[i+2].getText() && btns[i].getText() == btns[i+3].getText() && btns[i].getText() != "" && btns[i].getLayoutY()==btns[i+3].getLayoutY() && btns[i].getLayoutY() == btns[i+1].getLayoutY() && btns[i].getLayoutY() == btns[i+2].getLayoutY()) {
						highlightWinningCombo(btns[i],btns[i+1],btns[i+2],btns[i+3]);
						return true;
					}
				}
				
				//check vertical win
				for (int i = 0; i < btns.length-(Math.sqrt(size)*3);i++) { //loops as many times as there are buttons minus the three right most columns 
					//if all the buttons in the row are in the same column and have the same text and the text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size))].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size))].getText() && btns[i].getText() == btns[(int) (i+3*Math.sqrt(size))].getText() && btns[i].getText() != "") {
						highlightWinningCombo(btns[i],btns[(int) (i+Math.sqrt(size))],btns[(int) (i+2*Math.sqrt(size))],btns[(int) (i+3*Math.sqrt(size))]);
						return true;
					}
				}
				
				//check right diagonal win (top left to bottom right)
				for (int i = 0; i < btns.length-(3+(Math.sqrt(size)*3));i++){ //loops as many times as there are buttons - the bottom three rows and 3 right most columns and the last three rows 
					//if all the buttons are in a diagonal from top left to bottom right and all the text is the same and the text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size)+1)].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size)+2)].getText() && btns[i].getText() == btns[(int) (i+3*Math.sqrt(size)+3)].getText() && btns[i].getText() != "" && btns[i].getLayoutX() < btns[btns.length-3].getLayoutX()) {
						highlightWinningCombo(btns[i], btns[(int) (i+Math.sqrt(size)+1)], btns[(int) (i+2*Math.sqrt(size)+2)], btns[(int) (i+3*Math.sqrt(size)+3)]);
						return true;
					}
				}
				
				//check left Diagonal win
				for (int i = 2; i < btns.length-(Math.sqrt(size)*3);i++){ //loops as many times as there are buttons minus the three bottom rows and the first three rows 
					//if all the buttons are in a diagonal from top right to bottom left and have the same text and that text is not ""
					if (btns[i].getText()==btns[(int) (i+Math.sqrt(size)-1)].getText() && btns[i].getText() == btns[(int) (i+2*Math.sqrt(size)-2)].getText() && btns[i].getText() == btns[(int) (i+3*Math.sqrt(size)-3)].getText() && btns[i].getText() != "" && btns[i].getLayoutX() > btns[2].getLayoutX()) {
						highlightWinningCombo(btns[i],btns[(int) (i+Math.sqrt(size)-1)], btns[(int) (i+2*Math.sqrt(size)-2)],btns[(int) (i+3*Math.sqrt(size)-3)]);
						return true;
					}
				}
			}
			
			if (openNum==0 && winner=="" && findWinner) { //if there are no more open tiles and there is no winner then the winner is t for tie 
				winner = "t";
				scoreNum[1]++; //the tie score goes up by one 
				score.setText("Score: " + scoreNum[0] +"-" + scoreNum[1] + "-" + scoreNum[2]); // update the one screen score 
			}
			
		return false; //if now winner was found return false 
	}
	
	private void highlightWinningCombo(Button first, Button second, Button third, Button forth) { //method that applies the css formatting to the winning buttons taking the buttons as parameters 
		// apply winning button css to all the winning buttons 
		first.getStyleClass().add("winning-button");
		
		second.getStyleClass().add("winning-button");

		third.getStyleClass().add("winning-button");
		
		if (forth !=null) {
			forth.getStyleClass().add("winning-button");
		}
		
		winner = first.getText(); 
		
		if (winner == "X") { //update the scores array accordingly and update the screen
			scoreNum[0]++;
			score.setText("Score: " + scoreNum[0] +"-" + scoreNum[1] + "-" + scoreNum[2]);
		} else if (winner == "O") {
			scoreNum[2]++;
			score.setText("Score: " + scoreNum[0] +"-" + scoreNum[1] + "-" + scoreNum[2]);
		}
		
	}
	
	/* this method opens up the start screen window */
	private void openStartWindow() {		
		try {
			secondaryStage.close();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("startScreen.FXML"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void openHowToWindow(){ //window that is opened from the main game screen under the help menu from the How To Play button
		try {
			//load the pop up window 
			Pane howTo = (Pane) FXMLLoader.load(getClass().getResource("howToPlay.fxml"));
			//create new scene
			Scene howToScene = new Scene(howTo, 300, 350);
			//add CSS to new scene
			howToScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//create new stage to put in 
			popUps = new Stage();
			popUps.setScene(howToScene);
			popUps.setResizable(false);
			popUps.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void openControlsWindow(){ //window that is opened from the opening game screen under the directions menu from the controls button
		try {
			//load the pop up window
			Pane howTo = (Pane) FXMLLoader.load(getClass().getResource("controls.fxml"));
			//create new scene
			Scene howToScene = new Scene(howTo, 250, 250);
			//add CSS to new scene
			howToScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//create new stage to put in 
			popUps = new Stage();
			popUps.setScene(howToScene);
			popUps.setResizable(false);
			popUps.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void openAboutWindow(){ //window that is opened from the main game screen under the help menu from the about button
		try {
			//load the pop up window
			Pane a = (Pane) FXMLLoader.load(getClass().getResource("about.fxml"));
			//create new scene
			Scene about = new Scene(a, 250, 250);
			//add CSS to new scene
			about.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//create new stage to put in 
			popUps = new Stage();
			popUps.setScene(about);
			popUps.setResizable(false);
			popUps.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void openWarningWindow() {
		try {
			//load the pop up window
			Pane a = (Pane) FXMLLoader.load(getClass().getResource("3x3AIWarning.fxml"));
			//create new scene
			Scene about = new Scene(a, 250, 250);
			//add CSS to new scene
			about.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//create new stage to put in 
			popUps = new Stage();
			popUps.setScene(about);
			popUps.setResizable(false);
			popUps.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void closeCurrentWindow(ActionEvent evt) { //gets the current stage and closes it 
		final Node source = (Node) evt.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	public void startGame(){ //my main game stage that I did using code rather than using an FXML file and scene builder so that I can have a dynamic scene that changes size according to how many buttons there are and so I can change how many buttons I have 
		
		btns = new Button[Integer.parseInt(gridSize.substring(0,1))*Integer.parseInt(gridSize.substring(0,1))]; //update the buttons array to contain the correct amount of items 
		
		
		try {
			initButtonsArray(btns); //populate the button array with the correct amount of buttons using the initButtonArray functions 
			BorderPane borderPane = new BorderPane();
			borderPane.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //load css file 
			HBox hbox = new HBox();
			Group group = new Group();
			
			//set parents 
			borderPane.getChildren().add(hbox); 
			hbox.getChildren().add(group);
			group.getChildren().add(getGrid(btns));
			
			/* actions menu */
			Menu actionsMenu = new Menu("Actions"); //declare the actions menu
			actionsMenu.getStyleClass().add("menuText"); //add the css file to the menu
			/* play button */
			MenuItem play = new MenuItem("Play"); //declare the play 
			play.setOnAction(this::menuClickHandler); //set the on action handler 
			actionsMenu.getItems().add(play); //add the lay button to the actions menu 
			
			/* back button */
			MenuItem back = new MenuItem("Back"); //declare the back button 
			back.setOnAction(this::menuClickHandler); //set the on action handler
			actionsMenu.getItems().add(back); //add the back button to the actions menu 
			
			/* quit button */
			MenuItem quit = new MenuItem("Quit"); //declare the quit button 
			quit.setOnAction(this::menuClickHandler); //set the on action handler
			actionsMenu.getItems().add(quit); //add the quit button to the actions menu 
			
			
			/* help menu */
			Menu helpMenu = new Menu("Help");
			
			/* how to play button */
			MenuItem howToPlay = new MenuItem("How To Play"); 
			howToPlay.setOnAction(this::menuClickHandler);
			helpMenu.getItems().add(howToPlay);
			
			/* about button */
			MenuItem about = new MenuItem("About");
			about.setOnAction(this::menuClickHandler);
			helpMenu.getItems().add(about);
			
			/* menu bar */
			MenuBar menuBar = new MenuBar();
			menuBar.setPrefSize(30, 10); //size
			menuBar.getMenus().add(actionsMenu); //add menus to the menu bar
			menuBar.getMenus().add(helpMenu);
			borderPane.setTop(menuBar); //put the menu bar at the top of the screen 
			
			/* score text */
			score.setText("Score: " + scoreNum[0] +"-" + scoreNum[1] + "-" + scoreNum[2]); //set the score text 
			score.setLayoutX(10); //set the position 
			score.setLayoutY(75);
			score.setTextAlignment(TextAlignment.CENTER); //Align the text 
			borderPane.getChildren().add(score); //add score to the border pane as a child 
			score.setId("score"); //set the text css
			
			int sceneExtra = 0; //amount of extra room the screen needs to compensate for the button hgap and vgap 
			for (int i = 0; i<Math.sqrt(btns.length);i++) { //depending on how many columns there are add the correct amount to the 
				sceneExtra+=5;
			}
			
			//create new scene
			Scene scene = new Scene(borderPane, 410+sceneExtra, 500+sceneExtra);

			//add CSS to new scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//create new stage to put scene in 
			secondaryStage = new Stage();
			secondaryStage.setTitle("TicTacToe");
			secondaryStage.setScene(scene);
			secondaryStage.setResizable(false);
			secondaryStage.show();
			hbox.setPrefHeight(scene.getHeight());
			hbox.setPrefWidth(scene.getWidth());
			hbox.setLayoutX(scene.getWidth()-btns[btns.length-1].getWidth()*Math.sqrt(btns.length)-7.5-(sceneExtra)); //setting the hBox layout to be in the bottom right hand corner plus a little bit extra room
			hbox.setLayoutY(scene.getHeight() -btns[btns.length-1].getHeight()*Math.sqrt(btns.length)-7.5-(sceneExtra));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public Pane getGrid(Button[] btns) { //method for creating a grid with the correct amount of columns and rows as well as adding the buttons to their respective slot
		int row = 0; //number of rows 
		int col = 0; //number of columns 
		gridPane = new GridPane();
		for (Button b: btns) { //add correct amount of rows and columns 
			gridPane.add(b,col,row); //add the button to the correct row and column 
			col++;
			if(Math.sqrt(btns.length) == col) {
				row+=1;
				col=0;
			}
		}
		gridPane.setHgap(5); //set the gap between the grid slot so the buttons don't touch 
		gridPane.setVgap(5);
		return gridPane;
	}
	
	public void initButtonsArray(Button[] btns) { //method for populating the buttons array
		int btnTextSize = 86;
		int subtractionAmount = 12; //variable that scales so that the text does not get to small but still doesn't change the buttons size
		for (int i = 0; i< Math.sqrt(btns.length);i++) { //for loop that loops as many times as there are columns to determine the correct text size for the amount of buttons 
			btnTextSize-=subtractionAmount;
			subtractionAmount--;
		}

		for(int i = 0; i < btns.length; i++) { //for loop that goes through all the buttons and applies the correct css, size and on action handler 
			btns[i] = new Button();
			btns[i].getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 
			btns[i].getStyleClass().add("button");
			btns[i].setOnAction(this::buttonClickHandler);
			double buttonSize = 400/Math.sqrt(btns.length);
			btns[i].setPrefSize(buttonSize, buttonSize);
			btns[i].setStyle("-fx-font-size:" + Integer.toString(btnTextSize));
		}
	}
	
	public void easyAi() { //easy mode for the AI that randomly chooses an open tile 
		List<Integer> openSpaces = new ArrayList<Integer>(); 
		for (int i = 0; i<btns.length; i++) { //calculate the amount of open spaces there are 
			if (btns[i].getText().isEmpty()) { 
				openSpaces.add(0);
				openNum++;
			}else {
				openSpaces.add(1); //add to the openSpaces array a 1 if the space is taken and a 0 if it is open
			}
		}
		if (openNum == 0) { //return if the game is a tie
			return;
		}
		Random rand = new Random(); 
		int randNum = rand.nextInt(openSpaces.size()); 
		while (openSpaces.get(randNum) != 0) { //keeps looping until the index in openSpaces is 0 (meaning the space is open) 
			randNum = rand.nextInt(openSpaces.size());
		}
		btns[randNum].fire(); 
	}
	
	
	public void hardAI () { //hard AI using minimax 
		bestMove().fire();
	}
	
	private Button bestMove() { //method that finds the best move possible 
		int bestScore = -100;
		int score;
		Button button = null; 
		for(int i = 0; i<btns.length;i++) { //cycle through each button 
			if(btns[i].getText()==""){
				if (isFirstPlayer) {
					btns[i].setText("X"); //depending on who's turn it is then place the corresponding value on the tile which will act as a "fake move" and be reset later 
				} else {
					btns[i].setText("O");
				}
				score = minimax(true); //call the minimax function 
				btns[i].setText(""); //reset the "fake move" from earlier 
				if (score > bestScore) {
					bestScore = score; //update the best score if need be 
					button = btns[i]; //set the corresponding button as the best move 
				}
			}
		}
		return button;
	}
	
	private int minimax (boolean ai) { //takes a boolean based on if the AI is starting or not
		for (int i = 0; i<btns.length;i++) {
			if(find3InARow(false)) { //for each button check for a win
				if(ai) {
					return 1;
				}else {
					return -1;
				}
			}
		}

		boolean full = true; //if the button has text on it the return 0 as it is an invalid move 
		for (int i=0;i<btns.length;i++) {
			if (btns[i].getText()=="") {
				full = false;
			}
		}
		if (full) {
			return 0;
		}
		
		if (ai==false) {
			int bestScore = -100; //if it is the AI turn the AI is maximizing their score, so the best score is very little to start
			for (int i = 0; i<btns.length;i++) { //for every button set the text to O
				if (btns[i].getText()=="") {
					btns[i].setText("O");
					int score = minimax(true); //call the minimax function in recursion this time with the opposite value passed to it
					btns[i].setText("");
					if (score > bestScore) {
						bestScore = score; //if the score is better than best score then that is the new best score 
					}
				}
			}
			return bestScore;
		} else {
			int bestScore = 100; //if it is the Player turn the player is minimizing their score, so the best score is very high to start 
			for (int i=0;i<btns.length;i++) { //for every button set the text to X 
				if(btns[i].getText()=="") {
						btns[i].setText("X");
					int score = minimax(false); // use the minimax function with the opposite value passed to it to calculate score for that position 
					btns[i].setText("");
					if(score < bestScore) { //if the score is better than best score then that is the new best score 
						bestScore = score;
					}
				}
			}
			return bestScore;
		}
	}
}
