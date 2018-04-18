package com.example.dante.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private boolean DEBUG = true;
    private int guesses_left;
    private char[] guessed_letters;
    private int ArrayIndex;
    private String secret_word;
    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState != null) {
            guesses_left = savedInstanceState.getInt("guesses_left");
            guessed_letters = savedInstanceState.getCharArray("guessed_letters");
            ArrayIndex = savedInstanceState.getInt("ArrayIndex");
            secret_word = savedInstanceState.getString("secret_word");
            update_game();
        }
        else {
            try {
                dict = new Dictionary(getApplicationContext());
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            secret_word = dict.new_word();
            Log.d("SECRET WORD", secret_word);
            guesses_left = 6;
            guessed_letters = new char[26];
            ArrayIndex = 0;
        }

        TextView info = findViewById(R.id.info_text);
        info.setText(R.string.info_text);
        update_game();
    }

    public void guess (View v) {
        //TODO make multiple letters possible to input
        EditText input = findViewById(R.id.guess_input);
        String guess = input.getText().toString();
        if (guess.matches("")) {
            return;
        }

        char guess_char = guess.charAt(0);
        TextView info = findViewById(R.id.info_text);
        if (inArray(guessed_letters, guess_char)) {
            info.setText(R.string.already_guessed);
            return;
        }

        guessed_letters[ArrayIndex] = guess_char;
        ArrayIndex++;
        if (secret_word.indexOf(guess_char) == -1) {
            guesses_left--;
        }
        update_game();
    }

    public void new_game (View v) {
        guesses_left = 6;
        guessed_letters = new char[26];
        ArrayIndex = 0;
        secret_word = dict.new_word();
        TextView info = findViewById(R.id.info_text);
        info.setText(R.string.info_text);
        update_game();
    }

    private void update_game() {
        ImageView image = findViewById(R.id.process_image);
        int resID = getResources().getIdentifier( "hangman" + guesses_left, "drawable", getPackageName());

        image.setImageResource(resID);
        TextView t = findViewById(R.id.guess_text);
        String input = "You have guessed : ";

        if (guessed_letters != null) {
            for (char guess : guessed_letters) input += guess;

        }
        input += " (" + guesses_left + " guesses left)";
        t.setText(input);
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

            String info_string;
            if (guesses_left == 0) {
                info_string = "You have lost.";
            }
            else {
                info_string = "You have won!!";
            }

            info_string += " Click New to start another game.";
            TextView info = findViewById(R.id.info_text);
            info.setText(info_string);

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

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("guesses_left", guesses_left);
        outState.putInt("ArrayIndex", ArrayIndex);
        outState.putCharArray("guessed_letters", guessed_letters);
        outState.putString("secret_word", secret_word);
    }
}
