
import java.io.*;
import java.util.Scanner;

public class CsvCreator {
	public static void main(String ... args) {
		File rawFile = new File("NYC_City_Hall_Library_Publications.csv");
		File output = new File("INTEGRATED-DATASET");
		try {
		if (!output.exists()) {
			output.createNewFile();
		}
		Scanner s;
		StringBuffer content = new StringBuffer();
		
			s = new Scanner(rawFile);
			
			for(int i=0;i<10;i++){
			//while(s.hasNext()){
				String line = s.nextLine();
				String[] fields = line.split(",");
				content.append(fields[1]+","+fields[4]+","+fields[6]+"\n");
				
			//}
			}
			
			FileWriter fw = new FileWriter(output.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
