import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.Set;

public class WordCounter {

	private static HashTable freqMap = new HashTable();
	private static String[] words;

	public static void main(String[] args) throws IOException {
		readFile(args[0]);
		writeOutput(args[1]);
	}

	public static void readFile(String inputFileName) throws IOException {
		FileReader fReader = new FileReader(inputFileName);
		BufferedReader bReader = new BufferedReader(fReader);
		String line = null;

		while ((line = bReader.readLine()) != null) {
			if (!line.equals("")) {
				words = line.split("\\s+");
				for (int i = 0; i< words.length; i++) {
					words[i] = words[i].toLowerCase();
					if (!freqMap.containsKey(words[i])) {
						freqMap.put(words[i], 1);
					} else {
						freqMap.put(words[i], freqMap.get(words[i]) + 1);
					}
				}
			}
		}

		fReader.close();
		bReader.close();
	}
	
	public static void writeOutput(String outputFileName) throws IOException {
		//FileWriter fWriter = new FileWriter(outputFileName);
		BufferedWriter bWriter = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(outputFileName), "utf-8")));		
		Set<String> keySet = freqMap.keySet();
		
		for (String word : keySet) {
			String output = "("+ word +","+ freqMap.get(word) +") ";
			bWriter.write(output + "\n");
		}
		
		//fWriter.close();
		bWriter.close();
	}
}
