package Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Test {
	
	public static void readEvent() {
		String FILE_URL = "data.txt";
    	File file = new File(FILE_URL);
        InputStream inputStream;
        try (
        		FileInputStream fis = new FileInputStream(file);
        		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        		BufferedReader reader = new BufferedReader(isr)
        	){
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void writeEvent() throws IOException {
		File file = new File("Event.txt");
		try (FileOutputStream fos = new FileOutputStream(file);
	             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
	             BufferedWriter writer = new BufferedWriter(osw)
	        ) {
			writer.append("Hello");
            writer.newLine();
			

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			readEvent();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		readEvent();
	}

}
