package com.example.dante.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private boolean DEBUG = true;
    private int guesses_left = 6;
    private char[] guessed_letters = new char[26];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void guess (View v) {
        if (DEBUG) {
            Log.d("guess checker", "check");
        }

        guesses_left--;
        guessed_letters[0] = 'a';
        update_game(v);
    }

    public void new_game (View v) {
        if (DEBUG) {
            Log.d("new checker", "check");
        }

        guesses_left = 6;
        guessed_letters = null;
        update_game(v);
    }

    private void update_game(View v) {
        if (DEBUG) {
            Log.d("update_game", "start");
        }

        ImageView image = findViewById(R.id.process_image);

        if (DEBUG) {
            Log.d("update_game", "got image");
        }

        int resID = getResources().getIdentifier( "hangman" + guesses_left, "drawable", getPackageName());
        if (DEBUG) {
            Log.d("update_game", "got ID");
        }


        image.setImageResource(resID);
        if (DEBUG) {
            Log.d("update_game", "changed image");
        }

        TextView t = findViewById(R.id.guess_text);
        if (DEBUG) {
            Log.d("update_game", "got textView");
        }

        String input = "You have guessed : aest (" + guesses_left + " guesses left";
        t.setText(input);
        if (DEBUG) {
            Log.d("update_game", "changed text");
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) {
            Log.d("save checker", "check");
        }
    }
}
