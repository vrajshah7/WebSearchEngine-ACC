package mainEngine;
/**
 * Merge sort is used for ranking the web pages.
 * Merge sort is used from Collections.sort by default.
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public class Sorting {
	// Ranking the pages according to word frequency
	public static void pageSort(Hashtable<?, Integer> t,int occur)
	{
		 //Here we transfer as a list and do sorting of it
	       ArrayList<Map.Entry<?, Integer>> LIST = new ArrayList(t.entrySet());
	       Collections.sort(LIST, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o1.getValue().compareTo(o2.getValue()); // it compares the values
	        }});
	      
	       Collections.reverse(LIST);
	       if(occur!=0) {
		       System.out.println("\n-----------------Ranking of WEB-PAGES----------------\n");
		       
		       int n = 10;
		       int j = 1;
		       System.out.printf("%-10s %s\n", "Sr. No.", "File Name \t  occurance");
				System.out.println("******************************************************");
				while (LIST.size() > j && n>0){
					
					  
					 System.out.printf("\n%-10d| %s\n", j, LIST.get(j));
					j++;
					n--;
				}
				System.out.println("\n*****************************************************\n");
	       }
	}

}
