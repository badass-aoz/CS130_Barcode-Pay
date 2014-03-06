package com.project.barwallet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

public class SocketConnTask {

	
	public SocketConnTask(){
	}
	
	
	public void signUpConn(Activity activity, String userName, String passwd, String phoneNum, Uri photo){
		String connString = userName+"`"+passwd+ "`"+phoneNum+"\n";
		
		new ConnSignUp(activity).execute(new String []{connString, photo.toString()});
		
	}
	
	
	private class ConnSignUp extends AsyncTask<String, Void, String> {
		
		//activity of the caller
		private Activity activity;
		public ConnSignUp(Activity activity){
			this.activity = activity;
		}
	     protected String doInBackground(String... connStrings) {
	    	 String statusCode="";
	    	 try{
					Socket clientSocket = new Socket ("108.168.239.90",9800);
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out.println(connStrings[0]);
					
					
					File myFile = new File(connStrings[1].toString());
					FileInputStream fis = null;
			         
					try {
			             fis = new FileInputStream(myFile);
			         } catch (FileNotFoundException e) {
			             // TODO Auto-generated catch block
			             e.printStackTrace();
			         }

			         Bitmap bm = BitmapFactory.decodeStream(fis);
			         byte[] imgbyte = getBytesFromBitmap(bm);
			         out.println(imgbyte);
			         
			         
			        statusCode = in.readLine();
					if(statusCode.length()!=0 )
						clientSocket.close();
					else{
						clientSocket.close();
						throw new Exception();
					}
				}catch (Exception ex){
					ex.printStackTrace();
				}
	    	 return statusCode;
	     }


	     protected void onPostExecute(String code) {
	         if (code == ""){
	        	 String errorString = "Request not sent";
	        	 showDialog(activity,errorString);
	         }
	         else{
	        	 String sucessString = "Connection Succeed";
	        	 showDialog(activity,sucessString);
	         }
	     }
	 }
	
	
	public static void showDialog(Context context,String statusString){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(statusString);
		
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
			
		});
		AlertDialog askVoucherName = dialogBuilder.create();
		askVoucherName.show();
	}
 
	
	
	public byte[] getBytesFromBitmap(Bitmap bitmap) {
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    bitmap.compress(CompressFormat.JPEG, 70, stream);
	    return stream.toByteArray();
	}
	
}
