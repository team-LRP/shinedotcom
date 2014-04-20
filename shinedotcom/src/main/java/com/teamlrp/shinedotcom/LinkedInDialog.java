package com.teamlrp.shinedotcom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 20/04/14.
 */
public class LinkedInDialog extends Dialog {
    private ProgressDialog progressDialog = null;

    public static LinkedInApiClientFactory factory;
    public static LinkedInOAuthService oAuthService;
    public static LinkedInRequestToken liToken;

    public static final String LINKEDIN_CONSUMER_KEY = "757gcoig872o8m";
    public static final String LINKEDIN_CONSUMER_SECRET = "1ejxSLDIUAf6whZJ";

    public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
    public static final String OAUTH_CALLBACK_HOST = "callback";
    public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

    public LinkedInDialog(Context context, ProgressDialog progressDialog)
    {
        super(context);
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//must call before super.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_in_dialog);

        setWebView();
    }


    private void setWebView()
    {
        LinkedInDialog.oAuthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET);
        LinkedInDialog.factory = LinkedInApiClientFactory.newInstance(LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET);

        LinkedInDialog.liToken = LinkedInDialog.oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);

        WebView mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);

        Log.i("LinkedinSample", LinkedInDialog.liToken.getAuthorizationUrl());
        mWebView.loadUrl(LinkedInDialog.liToken.getAuthorizationUrl());
        mWebView.setWebViewClient(new HelloWebViewClient());

        mWebView.setPictureListener(new WebView.PictureListener()
        {
            @Override
            public void onNewPicture(WebView view, Picture picture)
            {
                if(progressDialog != null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }

            }
        });

    }

    class HelloWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if(url.contains(OAUTH_CALLBACK_URL))
            {
                Uri uri = Uri.parse(url);
                String verifier = uri.getQueryParameter("oauth_verifier");

                cancel();

                for(OnVerifyListener d : listeners)
                {
                    //call listener method
                    d.onVerify(verifier);
                }
            }
            else
            {
                Log.i("LinkedinSample", "url: " + url);
                view.loadUrl(url);
            }

            return true;
        }
    }

    /**
     * List of listener.
     */
    private List<OnVerifyListener> listeners = new ArrayList<OnVerifyListener>();

    /**
     * Register a callback to be invoked when authentication  have finished.
     *@param data The callback that will run
     */
    public void setVerifierListener(OnVerifyListener data)
    {
        listeners.add(data);
    }

    /**
     * Listener for oauth_verifier.
     */
    interface OnVerifyListener
    {
        /**
         * invoked when authentication  have finished.
         * @param verifier oauth_verifier code.
         */
        public void onVerify(String verifier);
    }

}
