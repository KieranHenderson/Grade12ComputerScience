package randomInt;
import java.io.*;

public class RandomInt {
	public static void main(String[] args) throws IOException{
		int number;
		int number2;
		number = (int)(Math.random() * 10)+1;
		number2 = (int)(Math.random() * 10)+21;
		System.out.println(number);
		System.out.println(number2);	
	}

}
