package com.example.souabni.drawing_game;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Souabni on 27/11/2018.
 */

public class Players extends Application {

    private int nb_players_left;
    private ArrayList<String> advancement_of_game = new ArrayList<String>();
    private ArrayList<String> name_players = new ArrayList<String>();

    public int getNb_players_left() {
        return nb_players_left;
    }

    public void setNb_players_left(int nb_players_left) {
        this.nb_players_left = nb_players_left;
    }

    public ArrayList<String> getAdvancement_of_game() {
        return advancement_of_game;
    }

    public void setAdvancement_of_game(ArrayList<String> advancement_of_game) {
        this.advancement_of_game = advancement_of_game;
    }

    public void Add_string(String str){
        this.getAdvancement_of_game().add(str);
    }

    public ArrayList<String> getName_players() {
        return name_players;
    }

    public void setName_players(ArrayList<String> name_players) {
        this.name_players = name_players;
    }
}
