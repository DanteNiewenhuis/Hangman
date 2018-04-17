package com.example.dante.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private boolean DEBUG = true;
    private int guesses_left = 6;
    private char[] guessed_letters = new char[26];
    private int ArrayIndex = 0;
    private String secret_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            guesses_left = savedInstanceState.getInt("guesses_left");
            guessed_letters = savedInstanceState.getCharArray("guessed_letters");
            ArrayIndex = savedInstanceState.getInt("ArrayIndex");
            secret_word = savedInstanceState.getString("secret_word");
        }
        else {
            secret_word = get_secret_word();
        }

        update_game();
    }

    public void guess (View v) {
        if (DEBUG) {
            Log.d("guess checker", "in function");
        }

        EditText input = findViewById(R.id.guess_input);
        if (DEBUG) {
            Log.d("guess checker", "found id");
        }
        char guess = input.getText().toString().charAt(0);
        if (DEBUG) {
            Log.d("guess checker", "got input char");
        }

        //TODO check for an empty input!!!
        if (inArray(guessed_letters, guess)) {
            return;
        }

        guessed_letters[ArrayIndex] = guess;
        ArrayIndex++;
        if (DEBUG) {
            Log.d("guess checker", "added char to array");
        }

        if (secret_word.indexOf(guess) == -1) {
            guesses_left--;
        }

        if (DEBUG) {
            Log.d("guess checker", "checked if char in string");
        }
        update_game();
    }

    public void new_game (View v) {
        if (DEBUG) {
            Log.d("new checker", "check");
        }

        guesses_left = 6;
        guessed_letters = null;
        secret_word = get_secret_word();
        update_game();
    }

    private void update_game() {
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

        String input = "You have guessed : ";

        if (guessed_letters != null) {
            for (char guess : guessed_letters) input += guess;

        }
        input += " (" + guesses_left + " guesses left)";
        t.setText(input);

        if (DEBUG) {
            Log.d("update_game", "changed text");
        }

        t = findViewById(R.id.secret_word);
        input = "";
        int letters_to_guess = 0;
        for (int i = 0; i < secret_word.length(); i++){
            char c = secret_word.charAt(i);
            if (inArray(guessed_letters, c)) {
                input += c;
            }
            else {
                input += '?';
                letters_to_guess += 1;
            }
        }

        t.setText(input);

        if (guesses_left == 0 || letters_to_guess == 0) {
            if (DEBUG) {
                Log.d("update_game", "game is done");
            }
            //TODO check if the game is finished
            //TODO if finished disable the input and the guess button
        }

        EditText guess_input = findViewById(R.id.guess_input);
        guess_input.setText("");
    }

    private boolean inArray (char[] Array, char value) {
        if (Array == null) {
            return false;
        }
        for (char c : Array) {
            if (c == value) {
                return true;
            }
        }
        return false;
    }

    private String get_secret_word() {
        return "secret_word";
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) {
            Log.d("save checker", "check");
        }

        outState.putInt("guesses_left", guesses_left);
        outState.putInt("ArrayIndex", ArrayIndex);
        outState.putCharArray("guessed_letters", guessed_letters);
        outState.putString("secret_word", secret_word);
    }
}
