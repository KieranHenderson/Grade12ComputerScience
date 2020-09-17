/* Main is a class that runs as soon the program is launched, it loads all of the screens and then sets the screen to the main menu */
/* Kieran Henderson */
/* 6/7/20 */
//Source: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Main extends Application {
	
	/* names of each scene and the FXML file of each */
	public static String mainName = "main";
	public static String mainFile = "MainMenu.fxml";
	
	public static String gameName = "asteroids";
	public static String gameFile = "Asteroids.fxml";
	
	public static String highName = "highscores";
	public static String highFile = "Highscores.fxml";
	
	public static String deathName = "death";
	public static String deathFile = "DeathScreen.fxml";

	@Override
	public void start(Stage primaryStage) {
		ScreenController mainController = new ScreenController(); //new screen controller object 
		
		/* load each scene into the main controller */
		mainController.loadScreen(Main.mainName, Main.mainFile, primaryStage);
		mainController.loadScreen(Main.gameName, Main.gameFile, primaryStage);
		mainController.loadScreen(Main.highName, Main.highFile, primaryStage);
		mainController.loadScreen(Main.deathName, Main.deathFile, primaryStage);

		mainController.setScreen(Main.mainName, Main.mainFile); //set the current screen to the main menu
		
		Group root = new Group(); //create a new group that will hold all of the elements in the controllers scene 
		root.getChildren().addAll(mainController); //add all the elements of the controllers scene to the root
		Scene scene = new Scene(root); //create a new scene with the elements of the root 
		
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setTitle("Asteroids");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
