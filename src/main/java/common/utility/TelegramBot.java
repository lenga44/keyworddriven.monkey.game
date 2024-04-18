package common.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelegramBot {
    public static void sendMessTele(String message){
        try {
            // Create URL object
            URL url = new URL("https://api.telegram.org/bot6113240161:AAHqK7JMEOONJNxFH2ctniwIDmr26HLMRkY" +
                    "/sendMessage?chat_id=@noti_tes&text="+message);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            conn.setRequestMethod("GET");

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            Log.info("sendMessTele: "+message);
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            //System.out.println("Response Body: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
