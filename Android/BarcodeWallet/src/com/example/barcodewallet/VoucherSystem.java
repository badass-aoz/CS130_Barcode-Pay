package com.example.barcodewallet;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.example.barcodewallet.helper.DatabaseHelper;
import com.example.barcodewallet.model.Voucher;

public class VoucherSystem extends Activity {
	final Activity PARENT = this;
	public final static String EXTRA_VOUCHER_ID = "com.example.barcodewallet.VOUCHERID";
	public final static String EXTRA_VOUCHER_TYPE = "com.example.barcodewallet.VOUCHERTYPE";

	 protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    layout = new LinearLayout(this);
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			layout.setOrientation(LinearLayout.VERTICAL);
			tv = new TextView(this);
			layout.addView(tv);
			setContentView(layout);
			new GetVouchers().execute();
			Button btnAdd = new Button(this);
			btnAdd.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "ONE_D_MODE");
					startActivityForResult(intent, 0);
				}
			});
			btnAdd.setText("Add a Voucher");
			layout.addView(btnAdd);
	 }
	 
	 private void setView(){
		 layout = new LinearLayout(PARENT);
		 layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 layout.setOrientation(LinearLayout.VERTICAL);
		 tv = new TextView(PARENT);
		 layout.addView(tv);
		 tv.setText("You have "+voucherList.size()+" vouchers");
		 for (Voucher t: voucherList){
			 Button b = new Button(this);
			 final String voucherID = t.getBarcodeID();
			 final String voucherType = t.getBarcodeType();
			 final String voucherName = t.getName();
			 b.setOnClickListener(new View.OnClickListener(){
				 public void onClick(View v){
					 Intent intent = new Intent(PARENT,VoucherBarcodeActivity.class);
			    	 intent.putExtra(EXTRA_VOUCHER_ID,voucherID);
			    	 intent.putExtra(EXTRA_VOUCHER_TYPE,voucherType);
			    	 startActivity(intent);
				 }
			 });
			 b.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					DatabaseHelper dbHelper = new DatabaseHelper (PARENT);
			        dbHelper.removeVoucher(voucherName);
			        new GetVouchers().execute();
					return true;
				}
			});
			 b.setText(t.getName());
			 layout.addView(b);
		 }
		 Button btnAdd = new Button(PARENT);
		 btnAdd.setOnClickListener(new View.OnClickListener(){
			 public void onClick(View v){
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				 intent.putExtra("SCAN_MODE", "ONE_D_MODE");
				 startActivityForResult(intent, 0);
			 }
		});
		btnAdd.setText("Add a Voucher");
		layout.addView(btnAdd);
		setContentView(layout);
	 }
	 
	 public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		 if (requestCode == 0) {
		 	if (resultCode == RESULT_OK) {
		 		String barcodeContents = intent.getStringExtra("SCAN_RESULT");
		        String barcodeFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
		        
		        // ask userName, refresh the screen too
		        askVoucherNameDialog(barcodeContents,barcodeFormat);
		 	} else if (resultCode == RESULT_CANCELED) {
		            // Handle cancel
		    }
		}
	}
	 
	 private void askVoucherNameDialog(String argBarcodeContent, String argBarcodeFormat){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Enter Name for the Voucher");
			final EditText nameInput = new EditText(this);
			final String barcodeContent = argBarcodeContent;
			final String barcodeFormat = argBarcodeFormat;
			
			
	        dialogBuilder.setView(nameInput);
	        
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String voucherName = nameInput.getText().toString();
					DatabaseHelper dbHelper = new DatabaseHelper (PARENT);
					if(dbHelper.checkName(voucherName)){
						// if replicate
						askReplicatedVoucherNameDialog(barcodeContent,barcodeFormat);
						return;
					}
					else{
						// if name does not replicate, create an entry
						Voucher item = new Voucher(voucherName,barcodeContent,barcodeFormat);
				        dbHelper.createVoucher(item);
				        new GetVouchers().execute();
						return;
					}
				}
				
			});
			AlertDialog askVoucherName = dialogBuilder.create();
			askVoucherName.show();
		}
	 
	 
	 // evoke when a replicated name found
	 private void askReplicatedVoucherNameDialog(String argBarcodeContent, String argBarcodeFormat){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Name exists, use another one!");
			final EditText nameInput = new EditText(this);
			final String barcodeContent = argBarcodeContent;
			final String barcodeFormat = argBarcodeFormat;
			
			
	        dialogBuilder.setView(nameInput);
	        
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String voucherName = nameInput.getText().toString();
					DatabaseHelper dbHelper = new DatabaseHelper (PARENT);
					if(dbHelper.checkName(voucherName)){
						// if replicate
						askReplicatedVoucherNameDialog(barcodeContent,barcodeFormat);
						return;
					}
					else{
						// if name does not replicate, create an entry
						Voucher item = new Voucher(voucherName,barcodeContent,barcodeFormat);
				        dbHelper.createVoucher(item);
				        new GetVouchers().execute();
						return;
					}
				}
				
			});
			AlertDialog askVoucherName = dialogBuilder.create();
			askVoucherName.show();
		}
	 
	 private class GetVouchers extends AsyncTask<Void, Void, List<Voucher>> {
	     
		 protected List<Voucher> doInBackground(Void... source) {
	    	 DatabaseHelper dbHelper = new DatabaseHelper(PARENT);
	    	 return dbHelper.getAllVouchers();
	     }
		 
		 
		 protected void onPostExecute(List<Voucher> res) {
			 voucherList = res;
			 setView();
	     }
	 }
	 
	 TextView tv;
	 LinearLayout layout;
	 List<Voucher> voucherList;
}
