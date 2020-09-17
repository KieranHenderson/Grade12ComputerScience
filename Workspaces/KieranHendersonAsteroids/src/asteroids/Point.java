/* Point is a simple class that takes two doubles and basically creates a point with a give x and y position which is used throughout the program but is essential for the generate polygon points class */
/* Kieran Henderson */
/* 6/7/20 */
//Source: https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

public class Point{ //simple class that takes two double and becomes a point, helps with the creation of the asteroids, player, enemy, and bullets 
	double x,y;
	Point(double x, double y){
		this.x=x;
		this.y=y;
	}
}
