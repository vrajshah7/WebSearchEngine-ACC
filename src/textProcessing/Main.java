package textProcessing;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Main m = new Main();
		String sample= m.wordsuggestion("Vraj");
		System.out.print(sample);
	}
	// correcting the user search word
	public String wordsuggestion(String word) throws IOException{
		
		String dictionaryFileName = ("C:\\Users\\VRAJ\\Desktop\\acc\\Web_Search_Engine-main\\bin\\textProcessing\\dictionary.txt");
		String inputWord = word;
		ISpellCorrector correction = new SpellCorrector();
		// for incorrect spell search instead for newWord
		correction.useDictionary(dictionaryFileName);
		String newWord = correction.suggestSimilarWord(inputWord);
		if (newWord == null) {
			newWord = "There are no similar words found";
		}
		
		return newWord;
	}

}
