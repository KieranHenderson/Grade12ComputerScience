/* Calculator Controller is a class that is in charge of controlling all the actions that may occur while running the calculator application, this includes key inputs as well as mouse inputs */
/* Kieran Henderson */
/* 3/12/20 */
package mycalculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class CalculatorController{
	@FXML //linking FXML file to controller 

	//declaring main variables 
	public TextField displayField;
	private float num1 = 0; //variable stores the first number from the user 
	private String operator = ""; //variable stores the operator 
	private float memory; //variable stores the number in memory currently 

	DecimalFormat format = new DecimalFormat("#.#########################"); //format the answer so that the answer is only showing the necessary decimals to 25 decimal places

	//method for handling key inputs 
	public void buttonPressHandler(KeyEvent e) {
		if(e.getEventType()== KeyEvent.KEY_PRESSED) {	//if a key event happens 
			String buttonPressed = e.getCode().toString();	//figure out which on screen button corresponds with the key pressed 
			switch(buttonPressed) {
				case "DIGIT0": case "DIGIT1": case "DIGIT2": case "DIGIT3": case "DIGIT4": case "DIGIT5": //if the button pushed is a digit then call processDigit with the pushed button as the parameter 
				case "DIGIT6": case "DIGIT7": case "DIGIT8": case "DIGIT9": case "NUMPAD0": case "NUMPAD1":
				case "NUMPAD2": case "NUMPAD3": case "NUMPAD4": case "NUMPAD5": case "NUMPAD6": case "NUMPAD7": case "NUMPAD8": case "NUMPAD9":
					buttonPressed = buttonPressed.substring(buttonPressed.length()-1); //isolate for the final character in the string so that processDigit is only passed a single number character
					processDigit(buttonPressed);
				break;
				/* converting all of the key inputs to their correct symbols to pass to their correct methods */
				case "PERIOD": case "DECIMAL":
					buttonPressed = ".";
					processDigit(buttonPressed); 
				break;
				case "EQUALS": 
					buttonPressed = "=";
					processOperation(buttonPressed);
				break;
				case "ADD":
					buttonPressed = "+";
					processOperation(buttonPressed);
				break;
				case"MINUS": case"SUBTRACT":
					buttonPressed = "-";
					processOperation(buttonPressed);
				break;
				case"MULTIPLY":
					buttonPressed = "X";
					processOperation(buttonPressed);
				break;
				case"DIVIDE":
					buttonPressed = "/";
					processOperation(buttonPressed);
				break;
				case "HOME":
					buttonPressed = "+/-";
					processOperation(buttonPressed);
				break;
				case"ENTER":
					buttonPressed = "=";
					processOperation(buttonPressed);
				break;
				case"COMMA": 
					buttonPressed = "MC";
					processMemory(buttonPressed);
				break;
				case"B": 
					buttonPressed = "M+";
					processMemory(buttonPressed);
				break;
				case"N": 
					buttonPressed = "M-";
					processMemory(buttonPressed);
				break;
				case "M": 
					buttonPressed = "MR";
					processMemory(buttonPressed);
				break;
				case "BACK_SPACE": case"DELETE": case "ESCAPE":
				 	buttonPressed = "C";
					processMemory(buttonPressed);
			}
		}
	}
	//method for handling button clicks 
	public void buttonClickHandler(ActionEvent evt) {
		Button buttonClicked = (Button) evt.getTarget();
		String buttonLabel = buttonClicked.getText(); //figure out which button is pressed and get the label of that button 
		switch(buttonLabel) {
		case "0": case "1": case "2": case "3": case "4": case "5": //if the button pushed is a digit then call processDigit with the pushed button as the parameter 
		case "6": case "7": case "8": case "9": case ".":
			processDigit(buttonLabel);
		break;
		case "=": case "+": case"-": case"X": case"/": case "+/-": //if the button pushed is an operation then call processOperation with the pushed button as the parameter 
			processOperation(buttonLabel);
		break;
		case"MC": case"M+": case"M-": case "MR": case "C": //if the button pushed is a memory function then call processMemory with the pushed button as the parameter 
		 	processMemory(buttonLabel);
		}
	}
	
	//method for processing when a digit is clicked
	public void processDigit(String buttonLabel) {
		String value = buttonLabel;
		displayField.setText(displayField.getText()+value); //display the value of the button label on the displayField + the other numbers that were already on the displayField			
	}
	
	//method for processing when an operation is clicked
	private void processOperation(String buttonLabel) {
		format.setRoundingMode(RoundingMode.HALF_EVEN); //set the rounding mode of the formating to round up when above 5 and down when below
		String value = buttonLabel;
		if (value.equals("+/-")) { // if the button clicked is +/-
			if (displayField.getText().isEmpty()) { //doesn't allow the +/- button to work if there is no number being displayed 
				return;
			}
			if (-Float.parseFloat(displayField.getText()) % 1 ==0) { //check if it is a whole number, if it is then format it, if not don't format it 
				String flipped = format.format(-Float.parseFloat(displayField.getText()));
				displayField.setText(flipped.toString()); //if the button pushed was the +/- button the you display the negative version of the number currently being displayed
			}else {
				displayField.setText(Float.toString(-Float.parseFloat(displayField.getText())));
			}
		}else if (!value.equals("=")) {
			if (displayField.getText().isEmpty()) { // make sure the program doesn't receive runtime errors (operation cannot be used with out two numbers)
				return;
			} else {
				operator = value;
				num1 = Float.parseFloat(displayField.getText()); //if the value of the button does not equal the = then number 1 is assigned to the current value being displayed, the operator is set to the button that was pushed and the display field is reset to be blank
				displayField.setText("");
			}
		}else {
			if (operator.isEmpty()) {	//check to make sure that there is an operator that has been selected, if not then the button doesn't work
				return;
			}
			float num2 = Float.parseFloat(displayField.getText());	//number 2 is equal to the number that is currently displayed on the screen
			float output = calculate(num1, num2, operator); //pass both numbers and the operator to the calculate method and display them
			String ans = format.format(output);
			displayField.setText(ans);
			operator = ""; //clear the operator 
		}
	}
	
	//method for processing when a memory button is clicked
	private void processMemory(String buttonLabel) {
		String value = buttonLabel;
		if (value.equals("MC")) {	//clear memory 
			memory = 0;
		}else if(value.equals("M+")) { 
			if (displayField.getText().isEmpty()) { //make sure that it doesn't crash if the text field is empty (button just wont work)
				return;
			} else {
				memory=memory+Float.parseFloat(displayField.getText()); //add current number to the number in memory 
			}
		}else if (value.equals("M-")) {
			if (displayField.getText().isEmpty()) {//make sure that it doesn't crash if the text field is empty (button just wont work)
				return;
			} else {
				memory=memory-Float.parseFloat(displayField.getText()); //subtract the number being displayed from the number being stored 
			}
		}else if (value.equals("MR")) {
			displayField.setText(format.format(memory)); //recalls the stored number in memory to the display
		}else if (value.equals("C")){	//clears the current display
			displayField.clear();
		}
	}
	
	
	//method for calculating and returning the correct answer based of the operator used
	private float calculate(float num1, float num2, String operator) {
		switch(operator) {
		case"+": //case for when the operation is addition
			return num1+num2;
		case"-": //case for when the operation is subtraction 
			return num1-num2;
		case"X": //case for when the operation is multiplication 
			return num1*num2;
		case"/": //case for when the operation is dividing 
			if (num2==0) { //makes sure I don't divide by zero
				return 0;
			}else {
			return num1/num2;
			}
		default: //catch
			return 0;
		}
	}
}
