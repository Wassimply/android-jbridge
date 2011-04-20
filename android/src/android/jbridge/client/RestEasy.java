package android.jbridge.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Majid Khosravi
 */ 
public class RestEasy {
	 
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    // Post JSON to the resteasy web service
    public static HttpResponse doPost(String url, JSONObject c) throws ClientProtocolException, IOException 
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(c.toString());
        //s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");

        request.setEntity(s);
        request.addHeader("accept", "application/json");
        
        return httpclient.execute(request);
    }

 
    /* This is a test function which will connects to a given
     * rest service and prints it's response to Android Log with
     * labels "Praeda".
     */
    public static JSONObject connect(String url)
    {
    	JSONObject json = null;
    	
        HttpClient httpclient = new DefaultHttpClient();
        
 
        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 
        
        // Accept JSON
        httpget.addHeader("accept", "application/json");
 
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
 
                // A Simple JSONObject Creation
                json=new JSONObject(result);
     
 
                // Closing the input stream will trigger connection release
                instream.close();
            }
             
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return json;

    }
 
}