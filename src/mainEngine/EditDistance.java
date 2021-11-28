package mainEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;


public class EditDistance {


	public static int findEditDistance(String word1, String word2)
	{
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
		return dp[len1][len2];		
	}
	
	/**
	 * Find the given word in the file
	 * @param sourceFile
	 * @param fileNumber
	 * @param matcher
	 * @param p1
	 */
	public static void findWord(File sourceFile,int fileNumber,Matcher matcher,String p1)
	{
		try

    	{

			FileReader aa = new FileReader(sourceFile);
			BufferedReader x = new BufferedReader(aa);
			String line = null;
			
			while ((line = x.readLine()) != null){
				matcher.reset(line);
				while (matcher.find()) {
					WebSearchEngine.key.add(matcher.group());
				}
			}
			x.close();
			int p=0,W = WebSearchEngine.key.size();

			while(p<W)
			{
				WebSearchEngine.numbers.put(WebSearchEngine.key.get(p), findEditDistance(p1.toLowerCase(),WebSearchEngine.key.get(p).toLowerCase()));
				p++;
			}



    	}     



    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
	}
}
