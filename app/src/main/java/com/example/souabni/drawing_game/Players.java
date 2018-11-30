package com.example.souabni.drawing_game;

import android.app.Application;

/**
 * Created by Souabni on 27/11/2018.
 */

public class Players extends Application {

    private int nb_players_left;

    public int getNb_players_left() {
        return nb_players_left;
    }

    public void setNb_players_left(int nb_players_left) {
        this.nb_players_left = nb_players_left;
    }
}
