package question;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class MarketNavigator
{  

	/* Method that gives the distance between two points */
	public static double distanceFinder(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}





	public static int pathFinder(String filename) {

		/* Find the smallestTotalDistance */
		double smallestTotalDistance = 0;

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE


		FileInputStream file;
		ArrayList<String> houseList = new ArrayList<>();  // In a form of [migros, a, b ,c ] 
		ArrayList<int[]> coordinateList = new ArrayList<>(); // In a form of {[24,32], [25,25] }
		ArrayList<House> houseObjects = new ArrayList<>();  //Array list involving house objects

		try {
			//File reading
			file = new FileInputStream(filename);
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String tempString = sc.nextLine();
				String[] stringArray =  tempString.split(" ");
				houseList.add(stringArray[0]);
				int[] intarray = {Integer.parseInt(stringArray[1]),Integer.parseInt(stringArray[2])}; //[x,y]
				coordinateList.add(intarray);

			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0 ; i< coordinateList.size(); i++) {
			//Adding different objects into the arraylist
			houseObjects.add(new House(houseList.get(i), coordinateList.get(i)[0], coordinateList.get(i)[1]));
		}


//		for (House house : houseObjects) {
//			System.out.println(house.toString());
//		}


		// one of the paths to be modified later
		String houses ="";
		
		for  (int i = 1; i < houseList.size() ;i++ ) {
			houses += houseList.get(i);
		}

		
		// Max value to change the value at the beginning
		smallestTotalDistance = Integer.MAX_VALUE;
		//calling permutation func
		ArrayList<String> permutations =permutation(houses);
		for (String iString : permutations) {
			//To find the distance of all possible roads, and compare them with each other to get minimum value
			double res = distanceCalculator(iString, houseObjects,  houseObjects.get(0).getX(),  houseObjects.get(0).getY(), 0);
			if (res <smallestTotalDistance) {
				
				smallestTotalDistance = res;
			}
		}
		
	


		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

		int roundedValue = (int) Math.round(smallestTotalDistance);
		return roundedValue;

	}




	public static double distanceCalculator(String houses, ArrayList<House> houseList , int previousX , int previousY ,double totalDistance) {
		
		// I transferred each element of the permutation list and this func just calculate the distance by their object attributes.
		// Migros is the starting and ending point I didn't include migros in a permutation.
		if (houses.length() ==0) {
			// Since migros is the fixed last element and not included in permutation, I return the total distance after adding last way to the migros.
			totalDistance += distanceFinder(previousX, previousY,  houseList.get(0).getX(), houseList.get(0).getY());
			return totalDistance;
		}
		
		for (int i = 1 ;i <houseList.size() ; i++ ) {
			if (houseList.get(i).getName().equals(houses.substring(0,1))) {
				//Prev x and y are the last coordinates of last visited house.
				totalDistance += distanceFinder(previousX, previousY, houseList.get(i).getX(), houseList.get(i).getY());
				houses = houses.substring(1);
				previousX =  houseList.get(i).getX();
				previousY =  houseList.get(i).getY();
				return distanceCalculator(houses, houseList, previousX, previousY, totalDistance);
				
				
			}
		}
		return totalDistance;
		
	}




	
	public static ArrayList<String> permutation(String houses) {
		
		// In order to keep the values safe from the different branches of recursive func I declared arraylist and returned it.
	    ArrayList<String> res = new ArrayList<String>();
	    // If input string's length is 1, it is the base case
 	    if (houses.length() == 1) {
	        res.add(houses);
	    } else if (houses.length() > 1) {
	        int lastIndex = houses.length() - 1;
	        //  the last character
	        String last = houses.substring(lastIndex);
	        // Rest of the string
	        String rest = houses.substring(0, lastIndex);
	        // For example we start with "abcd". firstly we divide abc and d but after doing that the func actually does nothing at that recursive level
	        //so it goes to single house element and it returns "a" and then func starts to ascend to the upper recursive level and "b" is inserted all possible
	        //places. And since the func returns the arraylist we get {"ab","ba"} and it continues like that.
	        
	        res = merge(permutation(rest), last);
	    }
	    return res;
	}

	//function for inserting an element to the all possible places in the string
	public static ArrayList<String> merge(ArrayList<String> list, String c) {
	    ArrayList<String> res = new ArrayList<>();
	    
	    for (String s : list) {
	        // list contains elements like ["ab", "ba"] and we insert "c" ant get total string.length!(factorial) cases, which is permutation.
	        for (int i = 0; i <= s.length(); ++i) {
	        	// at the corner cases 0,0 and lastindex,lastindex don't have an effect on string and c can be placed both at the beginning and end in the string
	        	String newPerm = s.substring(0, i) + c +s.substring(i);
	            res.add(newPerm);
	        }
	    }
	    return res;
	}
	

	

	public static void main(String[] args) {

		/* This part is for you to test your method, no points will be given from here */
		String path = MarketNavigator.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator+"coordinates.txt";
		int distance = pathFinder("coordinates3.txt");
		System.out.println("Smallest distance:" + distance);
	}

}  

