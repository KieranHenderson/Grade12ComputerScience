/* Screen Controller acts as a controller that controls all of the screens, it helps set which scene is showing and loading all the screens and their controllers */
/* Kieran Henderson */
/* 6/7/20 */
//Source: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import javafx.util.Duration;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenController extends AnchorPane{
	
	static Stage stage; //the stage that the game is on 
	
	@FXML
	Canvas gameCanvas;
	
	Scene gameScene = null;
	
	GraphicsContext gc;
	
	private HashMap<String, Node> screens = new HashMap<>(); //hash map that stores the names of the different scenes and their corresponding screen
	
	public ScreenController() {
		super();
	}
	
	public void addScreen(String name, Node screen) { //method that adds the screen to the hash map
		screens.put(name, screen);
	}
	
	public Node getScreen(String name) { //method that gets the corresponding screen from the hash map depending on the name 
		return screens.get(name);
	}

	public boolean loadScreen(String name, String resource, Stage stage) { //method that loads the scene
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource)); //link the FXML file 
			Parent loadedScreen = (Parent) loader.load(); //load the FXML file 
			ControlledScreen screenController = ((ControlledScreen) loader.getController()); //create a new controlled screen to control the controller that corresponds to the FXML file 
			screenController.setScreenParent(this); //set the parent of the screen controller 
			addScreen(name, loadedScreen); //add the screen to the hash map
			ScreenController.stage = stage; //set the stage 
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean setScreen(final String name, String resource) {		
		if(screens.get(name) != null) { //if there is a screen that corresponds to the specified name 
			final DoubleProperty opacity = opacityProperty(); //get the opacity property of the scene 
			
			if(!getChildren().isEmpty()) { //if there are elements in the scene
				
				Timeline fade = new Timeline( //create a new time line animation called fade 
						new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)), //add a key frame at full opacity which happens at the start of the fade
						new KeyFrame(new Duration(200), new EventHandler<ActionEvent>() { //add a new key frame which occurs when the action event below happens 
					@Override
					public void handle(ActionEvent e) {
						getChildren().remove(0); 
						getChildren().add(0, screens.get(name));
						
						Timeline fadeIn = new Timeline( //create a new animation time line to fade into a scene
								new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), //create a new key frame which has an opacity of 0 at the start 
								new KeyFrame(new Duration(600), new KeyValue(opacity,1.0))); //create a new key frame which as a full opacity which takes 600 milliseconds to achieve
						fadeIn.play(); //play the fade in animation 
					}
				}, new KeyValue(opacity, 0.0))); // the key frame has a value of 0 which means it is totally see through
				fade.play(); //play the fade animation 
			} 
			
			else { //if get children is empty that mean that then program was just opened/this is the first scene
				setOpacity(0.0); //set the opacity to transparent
				getChildren().add(screens.get(name)); //add the screen to get children 
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), new KeyFrame(new Duration(1500), new KeyValue(opacity, 1.0))); //create a new time line animation that fades in over 1.5 seconds 
				fadeIn.play(); //play the animation to fade in
			}
			return true;
		} else { //if the screens name does not have a screen associated with is in the screens hash map then the screen could not be loaded 
			System.out.println("Screen Not Loaded"); 
			return false;
		}
	}
}
