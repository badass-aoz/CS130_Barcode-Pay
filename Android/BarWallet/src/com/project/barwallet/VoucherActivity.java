package com.project.barwallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.project.barwallet.PaymentActivity.VoucherFragment;


public class VoucherActivity extends Activity {

	ImageView ivBarcode;
	TextView tvName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
	    String barcodeID = intent.getStringExtra(VoucherFragment.EXTRA_VOUCHER_ID);
	    String barcodeType = intent.getStringExtra(VoucherFragment.EXTRA_VOUCHER_TYPE);
	    String barcodeName = intent.getStringExtra(VoucherFragment.EXTRA_VOUCHER_NAME);
	    
	    
	    //initialize ImageView
	    ivBarcode = (ImageView)this.findViewById(R.id.barcode);
	    tvName =(TextView)this.findViewById(R.id.voucherName);
	    // barcode image
	    Bitmap bitmap = null;

	    try {

	        bitmap = BarcodeGenerator.encodeAsBitmap(barcodeID, BarcodeFormat.valueOf(barcodeType), 600, 150);
	        ivBarcode.setImageBitmap(bitmap);

	    } catch (WriterException e) {
	        e.printStackTrace();
	    } catch (IllegalArgumentException e){
	    	e.printStackTrace();
	    }

	   //Barcode name
	    tvName.setText(barcodeName);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voucher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
}
