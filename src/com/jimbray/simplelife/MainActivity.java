package com.jimbray.simplelife;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends SherlockActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private JimbrayWebView mWebView;
	
	private Dialog mPgbDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initActionBar();
		init();
		
		mWebView = (JimbrayWebView) findViewById(R.id.jimbray_webview);
		WebSettings webSetting = mWebView.getSettings();
		webSetting.setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d(TAG, "========loading url is " + url + " =======");
				if(url.startsWith(getString(R.string.taobao_home_url)) || url.startsWith(getString(R.string.taobao_home_url_on_pc))) {
					url = getString(R.string.home_url);
				}
				view.loadUrl(url);
				return false;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showProgress(getString(R.string.loading_txt));
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				dismissProgress();
				super.onPageFinished(view, url);
			}
			
		});
		
		mWebView.loadUrl((getString(R.string.home_url)));
	}
	
	private void init() {
		mPgbDlg = new Dialog(this, R.style.Progress_Dialog_Theme);
		mPgbDlg.setContentView(R.layout.layout_progress_loading);
	}
	
	private void showProgress(String txt) {
		if(mPgbDlg != null) {
			mPgbDlg.show();
		}
	}
	
	private void dismissProgress() {
		if(mPgbDlg != null) {
			mPgbDlg.dismiss();
			mPgbDlg.cancel();
		}
	}
	
	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		boolean isProcessed = false;
		
		if(event.getAction() == KeyEvent.ACTION_DOWN) {
			switch(keyCode) {
				case KeyEvent.KEYCODE_BACK:
					Log.d(TAG, "==========OnKeyDown == curUrl= " + mWebView.getUrl());
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
				
			case android.R.id.home:
				String url = null;
				String homeUrl = getString(R.string.home_url);
				if(!TextUtils.isEmpty(url = mWebView.getUrl())) {
					if(!url.equals(homeUrl)) {
						mWebView.loadUrl(homeUrl);
					}
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
