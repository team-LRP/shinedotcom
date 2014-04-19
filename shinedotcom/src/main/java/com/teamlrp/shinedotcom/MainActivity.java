package com.teamlrp.shinedotcom;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import java.util.Locale;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {


    protected static final int RESULT_SPEECH = 1;


    Button buttonSpeak;

    TextView txt, txt1, txt2, txt3;
    EditText ed1, ed2, ed3;
    TextToSpeech tts;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txt1 = (TextView) findViewById(R.id.name);
        txt2 = (TextView) findViewById(R.id.location);
        txt3 = (TextView) findViewById(R.id.gender);
        ed1 = (EditText) findViewById(R.id.editname);
        ed2 = (EditText) findViewById(R.id.editlocation);
        ed3 = (EditText) findViewById(R.id.editgender);

        tts = new TextToSpeech(this, this);

        buttonSpeak = (Button) findViewById(R.id.button);

        buttonSpeak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                speakTheText();

<<<<<<< HEAD
=======
                i = 0;
                if (i == 0) {

                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                    try {
                        startActivityForResult(intent, RESULT_SPEECH);
                        ed1.setText("");
                    } catch (ActivityNotFoundException e) {
                        Toast t = Toast.makeText(getApplicationContext(), "Oops major fail", Toast.LENGTH_SHORT);
                        t.show();

                    }
                }
>>>>>>> FETCH_HEAD


            }
        });
    }






    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Ready to Speak", Toast.LENGTH_LONG).show();
                speakTheText();
            }

        } else {
            Toast.makeText(this, "Can Not Speak", Toast.LENGTH_LONG).show();
        }

    }


        @Override
        public void onDestroy()
        {
            // Do Not forget to Stop the TTS Engine when you do not require it
            if (tts != null)
            {
                tts.stop();
                tts.shutdown();
            }
            super.onDestroy();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ed1.setText(text.get(0));
                }
                break;
            }
        }
    }




    private void speakTheText() {
<<<<<<< HEAD
        String[] textToSpeak = getResources().getStringArray(R.array.questions);
        for (int counter = 0; counter < 5; counter++) {

=======
        String textToSpeak = "Please enter your Name ";
        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
>>>>>>> FETCH_HEAD

            tts.speak(textToSpeak[counter], TextToSpeech.QUEUE_FLUSH, null);

            while (tts.isSpeaking() == true) ;

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            try {
                startActivityForResult(intent, RESULT_SPEECH);
                ed1.setText("");
            } catch (ActivityNotFoundException e) {
                Toast t = Toast.makeText(getApplicationContext(), "Opps major fail", Toast.LENGTH_SHORT);
                t.show();

            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


     }

