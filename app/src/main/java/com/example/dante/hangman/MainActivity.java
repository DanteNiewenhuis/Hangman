package com.example.dante.hangman;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //TODO add a landscape mode
    private Hangman game;
    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState != null) {
            game = (Hangman) savedInstanceState.getSerializable("game");
            dict = (Dictionary) savedInstanceState.getSerializable("dict");
        }
        else {
            try {
                dict = new Dictionary(getApplicationContext());
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            game = new Hangman(dict);
        }

        update_game();
    }

    public void guess (View v) {
        EditText guess_input = findViewById(R.id.guess_input);
        String guess = guess_input.getText().toString();
        if (guess.matches("")) {
            return;
        }
        char guess_char = guess.charAt(0);
        String move = game.guess(guess_char);

        TextView info = findViewById(R.id.info_text);
        if (move == "already_guessed") {
            info.setText(R.string.already_guessed);
            guess_input.setText("");
        }

        update_game();
    }

    public void new_game (View v) {
        game.new_game(dict);
        // show info text

        TextView info = findViewById(R.id.info_text);
        info.setText(R.string.info_text);

        // activate guess button
        Button guess_button = findViewById(R.id.guess_button);
        guess_button.setActivated(true);

        // activate input box
        EditText guess_input = findViewById(R.id.guess_input);
        guess_input.setEnabled(true);


        // update game
        update_game();
    }

    private void update_game() {
        int guesses_left = game.get_guesses_left();
        ImageView image = findViewById(R.id.process_image);
        int resID = getResources().getIdentifier( "hangman" + guesses_left,
                "drawable", getPackageName());
        image.setImageResource(resID);

        TextView t = findViewById(R.id.guess_text);
        String input = "You have guessed : ";
        input += game.get_guessed_letters();
        input += " (" + guesses_left + " guesses left)";
        t.setText(input);

        t = findViewById(R.id.secret_word);
        t.setText(game.get_secret_word());

        // stop the game if no guesses are left or all letters have been guessed
        String finish = game.isFinished();
        if (finish != "no") {

            // show the user if they have won or lost the game.
            String info_string = finish + " Click New to start another game.";

            TextView info = findViewById(R.id.info_text);
            info.setText(info_string);

            // deactivate guess button
            Button guess_button = findViewById(R.id.guess_button);
            guess_button.setActivated(false);

            // deactivate input box
            EditText guess_input = findViewById(R.id.guess_input);
            guess_input.setEnabled(false);
        }

        // empty the input box.
        EditText guess_input = findViewById(R.id.guess_input);
        guess_input.setText("");
        guess_input.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("game", game);
        outState.putSerializable("dict", dict);
    }
}
