package com.teamlrp.shinedotcom;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity3 extends Activity implements TextToSpeech.OnInitListener {

    protected static final int RESULT_SPEECH = 1;


    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);

        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "This Language is not supported", Toast.LENGTH_LONG).show();

                }

                else {
                    Toast.makeText(this, "Ready to Speak", Toast.LENGTH_LONG).show();

                }

            }


            else {
                Toast.makeText(this, "Can Not Speak", Toast.LENGTH_LONG).show();
            }

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
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void speakTheText() {


        String textToSpeak = "This is your online Statement of purpose, you have the floor for 90seconds!";



        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);


        while(tts.isSpeaking()==true);


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(intent, RESULT_SPEECH);

        } catch (ActivityNotFoundException e) {
            Toast t = Toast.makeText(getApplicationContext(), "Oops major fail", Toast.LENGTH_SHORT);
            t.show();

        }

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
