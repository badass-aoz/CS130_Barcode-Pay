package com.project.barwallet;

import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class PaymentActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	List<Voucher> voucherList;
	DatabaseHelper dbHelper;
	
	Fragment fragmentSignUp;
	Fragment fragmentVoucher;
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
		    public int getCount() {
		        return 2;
		    }

		    public Fragment getItem(int position) {
				if(position==0){
					fragmentSignUp = new BarcodeFragment();
					return fragmentSignUp;
				}
				else{
					fragmentVoucher = new VoucherFragment();
					return fragmentVoucher;
				}
		    }
		    
		    @Override
			public CharSequence getPageTitle(int position) {
				Locale l = Locale.getDefault();
				switch (position) {
				case 0:
					return getString(R.string.BarcodePay).toUpperCase(l);
				case 1:
					return getString(R.string.Voucher).toUpperCase(l);
				}
				return null;
			}
			
			@Override
			public int getItemPosition(Object object) {
			    return POSITION_NONE;
			}
		});

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		
		
		dbHelper =  new DatabaseHelper(this);
		voucherList = dbHelper.getAllVouchers();
		FragmentStatePagerAdapter mSectionsPagerAdapter = (FragmentStatePagerAdapter)mViewPager.getAdapter();

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		
	}
	
	// getter for the voucher list
	public List<Voucher> getVouchers(){
		dbHelper =  new DatabaseHelper(this);
		voucherList = dbHelper.getAllVouchers();
		return voucherList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
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
	
	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	

	
	public void refresh(){
		mViewPager.getAdapter().notifyDataSetChanged();
	}
	
	
	
	public static class BarcodeFragment extends Fragment {
		
		ImageButton btnReq;
		ImageView ivBarcode;
		Bitmap bmBarcode;
		View rootView;
		
		String phoneNumber;
		
		public BarcodeFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
				rootView = inflater.inflate(R.layout.fragment_barcode,
					container, false);
				btnReq = (ImageButton)rootView.findViewById(R.id.photo);
				ivBarcode = (ImageView)rootView.findViewById(R.id.barcode);
				
				TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(TELEPHONY_SERVICE);
				phoneNumber = tm.getLine1Number();
				
				btnReq.setOnClickListener(new OnClickListener(){
				
				
				
				@Override
				public void onClick(View arg){
					String username= null;
					String phoneNumber = null;
					SocketConnTask conn = new SocketConnTask();
					SharedPreferences myprefs= getActivity().getSharedPreferences( LoginActivity.GLOBAL_PREF_NAME, MODE_PRIVATE);
					//get userName
					username = myprefs.getString(LoginActivity.USERNAME, null);
					
					if(phoneNumber == null){
						phoneNumber = "3100000000";
					}
					conn.requestConn(rootView, getActivity(), username, phoneNumber);
				}
			});
			
			return rootView;
		}
	}
	
	
	// Voucher Fragment
	public static class VoucherFragment extends Fragment{
		
		
		public final static String EXTRA_VOUCHER_ID = "com.example.barcodewallet.VOUCHERID";
		public final static String EXTRA_VOUCHER_TYPE = "com.example.barcodewallet.VOUCHERTYPE";
		public final static String EXTRA_VOUCHER_NAME = "com.example.barcodewallet.VOUCHERNAME";
		Button btnAddVoucher;
		List<Voucher> voucherList;
		LinearLayout layout;
		
		
		
		public VoucherFragment(){
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_voucher,
					container, false);
			btnAddVoucher = (Button) rootView.findViewById(R.id.addVoucher);
			btnAddVoucher.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "ONE_D_MODE");
					startActivityForResult(intent, 0);
				}
			});
			
			// get the voucher list from the Payment Activity
			voucherList = ((PaymentActivity)getActivity()).getVouchers();
			
			layout = (LinearLayout)rootView.findViewById(R.id.dynamic);
			
			initFragment();
			
			return rootView;
		}
		
		private void initFragment(){
			voucherList = ((PaymentActivity)getActivity()).getVouchers();
			for (Voucher t: voucherList){
				RelativeLayout.LayoutParams rel_bottone = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,150);
				Button b = new Button(getActivity());
				b.setLayoutParams(rel_bottone);
				b.getBackground().setAlpha(90);
				final String voucherID = t.getBarcodeID();
				final String voucherType = t.getBarcodeType();
				final String voucherName = t.getName();
				b.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v){
						Intent intent = new Intent(getActivity(),VoucherActivity.class);
				    	intent.putExtra(EXTRA_VOUCHER_ID,voucherID);
				    	intent.putExtra(EXTRA_VOUCHER_TYPE,voucherType);
				    	intent.putExtra(EXTRA_VOUCHER_NAME,voucherName);
				    	startActivity(intent);
					}
				});
				b.setOnLongClickListener(new View.OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						DatabaseHelper dbHelper = new DatabaseHelper (getActivity());
				        dbHelper.removeVoucher(voucherName);
				        
				        refreshFragment();
				        
						return true;
					}
				});
				 b.setText(t.getName());
				 layout.addView(b);
			}
		}
		
		private void refreshFragment(){
			layout.removeAllViews();
			initFragment();
		}
		
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			 if (requestCode == 0) {
			 	if (resultCode == Activity.RESULT_OK) {
			 		String barcodeContents = intent.getStringExtra("SCAN_RESULT");
			        String barcodeFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
			        
			        // ask userName, refresh the screen too
			        askVoucherNameDialog(barcodeContents,barcodeFormat);
			        
			 	} else if (resultCode == Activity.RESULT_CANCELED) {
			            // Handle cancel
			    }
			}
		}
		
		/* ask Voucher Dialog
		 * The following will evoke Naming Dialog
		 * 
		 */
		public void askVoucherNameDialog(String argBarcodeContent, String argBarcodeFormat){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
			dialogBuilder.setTitle("Enter Name for the Voucher");
			final EditText nameInput = new EditText(getActivity());
			final String barcodeContent = argBarcodeContent;
			final String barcodeFormat = argBarcodeFormat;
			
			final DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
			
			
	        dialogBuilder.setView(nameInput);
	        
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String voucherName = nameInput.getText().toString();
					if(dbHelper.checkName(voucherName)){
						// if replicate
						askReplicatedVoucherNameDialog(barcodeContent,barcodeFormat);
						return;
					}
					else{
						// if name does not replicate, create an entry
						Voucher item = new Voucher(voucherName,barcodeContent,barcodeFormat);
				        dbHelper.createVoucher(item);
				        refreshFragment();
						return;
					}
				}
				
			});
			AlertDialog askVoucherName = dialogBuilder.create();
			askVoucherName.show();
		}
	 
	 
		// evoke when a replicated name found
		public void askReplicatedVoucherNameDialog(String argBarcodeContent, String argBarcodeFormat){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
			dialogBuilder.setTitle("Name exists, use another one!");
			final EditText nameInput = new EditText(getActivity());
			final String barcodeContent = argBarcodeContent;
			final String barcodeFormat = argBarcodeFormat;
			final DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
			
			
	        dialogBuilder.setView(nameInput);
	        
			dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {		
			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String voucherName = nameInput.getText().toString();
					if(dbHelper.checkName(voucherName)){
						// if replicate
						askReplicatedVoucherNameDialog(barcodeContent,barcodeFormat);
						return;
					}
					else{
						// if name does not replicate, create an entry
						Voucher item = new Voucher(voucherName,barcodeContent,barcodeFormat);
						dbHelper.createVoucher(item);
						refreshFragment();
						return;
					}
				}
				
			});
			AlertDialog askVoucherName = dialogBuilder.create();
			askVoucherName.show();
		}
	}
}
