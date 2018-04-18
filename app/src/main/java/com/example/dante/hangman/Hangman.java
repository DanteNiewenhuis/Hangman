package com.example.dante.hangman;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Hangman implements Serializable{
    private int guesses_left;
    private char[] guessed_letters;
    private int ArrayIndex;
    private String secret_word;

    public Hangman(Activity Main, Dictionary dict) {
        System.out.println("hangman init");
        new_game(Main, dict);
    }

    public void new_game (Activity activity, Dictionary dict) {
        System.out.println("new game");
        guesses_left = 6;
        guessed_letters = new char[26];
        ArrayIndex = 0;
        secret_word = dict.new_word();
        System.out.println("SECRET WORD == " + secret_word);

        TextView info = activity.findViewById(R.id.info_text);
        info.setText(R.string.info_text);
        update_game(activity);
    }

    public void guess (Activity activity) {
        //TODO make multiple letters possible to input
        EditText input = activity.findViewById(R.id.guess_input);
        String guess = input.getText().toString();
        if (guess.matches("")) {
            return;
        }

        char guess_char = guess.charAt(0);
        TextView info = activity.findViewById(R.id.info_text);
        if (inArray(guessed_letters, guess_char)) {
            info.setText(R.string.already_guessed);
            return;
        }

        guessed_letters[ArrayIndex] = guess_char;
        ArrayIndex++;
        if (secret_word.indexOf(guess_char) == -1) {
            guesses_left--;
        }
        update_game(activity);
    }



    public void update_game(Activity activity) {
        ImageView image = activity.findViewById(R.id.process_image);
        int resID = activity.getResources().getIdentifier( "hangman" + guesses_left,
                                            "drawable", activity.getPackageName());

        image.setImageResource(resID);
        TextView t = activity.findViewById(R.id.guess_text);
        String input = "You have guessed : ";

        if (guessed_letters != null) {
            for (char guess : guessed_letters) input += guess;

        }
        input += " (" + guesses_left + " guesses left)";
        t.setText(input);
        t = activity.findViewById(R.id.secret_word);
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
            TextView info = activity.findViewById(R.id.info_text);
            info.setText(info_string);

            //TODO if finished disable the input and the guess button
        }

        EditText guess_input = activity.findViewById(R.id.guess_input);
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

}
