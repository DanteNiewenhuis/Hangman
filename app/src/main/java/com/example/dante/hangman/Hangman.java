package com.example.dante.hangman;
import java.io.Serializable;

public class Hangman implements Serializable{
    private int guesses_left;
    private char[] guessed_letters;
    private int ArrayIndex;
    private String secret_word;
    private int letters_to_guess;

    public Hangman(Dictionary dict) {
        // start a game
        new_game(dict);
    }

    public void new_game (Dictionary dict) {
        // init variables
        guesses_left = 6;
        guessed_letters = new char[26];
        ArrayIndex = 0;
        secret_word = dict.new_word();
        System.out.println("SECRET WORD == " + secret_word);

    }

    public String guess (char guess_char) {
        if (inArray(guessed_letters, guess_char)) {
            return "already_guessed";
        }

        // add the char to the list of guessed letters
        guessed_letters[ArrayIndex] = guess_char;
        ArrayIndex++;

        // increment guesses left if the guess is not present in the secret word
        if (secret_word.indexOf(guess_char) == -1) {
            guesses_left--;
        }

        return "";
    }

    public String get_secret_word() {
        String result = "";
        char c;
        letters_to_guess = 0;
        for (int i = 0; i < secret_word.length(); i++) {
            c = secret_word.charAt(i);
            if (inArray(guessed_letters, c)) {
                result += c;
            }
            else {
                result += '?';
                letters_to_guess++;
            }
        }

        return result;
    }

    public String isFinished() {
        if (guesses_left == 0) {
            return "You have lost, the secret word was \"" + secret_word + "\"";
        }
        if (letters_to_guess == 0) {
            return "You have won";
        }
        return "no";
    }

    public String get_guessed_letters() {
        if (guessed_letters == null) {
            return "";
        }
        String result = "";
        for (char c : guessed_letters) {
            result += c;
        }

        return result;
    }

    public int get_guesses_left() {
        return guesses_left;
    }

    private boolean inArray (char[] Array, char value) {

        // if the array is null return false
        if (Array == null) {
            return false;
        }

        // loop through array to see if value is in it, return true if so.
        for (char c : Array) {
            if (c == value) {
                return true;
            }
        }
        return false;
    }

}
