package com.example.barcodewallet;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ChooseAction extends Activity {
	
	final Activity parent = this;

	public final static String EXTRA_HASH = "com.example.barcodewallet.HASH";
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("barcodeContents",barcodeContents);
		savedInstanceState.putString("barcodeFormat", barcodeFormat);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_action);
		
		// retrieve the user name from the shared preference lists.
		SharedPreferences myprefs= this.getSharedPreferences("user", MODE_PRIVATE);
		userName = myprefs.getString("user_name","");
		
		TextView tv = (TextView) findViewById(R.id.textView);
		
		
		if(savedInstanceState != null){
			barcodeContents = savedInstanceState.getString("barcodeContents");
			barcodeFormat = savedInstanceState.getString("barcodeFormat");
		}
		
		tv.setText(userName);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_action, menu);
		return true;
		
	}
	
	
	public void showBarcode (View view){
		// ask the passwd and stored to the private field
		askPasswordDialog();
		
	}
	
	
	private void askPasswordDialog(){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Enter Passwords");
		final EditText passwdInput = new EditText(this);
		
		// set the attributes of the EditText
		passwdInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
		
        dialogBuilder.setView(passwdInput);
        
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String passwd = passwdInput.getText().toString();
				String connString = "4`"+userName+"`"+passwd;
				new ConnHash().execute(connString);
				return;
			}
			
		});
		askPasswd = dialogBuilder.create();
		askPasswd.show();
	}
	private class ConnHash extends AsyncTask<String, Void, String> {
	     protected String doInBackground(String... connString) {
	    	 String hashVal = "";		// used to store hashVal from server
	    	 try{
					Socket clientSocket = new Socket ("108.168.239.90",9800);
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out.println(connString[0]);
					
					String statusCode = in.readLine();
					
					// TOCHANGE: change the condition for the validation
					if(statusCode.length() ==0){	// check statusCode for validation.
						// TODO: Handle Error
						
						
						clientSocket.close();
						throw new Exception();
					}
					
					
					hashVal = in.readLine();
					if(hashVal.length()!=0 )
						clientSocket.close();
					else{
						clientSocket.close();
						throw new Exception();
					}
				}catch (Exception ex){
					ex.printStackTrace();
				}
	    	 return hashVal;
	     }


	     protected void onPostExecute(String res) {
	    	 // TODO: check whether there's error in the result string indicates error
	    	 
	         // TODO: show a Dialog to indicate problem.
	    	 
	    	 // create new intent to display the hash number
	    	 Intent intent = new Intent(parent,BarCodeActivity.class);
	    	 intent.putExtra(EXTRA_HASH,res);
	    	 startActivity(intent);
	    	 
	     }
	 }
	
	public void linkCreditCard (View view){
		Intent intent = new Intent (this,LinkCardActivity.class);
		startActivity(intent);
	}
	
	public void voucher(View view){
		Intent intent = new Intent(this,VoucherSystem.class);
		startActivity(intent);
	}
	

	
	
	
	
		

	private String userName;
	private String barcodeFormat;
	private String barcodeContents;
	private AlertDialog	askPasswd;
	
}
