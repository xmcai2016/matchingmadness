
import java.io.*;
import java.util.*;


public class HighScores {

	public final static String FILENAME = "Highscores.txt";
	private BufferedReader r = new BufferedReader(new FileReader(FILENAME));
	private static LinkedList<TreeMap<Integer, String>> doc;

	private static int highestSeconds;

	private String currLine;

	public static class FormatException extends Exception {
		public FormatException(String msg) {
			super(msg);
		}
	}
	public static int getHighestSeconds() {
		//highestSeconds = doc.getLast().lastKey();
		int i = highestSeconds;
		return i;
	}
	public static int getDocSize() {
		
		int i = doc.size();
		return i;
	}
	private void isValidLine(String line) throws FormatException {
		String[] splited = line.split(",");

		if (splited.length != 2) throw new FormatException(line + " -- invalid");

		String name = splited[0].trim();
		int score = Integer.parseInt(splited[1].trim());

		TreeMap<Integer,String> m = new TreeMap<>();

		m.put(score, name);
		
		//invariant of my linkedlist: 
		//always from lowestseconds (highest score) to highest seconds

		doc.add(m);
		highestSeconds = score;
	}

	public HighScores() throws IOException, FormatException {

		if (r == null) throw new IllegalArgumentException();

		doc = new LinkedList<>();
		this.currLine = r.readLine();

		if (currLine.trim().isEmpty()) currLine = r.readLine();
		try {

			while(currLine != null) {
				isValidLine(currLine);
				currLine = r.readLine();
			}
		} finally { 
			r.close();
		}
	}


	//String representation of Highscores, also stores the lowest score (highest seconds)
	public static String getHighscores() {
		String hs = "<html> <p align='CENTER'> <font color='YELLOW'><font size='6'> "
				+ " <b> TOP TEN HIGH SCORES<font color='WHITE'><font size='5'<i>";
		try {new HighScores();} catch (Exception ex) {
			System.out.println("error in generating HighScores -- in Highscores");
			};
			if (doc == null ) System.out.println("doc is null");
		for (int idx = 0; idx < doc.size(); idx++) {
			TreeMap<Integer, String> map1 = doc.get(idx);
			int sec = map1.firstKey();
			String nam = map1.get(sec);
			hs = hs + "<br> #" +(idx + 1)+ ": "+ nam + " -- " + sec + " Seconds";

		}
		return hs;
	} 
	public static void clearFile() {
		FileWriter fileWriter;
		{
			try {
				fileWriter = new FileWriter(FILENAME,false);
				fileWriter.close();
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public static void writeNewHS() {
		
		clearFile();
		Writer out;
		try {
			out = new BufferedWriter(new FileWriter(FILENAME));
			int counter = 0;
			for (TreeMap<Integer, String> map : doc) {
				int score = map.firstKey();
				String name = map.get(score);
				out.write("\n" + name + "," + score);
				counter++;
				if (counter == 10) break;
			} 

			out.close();
		} catch (Exception e) {
			System.out.print("i caught exception in writNewHS");
		}
	}
	public static void updateHighscores(int seconds, String name) {
		TreeMap<Integer, String> map2 = new TreeMap<>();
		map2.put(seconds, name);
		if (seconds < highestSeconds) {
			for (int idx = 0; idx < Math.min(10, doc.size()); idx++) {
				TreeMap<Integer, String> map1 = doc.get(idx); 
				
				int sec = map1.firstKey();
				
				if (seconds < sec) {
					doc.add(idx, map2);
					
					break;
				}
			}
		}
		else {
			doc.add(map2);

			highestSeconds = seconds;
		}
		writeNewHS();
	}
}