package com.teamlrp.shinedotcom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;


public class LinkedInProfile extends Activity {

    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in_profile);

        loginButton  = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                linkedInLogin();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.linked_in_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void linkedInLogin()
    {
        ProgressDialog progressDialog = new ProgressDialog(LinkedInProfile.this);//.show(LinkedInSampleActivity.this, null, "Loadong...");

        LinkedInDialog d = new LinkedInDialog(LinkedInProfile.this, progressDialog);
        d.show();

        //set call back listener to get oauth_verifier value
        d.setVerifierListener(new LinkedInDialog.OnVerifyListener(){
            @Override
            public void onVerify(String verifier)
            {
                try
                {
                    Log.i("LinkedinSample", "verifier: " + verifier);

                    LinkedInAccessToken accessToken = LinkedInDialog.oAuthService.getOAuthAccessToken(LinkedInDialog.liToken, verifier);
                    LinkedInDialog.factory.createLinkedInApiClient(accessToken);

                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getToken());
                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getTokenSecret());
                    Toast.makeText(getApplicationContext(),""+accessToken.getToken(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.i("LinkedinSample", "error to get verifier");
                    e.printStackTrace();
                }
            }
        });

        //set progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

}
