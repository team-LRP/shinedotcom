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

    TextView txt;
    Button btn;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this,this);

        txt= (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakTheText();
                while(tts.isSpeaking()==true)
                {

                }
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                    try {
                        startActivityForResult(intent, RESULT_SPEECH);
                        txt.setText("");
                    } catch (ActivityNotFoundException e) {
                        Toast t = Toast.makeText(getApplicationContext(), "Oops major fail", Toast.LENGTH_SHORT);
                        t.show();
                    }



        Button buttonSpeak;

        TextView txt, txt1 , txt2, txt3 ;
        EditText ed1 , ed2 , ed3 ;
        TextToSpeech tts ;






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
        switch (requestCode){
            case RESULT_SPEECH: {
                if(resultCode == RESULT_OK ){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt.setText(text.get(0));
                }
                break;
            }
        }


    public void onInit(int status)
    {

        if (status == TextToSpeech.SUCCESS)
        {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Ready to Speak", Toast.LENGTH_LONG).show();
                speakTheText();
            }

        }
        else
        {
            Toast.makeText(this, "Can Not Speak", Toast.LENGTH_LONG).show();
        }


    private void speakTheText() {
        String textToSpeak = "Please enter your Name ";
        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
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

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS)
        {

            int result = tts.setLanguage(Locale.GERMAN);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Ready to Speak", Toast.LENGTH_LONG).show();
                speakTheText();
            }

        }
        else
        {
            Toast.makeText(this, "Can Not Speak", Toast.LENGTH_LONG).show();
        }
    }

    private void speakTheText()
    {
        String textToSpeak = "Say name";
        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy()
    {
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
