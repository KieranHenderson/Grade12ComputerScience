/* Controlled Screen is an abstract interface which helps when changing scenes as it is implemented by the screens class which means that it is implemented by each of the screen controllers so that I can create a controlled screen object in the screen controller to easily switch between scenes */
/* Kieran Henderson */
/* 6/7/20 */
//Source: https://github.com/acaicedo/JFX-MultiScreen/tree/master/ScreensFramework/src/screensframework

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;
import javafx.stage.Stage;

public interface ControlledScreen {
	//an interface that is abstract and all classes that implement must include these classes 
	//used to change screens 
	
	public void setScreenParent(ScreenController screenParent);
	
	public void getScene(Stage primaryStage);
}
