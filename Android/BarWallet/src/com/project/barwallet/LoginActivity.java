package com.project.barwallet;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	
	//Create a Telephony Manager
	TelephonyManager tm;
	
	//Phone number
	String phoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

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
		
		tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		phoneNumber = tm.getLine1Number();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
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
	
	
	public String getPhoneNumber (){
		return phoneNumber;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		String phoneNumber;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			switch (position) {
			case 0:
				Fragment loginFragment = new LoginFragment();
				return loginFragment;
			case 1:
				Fragment signupFragment = new SignUpFragment();
				return signupFragment;
			}
			return null;
			
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}
	


	// a fragment that show the login
	public static class LoginFragment extends Fragment {
		public static final String GLOBAL_PREF_NAME ="COM.PROJECT.BARWALLET";
		public static final String USERNAME ="COM.PROJECT.BARWALLET.USERNAME";
		
		Button btnSubmit;
		String userName;
		EditText etUserName;
		EditText etPassword;
		
		String phoneNumber;
		
		public LoginFragment (){
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			etUserName = (EditText) rootView.findViewById(R.id.username);
			etPassword = (EditText) rootView.findViewById(R.id.password);
			
			phoneNumber = ((LoginActivity) getActivity()).getPhoneNumber();
			// if we did not receive the number, we will hard-coded a number for demo purpose
			if(phoneNumber == null){
				phoneNumber = "3100000000";
			}
			
			userName = etUserName.getText().toString();
			btnSubmit = (Button) rootView.findViewById(R.id.Submit);
			btnSubmit.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg){
					
					SharedPreferences myprefs= getActivity().getSharedPreferences( GLOBAL_PREF_NAME, MODE_PRIVATE);
					myprefs.edit().putString(USERNAME, userName).commit();
					
					Intent intent = new Intent(getActivity(), PaymentActivity.class);
					startActivity(intent);
					
					// TODO: Send the information to the server with the phone number
				}
			});
			return rootView;
		}
	}
	
	// a fragment that shows the sign up
	public static class SignUpFragment extends Fragment {
		
		
		ImageButton btnPhoto;
		Uri selectedImageUri;
		Button btnSubmit;
		EditText etUsername;
		EditText etPasswd;
		EditText etRePass;
		EditText etPhoneNumber;
		
		public final String URL_PHOTO = "COM_PROJECT_BARWALLET_LOGIN_URL_PHOTO";
		private static final int SELECT_PICTURE = 1;
		
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState){
			super.onSaveInstanceState(savedInstanceState);
			if(selectedImageUri!=null)
				savedInstanceState.putString(URL_PHOTO, selectedImageUri.toString());
		}
		
	
	
		public SignUpFragment() {
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_signup,
					container, false);
			btnPhoto = (ImageButton) rootView.findViewById(R.id.photo);
			
			btnSubmit = (Button) rootView.findViewById(R.id.Submit);
			etUsername = (EditText) rootView.findViewById(R.id.username);
			etPasswd = (EditText) rootView.findViewById(R.id.password);
			etRePass = (EditText) rootView.findViewById(R.id.rePassword);
			etPhoneNumber =(EditText) rootView.findViewById(R.id.telphone);
			
			final String passwd = etPasswd.getText().toString();
			final String rePasswd = etRePass.getText().toString();
			
			final String userName = etUsername.getText().toString();
			final String phoneNumber = etPhoneNumber.getText().toString();
			
			btnSubmit.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg){
					
					if(!passwd.equals(rePasswd)){
						SocketConnTask.showDialog(getActivity(), "Password is not correct");
						return;
					}
					SocketConnTask newTask = new SocketConnTask();
					newTask.signUpConn(getActivity(),userName, passwd, phoneNumber, selectedImageUri);
					
					return;
				}
			});
//			
			if(savedInstanceState!=null){
				String photoURL = savedInstanceState.getString(URL_PHOTO);
				if(photoURL!=null)
					selectedImageUri = Uri.parse(photoURL);
			}
			
			
			if(selectedImageUri == null){
				btnPhoto.setImageResource(R.drawable.upload_pic);
			}
			else{
				btnPhoto.setImageURI(selectedImageUri);
			}
			btnPhoto.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg){
					Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
				}
				   
			});
			
			
			return rootView;
		}
		
		
		
		
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	                selectedImageUri = data.getData();
	                btnPhoto.setImageURI(selectedImageUri);
	            }
	        }
	    }
		
	}

}
