package android.jbridge.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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
			break;
		}
		
	}
}
