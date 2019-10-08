//package data_collector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 
 * how to run in Java 11: java RestData.java
 *
 */
public class RestData {
	public static void main(String[] args) {
		String csvFile = "search_keywords.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int LIM = 5;
        try {
        	int count = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null ) {//&& count < LIM) {

                line = line.trim();
                sendRequest(line);
                Thread.sleep(1000 * 60);
                count ++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	private static void sendRequest(String keyword) {
		try {
			URL url = new URL("http://localhost:8080//library-core/api/books/search");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"keyword\": \"" + keyword + "\"}";
			System.out.println("Searching for : " + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			long oldTS = System.currentTimeMillis();
			long LIM_WAIT = 5 * 60 * 1000;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				//System.out.println(output);
				if (System.currentTimeMillis() - oldTS > LIM_WAIT) {
					break;
				}
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
