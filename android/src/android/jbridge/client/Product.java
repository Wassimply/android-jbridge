package android.jbridge.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Product extends Activity{
	
	private static String TAG = "Products";
	private int id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null)
		{
			id = extras.getInt("id");
		}

		JSONObject json =RestEasy.connect("http://10.0.2.2:8080/JBridge/RestEasy/product/" + id);
		
		try
		{
			//JSONObject product = json.getJSONObject("product");
			if( json != null )
			{
				String name = json.getString("name");
				String price =  json.getString("price");
				String picture = json.getString("picture");
				String quantity = json.getString("quantity"); 
				String description = json.getString("description");
 
				ImageView img = (ImageView) findViewById(R.id.imgProduct);
				img.setImageBitmap(this.getImageBitmap("http://10.0.2.2:8080/JBridge/products/thumbnails/" + picture));
		                  
		        TextView txtName = (TextView) findViewById(R.id.txtName);
            	txtName.setText(name + " (" + quantity + ")");

            	TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
            	txtPrice.setText("£" + price);
            	
            	TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
            	txtDescription.setText(description);

			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            
            // resize the bitmap
            Matrix matrix = new Matrix();
            matrix.postScale(30, 30);
            
            bis.close();
            is.close();
       } catch (IOException e) {
           Log.e(TAG, "Error getting bitmap", e);
       }
       return bm;
    } 
}