package com.project.barwallet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
//import android.net.Uri;

public class SocketConnTask {

	
	public SocketConnTask(){
	}
	
	
	public void signUpConn(Activity activity, String userName, String passwd, String phoneNum, Uri photo){
		String connString = "1`"+userName+"`"+passwd+ "`"+phoneNum;
		
		new ConnSignUp(activity).execute(new String []{connString, photo.toString()});
		
	}
	
	
	private class ConnSignUp extends AsyncTask<String, Void, String> {
		
		//activity of the caller
		private Activity activity;
		public ConnSignUp(Activity activity){
			this.activity = activity;
		}
	     protected String doInBackground(String... connStrings) {
	    	 String statusCode="   ";
	    	 try{
					Socket clientSocket = new Socket ("108.168.239.90",9800);
					OutputStream outStream = clientSocket.getOutputStream();
					PrintWriter out = new PrintWriter(outStream,true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
					
					
					Bitmap bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(connStrings[1]));
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        bm.compress(CompressFormat.JPEG, 60, bos);
			        byte[] imgbyte = bos.toByteArray();
			        out.println(connStrings[0]+"`"+((Integer)(imgbyte.length)).toString()+"\n");
			        outStream.write(imgbyte);
			         
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
