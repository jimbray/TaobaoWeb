package com.jimbray.simplelife;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends SherlockActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private JimbrayWebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initActionBar();
		
		mWebView = (JimbrayWebView) findViewById(R.id.jimbray_webview);
		WebSettings webSetting = mWebView.getSettings();
		webSetting.setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.d(TAG, "========loading url is " + url + " =======");
				if(url.startsWith(getString(R.string.taobao_home_url))) {
					url = getString(R.string.home_url);
				}
				view.loadUrl(url);
				return false;
			}
			
		});
		
		mWebView.loadUrl(getString(R.string.home_url));
	}
	
	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null) {
			
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		boolean isProcessed = false;
		
		if(event.getAction() == KeyEvent.ACTION_DOWN) {
			switch(keyCode) {
				case KeyEvent.KEYCODE_BACK:
					if(!mWebView.getUrl().equals(getString(R.string.home_url))) {
						mWebView.goBack();
						isProcessed = true;
					}
					
					break;
			}
		}
		
		if(isProcessed) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
			case R.id.menu_exit:
				finish();
				break;
			case R.id.menu_left:
				mWebView.goBack();
				break;
				
			case R.id.menu_right:
				mWebView.goForward();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
