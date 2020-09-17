/* Main is a class that is in charge of creating the basic game window and scene as well as linking the FXML and CSS files and setting the window title */
/* Kieran Henderson */
/* 4/15/20 */
package monkeygame;
//import the libraries 
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Monkey Game"); //set the game title to Monkey Game
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MonkeyGame.fxml")); //link the FXML file 
			BorderPane root = (BorderPane)loader.load(); //load the FXML file 
			Scene scene = new Scene(root,1000,700); //set the scene size
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //link the CSS file 
			MonkeyGameController controller = loader.getController(); //create the controller 
			primaryStage.setScene(scene); //set the stage 
			controller.getScene(primaryStage);
			controller.gameLoop(); //start the game loop
			primaryStage.setResizable(false); //set the window so it can be resized 
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
