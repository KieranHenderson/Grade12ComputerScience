/* RadixConversions is the class of the KieranRadixConversions project which stores all of the created methods */
/* Kieran Henderson */
/* 3/4/20 */
package radixConversions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RadixConversions {
	//Declaring the main arrays used for hex conversions
	static char hexEquivalent [] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	static String hex = "0123456789abcdef";
	
	//method to convert binary numbers to decimal numbers
	public static int binToDec(String binNum) {
		//initialize variables 
		int binaryModifier = 1;
		int decimal = 0;
		
		//for every 1 in the string add an amount dependent on how many ones have already been added to the decimal sum
		for (int i = 0, length = binNum.length(); i<length;i++) {
			if (binNum.charAt(length-1-i)=='1') {
				decimal = decimal + binaryModifier;
			}
			//increase modifier by multiplying by 2 each time just like the value of each binary column
			binaryModifier = binaryModifier*2;
			
		}
		return decimal;
	}
	
	//method to convert decimal numbers to binary numbers
	public static String decToBin(double decimalNum) {
		//initialize variable 
		List<Integer> binary = new ArrayList<Integer>();
		String binaryNum =null;
		
		//loop until everything has been converted to a binary number
		//use modulus 2 and keep checking the remainder and take appropriate action based off the answer
		while (decimalNum>0) {
			if (decimalNum%2 == 1) {
				binary.add(1);
			}else if (decimalNum%2 == 0) {
				binary.add(0);
			}else if (decimalNum ==0) {
				binary.add(1);
			}
			decimalNum = decimalNum/2;
			decimalNum = Math.floor(decimalNum);
		}
		//flip the list to correctly align all of the binary values 
		Collections.reverse(binary);
		
		//for loop to remove all of the numbers from the array and present them all in a line
		for (int i = 0; i<binary.size();i++) {
			if(binaryNum ==null) {
				binaryNum=Integer.toString(binary.get(i));
			}else {
			binaryNum = binaryNum+Integer.toString(binary.get(i));
			}
		}
		return binaryNum;
	}
	
	//method to convert decimal numbers to hexadecimal numbers numbers
	public static String decToHex(int decimalNum) {
		//initialize the variable 
		String strBinary = decToBin(decimalNum);
		int length = strBinary.length();
		
		//check to make sure that the amount of digits in the binary number is a multiple of 4 for the next step and if it is not then I add leading zeros to compensate
		for (int i=1;i<=4;i++) {
			if (length%4!=0) {
				strBinary = "0"+strBinary;
				length = strBinary.length();
			}
		}
	
		//now use for loops to separate sections of 4 digits into a 2d array
		String hex = "";
		String element = "";
		for (int i=1;i<=length/4;i++) {
			String temp = "";
			for (int j = 1; j<=4;j++) {
				temp = temp+strBinary.charAt(((i-1)*4)+j-1);
			}
			//convert the 4 digit binary number into hex
			element = (String) String.valueOf(Array.get(hexEquivalent, binToDec(temp)));
			System.out.println(element);
			//Concatenation of elements
			hex = hex.concat(element);
		}
		return hex;
	}
	
	//method to convert hexadecimal numbers to decimal numbers numbers
	public static int hexToDec(String hexNum) {
		//initialize the variable 
		int decimal=0;
		int value=0;
		
		//for loop to determine the value of each part of the number (example: f = 15) and then adds it to the total value
		for (int i=0; i<hexNum.length();i++) {
			//use formula for finding decimal number using hexadecimal
			value = (int) ((int) (hex.indexOf(hexNum.charAt(i)))*Math.pow(16, hexNum.length()-i-1));
			decimal = decimal+value;
		}
		return decimal;
	}
	
	//method to convert binary numbers to hexadecimal numbers which uses my other methods to convert from binary to decimal and then from decimal to hexadecimal
	public static String binToHex(String binary) {
		String hex=decToHex(binToDec(binary));
		return hex;
	}
	//method to convert binary numbers to hexadecimal numbers which uses my other methods to convert from hexadecimal to decimal and then from decimal to binary
	public static String hexToBin(String hex) {
		String bin = decToBin(hexToDec(hex));
		return bin;
	}
}
