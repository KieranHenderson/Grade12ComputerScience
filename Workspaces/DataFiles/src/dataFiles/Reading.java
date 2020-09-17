package dataFiles;

import java.io.*;

public class Reading {
	
	

	public static void main(String[] args) 
	{
		BufferedReader readFile = null;
		try {
			readFile = new BufferedReader (new FileReader ("randomNums.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		int inMarks[] = new int [100];
		
		for (int i=0;i<100;i++) 
		{
			try {
				inMarks[i] = Integer.parseInt(readFile.readLine());
			} catch (NumberFormatException e) {
				System.out.println("sorry, the file must only include numbers");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("The average of the Random numbers is: ");
		System.out.println(findAverage (inMarks));
		
		try {
			readFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int findAverage(int marks[]) 
	{
		int sum=0;
		for (int i = 0; i<marks.length; i++)
		{
			sum+=marks[i];
		}
		return (sum/marks.length);
	}
}
