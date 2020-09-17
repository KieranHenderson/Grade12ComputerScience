package dataFiles;

import java.io.*;

public class Writing {

	public static void main(String[] args) 
	{
		int randomNum;
		
		
		PrintWriter fileOut = null;
		try {
			fileOut = new PrintWriter (new FileWriter("randomNums"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i =1; i<=100;i++) 
		{
			randomNum = (int)(Math.random()*100);
			
			fileOut.println(randomNum);
		}
		
		fileOut.close();
	}

}
