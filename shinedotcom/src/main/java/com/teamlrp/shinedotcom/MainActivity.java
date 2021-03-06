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

    String stp = "stop";



    TextView txt1, txt2, txt3;
    EditText ed1, ed2, ed3,ed4,ed5,ed6, ed7, ed8, ed9, ed10, ed11;

    TextToSpeech tts;

    int counter=0;
    EditText arr[] = { ed1, ed2, ed3,ed4,ed5,ed6, ed8, ed9, ed10, ed11} ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (TextView) findViewById(R.id.name);
        txt2 = (TextView) findViewById(R.id.country);
        txt2 = (TextView) findViewById(R.id.city);
        txt3 = (TextView) findViewById(R.id.gender);
        ed1 = (EditText) findViewById(R.id.editname);
        ed2 = (EditText)findViewById(R.id.editcountry);
        ed3 = (EditText) findViewById(R.id.editcity);
        ed4 = (EditText) findViewById(R.id.editgender);
        ed5 = (EditText) findViewById(R.id.radioButton);
        ed6 = (EditText) findViewById(R.id.edityears);
        ed8= (EditText) findViewById(R.id.editjobtitle);
        ed9 = (EditText) findViewById(R.id.editcompany);
        ed7 = (EditText) findViewById(R.id.editText);
        ed10 = (EditText) findViewById(R.id.editcollege);
        ed11 = (EditText) findViewById(R.id.editchoice);

        tts = new TextToSpeech(this, this);

        buttonSpeak = (Button)findViewById(R.id.button);



        buttonSpeak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                speakTheText(counter);
             }


          
    });
    }






/*

    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }
    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());


        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording(){
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
    }

*/

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


        EditText arr[] = {ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10, ed11};
        ArrayList<String> text = null;
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK) {
                    text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    arr[counter].setText(text.get(0));


                    break;
                }
            }
        }
        boolean b;
        try {
            b = (text.get(0).contains("no") == true||text.get(0).contains("new")==true);
        } catch (Exception e ) {
            b = false;
        }
        if(counter==3 &&text.get(0).contains("mail")==true){
            arr[counter].setText("male");

        }
        if (counter == 4 && b) {

            counter = counter + 3;

            }


            if (arr[counter].getText().toString().contains(stp) == true)

            {
                Toast.makeText(this, "Voice Assistant Paused", Toast.LENGTH_SHORT).show();
                //stopRecording();
            } else {

                counter++;

                if (counter < 11)
                    speakTheText(counter);
                else if (counter == 11) {
                    Toast.makeText(this, "Application process stopped", Toast.LENGTH_LONG).show();

                    //stopRecording();
                }

            }

        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void speakTheText(int count) {


        String[] textToSpeak = getResources().getStringArray(R.array.questions);



        tts.speak(textToSpeak[count], TextToSpeech.QUEUE_FLUSH, null);


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





