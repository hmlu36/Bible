package tkmagiclab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang3.StringEscapeUtils;

public class ReadTxtFile {

	private static final String INPUT_FILE = "D:\\svnbox\\bible\\java\\bible.txt";

	private static final String OUTPUT_FILE = "D:\\svnbox\\bible\\data\\bible.json";

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_FILE), "BIG5"))) {

			String sCurrentLine;
			String prevChapter = "";
			String verse = "";
			println("[");
			boolean lastVerse = false;
			
			while ((sCurrentLine = br.readLine()) != null) {
				int semicolon = sCurrentLine.indexOf(":");

				if (!prevChapter.equals(sCurrentLine.substring(0, semicolon))) {

					if (prevChapter.length() > 0) {
						println("]},");
					}
					println("{\"" + sCurrentLine.substring(0, semicolon) + "\":[");

					/*
					 * System.out.println("{\"chapter\":\"" +
					 * sCurrentLine.substring(0, semicolon) + "\",");
					 * System.out.print("\"contents\": [");
					 */
					lastVerse = true;
				} else {
					println(",");
				}

				verse = sCurrentLine.substring(semicolon + 1);
				// System.out.print("{ \"verser\":" + verse.substring(0,
				// firstStringIdx(verse)));
				if (verse.contains("&#")) {
					print("\"" + StringEscapeUtils.unescapeHtml4(verse.substring(firstStringIdx(verse), verse.length()).replace(" ", "")) + "\"");					
				} else {
					print("\"" + verse.substring(firstStringIdx(verse), verse.length()).replace(" ", "") + "\"");
				}

				/*
				 * if (!lastVerse) { System.out.println(","); }
				 */
				lastVerse = false;

				prevChapter = sCurrentLine.substring(0, semicolon);
			}

			println("]}]");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int firstStringIdx(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (!isNumeric(Character.toString(word.charAt(i)))) {
				return i;
			}
		}

		return 0;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public static void print(String string) { 
		try {
			Files.write(Paths.get(OUTPUT_FILE), string.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void println(String string) {
		print(string + "\n");
	}
	
}
