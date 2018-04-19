package com.example.dante.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Hangman game;
    private Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState != null) {
            game = (Hangman) savedInstanceState.getSerializable("game");
            dict = (Dictionary) savedInstanceState.getSerializable("dict");
            game.update_game(this);
        }
        else {
            System.out.println("making classes");
            try {
                dict = new Dictionary(getApplicationContext());
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            game = new Hangman(this, dict);
        }
    }

    public void guess (View v) {
        game.guess(this);
    }

    public void new_game (View v) {
        game.new_game(this, dict);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("game", game);
        outState.putSerializable("dict", dict);
    }
}
