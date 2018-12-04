package com.example.souabni.drawing_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayRandomImageActivity extends AppCompatActivity {

    Button btnFInalDisplay;
    private ImageView imageView;
    private Bitmap b;
    private ArrayList<String> finalDrawings, the_players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_image);

        finalDrawings = ((Players) this.getApplication()).getAdvancement_of_game();
        the_players = ((Players) this.getApplication()).getName_players();

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setVisibility(View.INVISIBLE);

        btnFInalDisplay = (Button) findViewById(R.id.ButtonFinalDisplay);
        btnFInalDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_step();
            }
        });
    }

    private void next_step() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        //S'il reste des actions effectuées par les joueurs qui n'ont pas encore été affichées
        int size = finalDrawings.size();
        if (size !=0 ){
            //Action différente en fonction de si on doit afficher l'image ou bien affichée la guess  du joueur
            if (size % 2 == 0){
                Toast.makeText(this, "Guessed it was a " + finalDrawings.get(size-1), Toast.LENGTH_SHORT).show();
            }else {
                ImageView preview = new ImageView(this);
                try {
                    byte[] byteArray = android.util.Base64.decode(finalDrawings.get(size-1), Base64.DEFAULT);
                    b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    preview.setImageBitmap(b);
                    //On vient changer la valeur de l'imageView avec celle de l'image contenue dans la bitmap
                    imageView.setImageBitmap(b);
                    imageView.setVisibility(View.VISIBLE);
                } catch (IllegalArgumentException e) {
                }
            }
            //On enlève le dernier élément de la liste
            finalDrawings.remove(size-1);
        }else {
            btnFInalDisplay.setEnabled(false);
        }

    }
}
