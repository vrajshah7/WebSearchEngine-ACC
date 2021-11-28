package textProcessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.TreeMap;
/**
 * the spell corrector feature defined 
 */
public class SpellCorrector implements ISpellCorrector{

	Trie trie = new Trie();
	Map<String, Integer> Dict = new HashMap<>();
	final static List<String> invalid = Arrays.asList("abcdefghijklmnopqrstuvwxyz");
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {	
		try {
			FileReader fr = new FileReader(dictionaryFileName);
			BufferedReader br = new BufferedReader(fr);	       
			String line = null;
			while ((line = br.readLine()) != null) {          			        					
				String word = line.toLowerCase();
				if (!line.contains(" ")) {
					Dict.put(word, Dict.getOrDefault(word, 0)+1);
					trie.add(word);
				} else {
					String[] strs= line.split("\\s");
					for(String str: strs) {
						Dict.put(str, Dict.getOrDefault(str, 0)+1);
						trie.add(str);
					}
				}
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	@Override
	public String suggestSimilarWord(String input_word) {
		if (input_word.length()==0 || input_word==null || invalid.contains(input_word.toLowerCase()) )
			return null;
		String s = input_word.toLowerCase();
		String res=null;
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();		
		INode node = trie.find(s);			
		if(node == null) {
			
			for (String w: Dict.keySet()) {
				int d = editDistance(w, s);				
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(d, new TreeMap<>());
				int freq = Dict.get(w);
				TreeSet<String> set = similarWords.getOrDefault(freq, new TreeSet<>());
				set.add(w);			
				similarWords.put(freq, set);
				map.put(d, similarWords);		
			}		
			res= map.firstEntry().getValue().lastEntry().getValue().first();
			
		 } else if (node !=null) {
			 
			 res = s;
		 }
		 return res;
	}

	private int editDistance(String W1, String W2) {
		int n = W1.length();
		int m = W2.length();
	    int ar[][] = new int[n+1][m+1];
	    int i=0;
	    while (i <= n) {
	        for (int j = 0; j <= m; j++) {
	            if (i == 0)
	                ar[i][j] = j;      
	            else if (j == 0)
	                ar[i][j] = i; 
	            else if (W1.charAt(i-1) == W2.charAt(j-1))
	                ar[i][j] = ar[i-1][j-1];	            
	            else if (i>1 && j>1  && W1.charAt(i-1) == W2.charAt(j-2) 
	            		&& W1.charAt(i-2) == W2.charAt(j-1))
	            	ar[i][j] = 1+Math.min(Math.min(ar[i-2][j-2], ar[i-1][j]), Math.min(ar[i][j-1], ar[i-1][j-1]));
	            else
	                ar[i][j] = 1 + Math.min(ar[i][j-1], Math.min(ar[i-1][j], ar[i-1][j-1])); 
	        }
	        i=i+1;
	    } 
	    return ar[n][m];
	}
}
