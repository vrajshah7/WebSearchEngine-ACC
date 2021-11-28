package mainEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	public static int pagecount = 0;
	public static int crawlcount = 0;
	static HashSet<String> uniqueL = new HashSet<String>();
	// crawling the web with the given url to get webpages
	public static void webCrawl(String urlToCrawl, int maxLimit) 
{
		uniqueL.add(urlToCrawl);
		try {
			Document doc = Jsoup.connect(urlToCrawl).get();
			String pattern = ".*" + urlToCrawl.replaceAll("^(http|https)://", "") + ".*";
			System.out.println("*********************************************");
			System.out.println("URL Pattern " + pattern);
			System.out.println("***************************************\n"+"CRAWLING WEB PAGES");
			

			Elements linksOnPage;
			linksOnPage = doc.select("a[href]");
			String currentURL;
			for (Element page : linksOnPage) {
				currentURL = page.attr("abs:href");
				if (uniqueL.contains(currentURL)) {
				
				} else if (!Pattern.matches(pattern, currentURL)) {
					
				} else {
					uniqueL.add(page.attr("abs:href"));
					
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// converting html webpages to text file
	public static void htmlToText() {
		try {

			String txt, currentURL;
			String filePath = "C:\\Users\\VRAJ\\Desktop\\acc\\project\\";
			Iterator<String> itr = uniqueL.iterator();
			while (itr.hasNext()) {
				currentURL = itr.next();
				Document document = Jsoup.connect(currentURL).get();
				txt = document.text();
				String fileName = document.title().replaceAll("[^a-zA-Z0-9_-]", "") + ".txt";
				BufferedWriter out = new BufferedWriter(new FileWriter(filePath + fileName, true));

				out.write(currentURL + " " + txt);
				out.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Using spider to crawl webpages
	public static void spider(String urlToCrawl) 
{
		int maxL = 1000;
		webCrawl(urlToCrawl, maxL);
		System.out.println("\nCONVERTING HTML WEB PAGES TO TEXT FILES");
		htmlToText();
		System.out.println("CONVERTED TO TEXT FILES");

	}

}
