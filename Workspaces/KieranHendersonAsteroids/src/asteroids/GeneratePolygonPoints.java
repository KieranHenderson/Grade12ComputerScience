/* Generate Polygon Points is a class which will take an array of points and return a vector of the points that go around the outside that can be used to create an asteroid */
/* Kieran Henderson */
/* 6/7/20 */
//Source: https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.util.Vector;
public class GeneratePolygonPoints {
	public static int orientation(Point p, Point q, Point r) {
		double val = (q.y-p.y)*(r.x-q.x) - (q.x - p.x) * (r.y - q.y);
		
		if (val == 0) {
			return 0;
		}
		return (val >0)? 1: 2; //clock wise or counter clock wise
	}
	
	public static Vector<Point> ConvexHull(Point points[], int n) {
		//there must be at least 5 points 
		if (n < 5){
			return null;
		}
		
		//initialize the result 
		Vector<Point> hull = new Vector<Point>();
		
		//find the left most point 
		int leftMostPoint = 0;
		for (int i = 1; i < n; i++) {
			if (points[i].x < points[leftMostPoint].x) {
				leftMostPoint = i;
			}
		}
		
		//starting from the left most point, keep moving counterclockwise until the start point is reached again
		//this loop runs h times where h is the number of points in the result 
		
		int p = leftMostPoint, q;
		do {
			//add current point to result 
			hull.add(points[p]);
			
			q = (p+1) % n;
			
			for (int i = 0; i <n; i++) {
				//if i is more counterclockwise than current q, update q
				if(orientation(points[p],points[i],points[q]) == 2) {
					q = i;
				}
			}
			
			//now that q is the most counterclockwise in respect to p, set p to q for the next iteration 
			p = q;
		} while (p != leftMostPoint);	//while we don't come back to the first point 
		return hull;
	}
}