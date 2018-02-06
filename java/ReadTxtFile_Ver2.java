package Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadTxtFile {

	private static final String FILENAME = "D:\\svnbox\\bible\\bible.txt";

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME), "UTF8"))) {

			String sCurrentLine;
			String prevChapter = "";
			String verse = "";
			System.out.println("[");
			boolean lastVerse = false;

			while ((sCurrentLine = br.readLine()) != null) {
				int semicolon = sCurrentLine.indexOf(":");


				if (!prevChapter.equals(sCurrentLine.substring(0, semicolon))) {

					if (prevChapter.length() > 0) {
						System.out.println("]},");
					}
					System.out.println("{\"" + sCurrentLine.substring(0, semicolon) + "\":[");
					
					/*
					System.out.println("{\"chapter\":\"" + sCurrentLine.substring(0, semicolon) + "\",");
					System.out.print("\"contents\": [");
					*/
					lastVerse = true;
				} else {
					System.out.println(",");
				}

				verse = sCurrentLine.substring(semicolon + 1);
				//System.out.print("{ \"verser\":" + verse.substring(0, firstStringIdx(verse)));
				System.out.print("\"" + verse.substring(firstStringIdx(verse), verse.length()) + "\"");
				// System.out.println(verse);

				/*
				if (!lastVerse) {
					System.out.println(",");
				}
				*/
				lastVerse = false;

				prevChapter = sCurrentLine.substring(0, semicolon);
			}

			System.out.println("]}]");

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
}
