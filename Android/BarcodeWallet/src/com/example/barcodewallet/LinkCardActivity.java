package com.example.barcodewallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class LinkCardActivity extends Activity {

	final Activity parent = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_card);
		SharedPreferences myprefs= this.getSharedPreferences("user", MODE_PRIVATE);
		userName = myprefs.getString("user_name","");
		
		Spinner spinner = (Spinner) findViewById(R.id.cardType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.CardType, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_card, menu);
		return true;
	}
	public void linkCard (View view){
		EditText CardHolderName1 = (EditText) findViewById(R.id.CardHolderName);
	    CardHolderName = CardHolderName1.getText().toString();
	    EditText CreditCardNumber1 = (EditText) findViewById(R.id.CreditCardNumber);
	    cardNumber = CreditCardNumber1.getText().toString();
        EditText cvv = (EditText) findViewById(R.id.CVV);
        cvvNumber = cvv.getText().toString();
        EditText expirMonth = (EditText) findViewById(R.id.Month);
        expirationMonth = expirMonth.getText().toString();
        EditText expirYear = (EditText) findViewById(R.id.Year);
        expirationYear = expirYear.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.cardType);
        cardType = spinner.getSelectedItem().toString();
		askPasswordDialog();
	}
	private void askPasswordDialog(){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Confirm password");
		final EditText passwdInput = new EditText(this);
		
		// set the attributes of the EditText
		passwdInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
		
        dialogBuilder.setView(passwdInput);
        
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String passwd = passwdInput.getText().toString();
				String connString = "2"+"`"+userName+"`"+passwd+"`"+cardType+"`"+cardNumber+"`"+expirationMonth+"`"+expirationYear+"`"+cvvNumber+"`"+CardHolderName+"\n";;
				new ConnLink().execute(connString);
				return;
			}
			
		});
		askPasswd = dialogBuilder.create();
		askPasswd.show();
	}
	private class ConnLink extends AsyncTask<String, Void, Void> {
	     protected Void doInBackground(String... connString) {
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
					
					clientSocket.close();
				}catch (Exception ex){
					ex.printStackTrace();
				}
	    	 return null;
	     }


	     protected void onPostExecute(Void res) {
	    	 // TODO: check whether there's error in the result string indicates error
	    	 
	         // TODO: show a Dialog to indicate problem.
	    	 
	    	 // go back
	    	 parent.finish();
	    	 
	     }
	 }
	private String userName;
    private String cardType;
    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;
    private String cvvNumber;
    private String CardHolderName;
	private AlertDialog	askPasswd;
}
