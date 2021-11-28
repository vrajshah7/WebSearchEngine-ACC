package mainEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import textProcessing.Main;

public class WebSearchEngine {

	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static int n = 1;
	static Scanner sc = new Scanner(System.in);
	static int R;
	static int[] right;

	public WebSearchEngine() {

		System.out.println("--------------------WEB SEARCH ENGINE--------------------");
	}

	// Using KMP for pattern searching against the given word
	public static int searchKMP(String pattern, String word) {
		KMP kobj = new KMP(pattern);
		int offset = kobj.search(word);
		return offset;
	}
	// flow of search engine defined
	public static void searchEngine() {

		WebSearchEngine w = new WebSearchEngine();
		Main main = new Main();
		String urlToCrawl = "https://en.wikipedia.org/";
		Crawler.spider(urlToCrawl); // crawling the url
		System.out.println("\n*Web Pages Crawled*");

		/**
		 * Calculating occurence to find the word frequency in every text files
		 */
		Hashtable<String, Integer> occursw = new Hashtable<String, Integer>();
		Scanner scan = new Scanner(System.in);
		String choice = "yes";

		do {
			try {

				System.out.println("*****************************************************");
				System.out.println("ENTER THE WORD TO BE SEARCHED: ");
				String Query = scan.nextLine();
				long fileNumberc = 0;
				int occurence = 0;
				int pagec = 0;
				try {

					String spellCorr = main.wordsuggestion(Query); // checking for spell corrections

					if (spellCorr != Query) {
						System.out.println("*Incorrect spelling detected*");
						Query = spellCorr;
						System.out.println("Instead seaching," + Query +" for you");
						Query = Query.toLowerCase();
					} else {

					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					File dir = new File("C:\\Users\\VRAJ\\Desktop\\acc\\project");

					File[] fileArray = dir.listFiles();
					for (int i = 0; i < fileArray.length; i++) {
						occurence = SearchWord.wordSearch(fileArray[i], Query); // searching word for the user input
						occursw.put(fileArray[i].getName(), occurence);
						if (occurence != 0)
							pagec++;
						fileNumberc++;
					}
					System.out.println("\n**************************************************************");

					if (pagec == 0) {
						System.out.println("\n\n*********************************************************");
						System.out.println("*Searched word was not found*");
						System.out.println("*Suggesting similar words*");
						SearchWord.altWord(Query); // with regex finding similar pattern
					} else {
						Sorting.pageSort(occursw, pagec); // Merge Sort used for ranking web-page
					}
					System.out.println("\n\n Would you like to search something else(yes/no)?");
					choice = scan.nextLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (choice.equals("yes"));
		System.out.println("\n***************************************************\n");
		System.out.println("THANK YOU FOR USING OUR SEARCH ENGINE, HOPE TO SERVE YOU AGAIN SOON.");
		System.out.println("\n***************************************************\n");

	}

	public static void main(String[] args) { // Main Method
		WebSearchEngine.searchEngine();
	}
}
