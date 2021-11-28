package mainEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {

	// Searches for word in crawled files
	public static int wordSearch(File filePath, String word)
	{
		int count=0;
		String data="";
		try
		{
			BufferedReader Object = new BufferedReader(new FileReader(filePath));
			String line = null;
			
			while ((line = Object.readLine()) != null){
				data= data+line;
			}
			Object.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		// looking for the position of the word
		String t = data;
			
		int offsetw = 0;
		
		for (int loc = 0; loc <= t.length(); loc += offsetw + word.length()) 
		{
			offsetw = WebSearchEngine.searchKMP(word, t.substring(loc)); 
			if ((offsetw + loc) < t.length()) {
				count++;
			}
		}
		return count;
	}
	
	//strings found based on similar pattern and call edit distance() on those strings
	public static void findData(File sourceFile,int fileNumber,Matcher matcher,String p1) throws FileNotFoundException,ArrayIndexOutOfBoundsException{
		EditDistance.findWord(sourceFile, fileNumber, matcher, p1);
	}
	
	/**
	 * Finds all the words which has edit distance 1 to the provided word
	 * @param p1
	 */
	public static void altWord(String p1)
	{
		String line = " ";
		String pattern_1 = "[a-zA-Z0-9]+";	  
		 
		// Pattern object created
		Pattern r_1 = Pattern.compile(pattern_1);
		// matcher object created
		Matcher m_1 = r_1.matcher(line);
		int fileNumber=0;
		
		File dir = new File("C:\\Users\\VRAJ\\Desktop\\acc\\project");
		File[] fileArray = dir.listFiles();
		for(int i=0 ; i<fileArray.length ; i++)
		{
			try
			{
				findData(fileArray[i],fileNumber,m_1,p1);
				fileNumber++;
			} 
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Integer allowedDist = 1; // edit distance threshold
		boolean matchWord = false;  

		System.out.println("*Suggestions List*");
		int i=0;
		//giving list of suggestions
		for(Map.Entry entry: WebSearchEngine.numbers.entrySet()){
			if(allowedDist == entry.getValue()) {
				i++;
				System.out.print("("+i+") "+entry.getKey()+"\n");		
				matchWord = true;
			}
		}	        
		if(!matchWord) System.out.println("*No similar word suggestions found*");
	}
}
