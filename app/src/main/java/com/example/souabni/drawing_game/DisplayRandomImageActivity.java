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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_image);

        ArrayList<String> finalDrawings = ((Players) this.getApplication()).getAdvancement_of_game();

        Toast.makeText(this, "Nb_item within the list" + Integer.toString(finalDrawings.size()),Toast.LENGTH_SHORT).show();

        ImageView imageView = (ImageView)findViewById(R.id.imageView2);

        for(int l=0; l<= finalDrawings.size()-1; l++){
            if (l == 0){
                ImageView preview = new ImageView(this);
                try{
                    byte[] byteArray = android.util.Base64.decode(finalDrawings.get(l), Base64.DEFAULT);
                    b = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                    preview.setImageBitmap(b);
                    //On vient changer la valeur de l'imageView avec celle de l'image contenue dans la bitmap
                    imageView.setImageBitmap(b);
                }catch(IllegalArgumentException e){}
            }else {
            }

        }
    }
}
