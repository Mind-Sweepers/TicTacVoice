package com.mindsweepers.tictacvoice;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private int tries;
    private Button playback_btn;
    private Button input_btn;
    private TextView textMessage;

    private int result;
    private String userInput;

    private TextToSpeech toSpeech;
    private TextToSpeech tts;

    private TicTacToe ticTacToe;

    private ImageView[][] im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tries = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = new ImageView[][] {{findViewById(R.id.imageViewA1), findViewById(R.id.imageViewA2), findViewById(R.id.imageViewA3)},
                {findViewById(R.id.imageViewB1), findViewById(R.id.imageViewB2), findViewById(R.id.imageViewB3)},
                {findViewById(R.id.imageViewC1), findViewById(R.id.imageViewC2), findViewById(R.id.imageViewC3)}
        };

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        } );

        textMessage = (TextView) findViewById(R.id.textMessage);
        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status){
                if (status == TextToSpeech.SUCCESS){
                    result = toSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void speakInstructions(View view){
        tts.speak("Welcome to Tic Tac Voice. Game is starting. Player X please say a coordinate or a 3 by 3 grid. Letter then Number", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void playbackOutput(View view){
        Log.i("playbackOutput", "PlayBack OutPut");
        switch (view.getId()){
            case R.id.playback_btn:
                Log.i("userInput","In text message case");

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                } else {
                    userInput = textMessage.getText().toString();
                    Log.i("userInput",userInput);
                    toSpeech.speak(userInput,TextToSpeech.QUEUE_FLUSH,null);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech != null){
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }

    public void getSpeechInput(View view){

        //TicTacToe.getInstance().setPiece("hi");
        //Log.e("inside button",TicTacToe.getInstance().getBoard()[0][0]);


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this,"Your device doesn't support speech input", Toast.LENGTH_SHORT).show();

        }
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textMessage.setText(result.get(0));
                    userInput = result.get(0);

                    char row = userInput.charAt(0);
                    int r;
                    int col = Character.getNumericValue(userInput.charAt(1));
                    if (row=='A') {
                        r = 0;
                    }
                    else if (row=='B') {
                        r = 1;
                    }
                    else {
                        r = 2;
                    }

                    if(userInput.length()==2 && (row =='A' || row=='B' || row=='C') && col<4) {
                        if (TicTacToe.getInstance().alreadyCalled(row,col)) {
                            Toast.makeText(getApplicationContext(), "That location has already been entered. Please pick a different location.", Toast.LENGTH_SHORT).show();
                            tts.speak("That location has already been entered. Please pick a different location.",TextToSpeech.QUEUE_FLUSH,null);
                        }else {
                            tries += 1;
                            if (tries % 2 == 0) {
                                im[r][col - 1].setImageResource(R.drawable.tic_tac_toe_x);
                            } else {
                                im[r][col - 1].setImageResource(R.drawable.tic_tac_toe_o);
                            }
                        }
                        if(TicTacToe.getInstance().userInput(userInput, tries) ==1){
                            tts.speak("Player X has won the game",TextToSpeech.QUEUE_FLUSH,null);
                            TicTacToe.getInstance().resetBoard();
                            tries = 0;
                        }else if(TicTacToe.getInstance().userInput(userInput, tries) ==2){
                            tts.speak("Player O has won the game",TextToSpeech.QUEUE_FLUSH,null);
                            TicTacToe.getInstance().resetBoard();
                            tries = 0;
                        }

                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Please make sure you're input is valid and is in the form of letter then number", Toast.LENGTH_SHORT).show();
                        tts.speak("Please make sure your input is valid and is in the form of letter then number",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }

                if(tries > 8){
                    tts.speak("Tied game",TextToSpeech.QUEUE_FLUSH,null);
                    TicTacToe.getInstance().resetBoard();
                    tries = 0;
                }
                Log.i("Hi","Test");
                break;
            }
    }
}
