package com.example.barcodewallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;



public class SignUp extends Activity {
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	
	
	public void submit(View view){
		EditText userNameEdit = (EditText) findViewById(R.id.username);
	    String userName = userNameEdit.getText().toString();
	    EditText initPasswdEdit = (EditText) findViewById(R.id.password);
	    String initPassword = initPasswdEdit.getText().toString();
		String passwd = initPassword;
		
		String connString = "1`"+userName + "`" + passwd;
		// create a asynActivity to finish the task.
	    new ConnSignUp().execute(connString);
	}
	
	private class ConnSignUp extends AsyncTask<String, Void, Void> {
	     protected Void doInBackground(String... connStrings) {
	    	 try{
					Socket clientSocket = new Socket ("108.168.239.90",9800);
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out.println(connStrings[0]);
					String statusCode = in.readLine();
					if(statusCode.length()!=0 )
						clientSocket.close();
					else{
						clientSocket.close();
						throw new Exception();
					}
				}catch (Exception ex){
					ex.printStackTrace();
				}
	    	 return null;
	     }


	     protected void onPostExecute(Void res) {
	         // TODO: show a Dialog to indicate result.
	    	 
	    	 
	    	 finish();
	     }
	 }
	

}
