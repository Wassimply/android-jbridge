package android.jbridge.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Majid Khosravi
 */ 
public class RestEasy {
	 
    
    // Post JSON to the resteasy web service
    public static HttpResponse doPost(String url, JSONObject c) throws ClientProtocolException, IOException 
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(c.toString());
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");

        request.setEntity(s);
        request.addHeader("accept", "application/json");
        
        return httpclient.execute(request);
    }
    
    // Put JSON to the resteasy web services
    public static HttpResponse doPut(String url, JSONObject c) throws ClientProtocolException, IOException
    {
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPut request = new HttpPut(url);
    	StringEntity s = new StringEntity(c.toString());
    	s.setContentEncoding("UTF-8");
        s.setContentType("application/json");

        request.setEntity(s);
        request.addHeader("accept", "application/json");
        
        return httpclient.execute(request);
    	
    }
    
    // Delete a resource in the resteasy web services
    public static void doDelete(String url) throws ClientProtocolException, IOException{
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpDelete delete = new HttpDelete(url);
    	delete.addHeader("accept", "application/json");
    	httpclient.execute(delete);
    }

 
    // Retrieve a resource from the resteasy web service
    public static JSONObject doGet(String url)
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
 
            // Get the response entity
            HttpEntity entity = response.getEntity();
            
            // If response entity is not null 
            if (entity != null) {
 
                // get entity contents and convert it to string
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
 
                // construct a JSON object with result
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
        
        // Return the json
        return json;
    }
    
    
    // Convert Inputstream to string
    public static String convertStreamToString(InputStream is) {
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
 
}