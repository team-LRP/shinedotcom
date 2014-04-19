package com.teamlrp.shinedotcom;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

        EditText txt;
        Button buttonSpeak;

        TextToSpeech tts ;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            tts = new TextToSpeech(this, this);

            txt =(EditText)findViewById(R.id.textView1);
            buttonSpeak =(Button)findViewById(R.id.button);


            buttonSpeak.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    speakTheText();

                }
            });
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

    }

    private void speakTheText()
    {
        String textToSpeak = String textToSpeak = editText.getText().toString();
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

}
