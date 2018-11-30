package com.example.souabni.drawing_game;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Welcome_activity extends AppCompatActivity {

    Button btnStartGame,btnAddPlayer;
    EditText editTxtPlayerName;
    ListView listViewPlayers;
    boolean first_player_added = false;
    int nb_player = 0;
    ArrayList<String> values;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);

        editTxtPlayerName = (EditText) findViewById(R.id.EditTextAddPlayer);
        btnAddPlayer = (Button) findViewById(R.id.buttonAddPlayer);
        btnAddPlayer.setEnabled(false);

        //Nous permet de désactiver le bouton d'ajout de joueurs quand le champ est vide et inversement
        editTxtPlayerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = editTxtPlayerName.getText().toString().trim();
                btnAddPlayer.setEnabled(!text.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Display hint within the listview to make the player understand what he has to do
        String[] the_values = new String[] { "Player1", "Player2", "Player3"};
        values = new ArrayList<String>();
        for (int i = 0; i < the_values.length; ++i) {
            values.add(the_values [i]);
        }

        //final ArrayList<String> values = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listViewPlayers = (ListView) findViewById(R.id.ListViewPlayers);
        listViewPlayers.setAdapter(adapter);
        listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showUpdateBox(values.get(i),i);
            }
        });

//        final ArrayList<String> listPlayerHint = new ArrayList<String>();
//        for (int i = 0; i < the_values.length; ++i) {
//            listPlayerHint.add(the_values [i]);
//        }
//
//        final ArrayList<String> values = new ArrayList<String>();
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//        listViewPlayers = (ListView) findViewById(R.id.ListViewPlayers);
//        listViewPlayers.setAdapter(adapter);


        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!first_player_added) {
                    values.clear();
                    values.add(editTxtPlayerName.getText().toString().trim());
                    adapter.notifyDataSetChanged();
                    editTxtPlayerName.setText("");
                    first_player_added = true;
                }else {
                    values.add(editTxtPlayerName.getText().toString().trim());
                    adapter.notifyDataSetChanged();
                    editTxtPlayerName.setText("");
                }
            }
        });

        btnStartGame = (Button) findViewById(R.id.buttonStart);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (values.size() >= 3){
                    //We catch the number of players to know how much loop we will have to do.
                    check_nb_player(values);
                    Toast.makeText(view.getContext(),"(WelcomeActivity) Nb_players within the Player class : " + Integer.toString(values.size()), Toast.LENGTH_SHORT).show();

                    //If the number of players is pair
                    if (values.size() % 2 == 0){
                        Intent i = new Intent(view.getContext(), DrawingActivity.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(view.getContext(), DisplayRandomImageActivity.class);
                        startActivity(i);
                    }
                }else {
                    Toast.makeText(view.getContext(),"(WelcomeActivity) Require at least 3 players to play", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void check_nb_player(ArrayList<String> players){
        ((Players) this.getApplication()).setNb_players_left(players.size());
    }

    public void showUpdateBox(String oldItem, final int index){
        final Dialog dialog = new Dialog(Welcome_activity.this);
        dialog.setTitle("Dialog box ");
        dialog.setContentView(R.layout.input_box);
        final EditText editTextDialog = (EditText)dialog.findViewById(R.id.EditText_InputBox);
        editTextDialog.setHint(oldItem);
        final Button buttonDialog = (Button) dialog.findViewById(R.id.Button_InputBox);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values.set(index,editTextDialog.getText().toString());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
        Toast.makeText(this,"text",Toast.LENGTH_SHORT).show();
        //Test to see if it's working or not
        //Why does this manipulation not working ???? 
    }
}
