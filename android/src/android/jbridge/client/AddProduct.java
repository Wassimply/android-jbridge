package android.jbridge.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AddProduct extends Activity implements OnClickListener{
	
	private static String TAG = "AddProduct";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addproduct);
		
		View addProductButton = findViewById(R.id.btnAddProduct);
        addProductButton.setOnClickListener(this); 
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnAddProduct:
			JSONObject json = new JSONObject();
			try
			{
				json.put("name", ((EditText) findViewById(R.id.txtName)).getText());
				json.put("price", ((EditText) findViewById(R.id.txtPrice)).getText());
				json.put("picture", ((EditText) findViewById(R.id.txtPicture)).getText());
				json.put("quantity", ((EditText) findViewById(R.id.txtQuantity)).getText());
				json.put("description", ((EditText) findViewById(R.id.txtDescription)).getText());
				
				RestEasy.doPost(getResources().getString(R.string.resteasy_url) + "/addProduct", json);
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClientProtocolException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
	}
}
