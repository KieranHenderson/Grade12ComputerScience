/* Main is a class that is in charge of launching the game and setting up the first stage */
/* Kieran Henderson */
/* 4/15/20 */
package tictactoe;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//making a new anchor pane and assigning the correct css and fxml files for the opening screen
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("startScreen.FXML"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
