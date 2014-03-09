package com.project.barwallet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
//import android.net.Uri;

public class SocketConnTask {

	
	public SocketConnTask(){
	}
	
	
	public void signUpConn(Activity activity, String userName, String passwd, String phoneNum, Uri photo){
		String connString = "1`"+userName+"`"+passwd+ "`"+phoneNum;
		
		new ConnSignUp(activity).execute(new String []{connString, photo.toString()});
		
	}
	
	public void loginConn (Activity activity, String userName, String passwd, String phoneNum){
		String connString = "2`"+userName+ "`"+passwd+"`"+phoneNum;
		new ConnLogin(activity).execute(new String []{connString});
	}
	
	// request barcode
	public void requestConn (View rootView, Activity activity, String userName, String phoneNum){
		String connString = "4`"+userName+"`"+phoneNum;
		new ConnRequest(activity,rootView).execute(new String[]{connString});
		
	}
	
	private class ConnRequest extends AsyncTask<String,Void,Bundle>{
		private Activity activity;
		private View rootView;
		public ConnRequest(Activity activity, View rootView){
			this.activity = activity;
			this.rootView = rootView;
		}
		
		protected Bundle doInBackground (String... connStrings){
			try{
				Socket clientSocket = new Socket("108.168.239.90",9800);
				OutputStream outStream = clientSocket.getOutputStream();
				PrintWriter out = new PrintWriter(outStream,true);
				
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				out.println(connStrings[0]);
				
				//get barcode number
				String barcode = in.readLine();
				
				//get bitmap
//				//String numBytesString = in.readLine();
//				if(numBytesString==""||numBytesString==null){
//					clientSocket.close();
//					return null;
//				}
				
				
				ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
				byte[] data = new byte[1024];
				int numRead = 0;
		    	int totalNumRead = 0;
		    	
		    
		    	InputStream inputStream = clientSocket.getInputStream();
		    	while((numRead = inputStream.read(data))!=-1){
		    		byteOutput.write(data,0,numRead);
		    		totalNumRead+=numRead;
		    	}
		    	
		    	
		    	System.out.println(totalNumRead);
		    	
		    	
		    	if(barcode==null || totalNumRead == 0){
		    		clientSocket.close();
		    		return null;
		    	}else{
		    		Bundle bundle = new Bundle();
		    		bundle.putByteArray("bitmapBytes",byteOutput.toByteArray());
		    		bundle.putString("barcode", barcode);
		    		clientSocket.close();
		    		return bundle;
		    	}
		    }catch(Exception e){
		    	e.printStackTrace();
		    	return null;
		    }
		}
		
		protected void onPostExecute(Bundle bundle) {
	         if (bundle == null){
	        	 String errorString = "Sorry, failed to receive data";
	        	 showDialog(activity,errorString);
	         }
	         else{
	        	 ImageButton btnReq = (ImageButton)rootView.findViewById(R.id.photo);
	        	 ImageView ivBarcode = (ImageView)rootView.findViewById(R.id.barcode);
	        	 
	        	 byte[] picData = bundle.getByteArray("bitmapBytes");
	        	 Bitmap bitmap = BitmapFactory.decodeByteArray( picData, 0,picData.length );
	        	 btnReq.setImageBitmap(bitmap);
	        	 
	        	 
	        	 Bitmap bmBarcode;
	        	 try{
					bmBarcode = BarcodeGenerator.encodeAsBitmap(bundle.getString("barcode"), BarcodeFormat.CODE_128, 600, 150);
	        	 }catch(Exception e){
					e.printStackTrace();
					return ;
	        	 }
	        	 ivBarcode.setImageBitmap(bmBarcode);
	         }
	     }
		
	}
	
	
	private class ConnLogin extends AsyncTask<String,Void,String>{
		
		private Activity activity;
		public ConnLogin(Activity activity){
			this.activity = activity;
		}
		
		protected String doInBackground (String... connStrings){
			String statusCode="";
	    	 try{
					Socket clientSocket = new Socket ("108.168.239.90",9800);
					OutputStream outStream = clientSocket.getOutputStream();
					PrintWriter out = new PrintWriter(outStream,true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
			        out.println(connStrings[0]);
			         
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
	         if (code.contains("ERROR") || code == null){
	        	 String errorString = "Sorry, failed to login";
	        	 showDialog(activity,errorString);
	         }
	         else{
	        	 Intent intent = new Intent(activity, PaymentActivity.class);
	        	 activity.startActivity(intent);
	         }
	     }
		
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
	         if (code.contains("ERROR") || code == null){
	        	 String errorString = "Sorry, failed to create an account";
	        	 showDialog(activity,errorString);
	         }
	         else{
	        	 String sucessString = "Account created";
	        	 Intent intent = activity.getIntent();
	        	 activity.finish();
	        	 activity.startActivity(intent);
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
