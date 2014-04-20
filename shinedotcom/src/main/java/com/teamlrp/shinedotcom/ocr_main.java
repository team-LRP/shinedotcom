package com.teamlrp.shinedotcom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ocr_main extends Activity implements View.OnClickListener {
    private final int RESPONSE_OK = 200;

    private final int IMAGE_PICKER_REQUEST = 1;

    private TextView picNameText;

    private String apiKey;
    private String langCode;
    private String fileName;
    final OCRServiceAPI apiClient = new OCRServiceAPI(apiKey);
    EditText ed1, ed2, ed3, ed4, ed5;
    int p1, p2, p3, p4, p5;
    String name = "Name";
    String gender = "Gender";
    String country = "Country";
    String qual = "Qualification";
    String college = "College";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ocr_main);
        ed1= (EditText) findViewById(R.id.editname);
        ed2 = (EditText) findViewById(R.id.editcountry);
        ed3 = (EditText) findViewById(R.id.editgender);
        ed4 = (EditText) findViewById(R.id.editqual);
        ed5 = (EditText) findViewById(R.id.editcollege);

        picNameText = (TextView) findViewById(R.id.imageName);

        final Button pickButton = (Button) findViewById(R.id.picImagebutton);
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starting image picker activity
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), IMAGE_PICKER_REQUEST);
            }
        });

        final Button convertButton = (Button) findViewById(R.id.convert);
        convertButton.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ocr_main, menu);
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

    @Override
    public void onClick(View view) {
        apiKey = "VmP4aXL292";
        langCode = "en";

        // Checking are all fields set
        if (fileName != null && !apiKey.equals("") && !langCode.equals("")) {
            final ProgressDialog dialog = ProgressDialog.show(ocr_main.this, "Loading ...", "Converting to text.", true, false);
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    apiClient.convertToText(langCode, fileName);

                    // Doing UI related code in UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                            // Showing response dialog
                            final AlertDialog.Builder alert = new AlertDialog.Builder(ocr_main.this);
                            alert.setMessage(apiClient.getResponseText());
                            alert.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick( DialogInterface dialog, int id) {
                                        }
                                    });

                            // Setting dialog title related from response code
                            if (apiClient.getResponseCode() == RESPONSE_OK) {
                                alert.setTitle("Success");


                            }
                            else {
                                alert.setTitle("Failed");
                            }
                            p1 = apiClient.getResponseText().indexOf(name);
                            p2 = apiClient.getResponseText().indexOf(country);
                            p3 = apiClient.getResponseText().indexOf(gender);
                            p4 = apiClient.getResponseText().indexOf(qual);
                            p5 = apiClient.getResponseText().indexOf(college);

                            int i=apiClient.getResponseText().length();

                            if(apiClient.getResponseText().contains("Name")==true)
                                ed1.setText(apiClient.getResponseText().substring(p1+name.length()+2, p2));
                            if(apiClient.getResponseText().contains("Country")==true)
                                ed2.setText(apiClient.getResponseText().substring(p2+country.length()+2, p3));
                            if(apiClient.getResponseText().contains("Gender")==true)
                                ed3.setText(apiClient.getResponseText().substring(p3+gender.length()+2, p4));
                            if(apiClient.getResponseText().contains("Qualification")==true)
                                ed4.setText(apiClient.getResponseText().substring(p4+qual.length()+2, p5));
                            if(apiClient.getResponseText().contains("College")==true)
                                ed5.setText(apiClient.getResponseText().substring(p5+college.length()+2, i-8));
                        }
                    });
                }
            });
            thread.start();
        } else {
            Toast.makeText(ocr_main.this, "All data are required.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
            fileName = getRealPathFromURI(data.getData());
            picNameText.setText("Selected: en" + getStringNameFromRealPath(fileName));

        }
    }

    /*
     * Returns image real path.
     */
    private String getRealPathFromURI(final Uri contentUri) {
        final String[] proj = { MediaStore.Images.Media.DATA };
        final Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    /*
     * Cuts selected file name from real path to show in screen.
     */
    private String getStringNameFromRealPath(final String bucketName) {
        return bucketName.lastIndexOf('/') > 0 ? bucketName.substring(bucketName.lastIndexOf('/') + 1) : bucketName;
    }

}
