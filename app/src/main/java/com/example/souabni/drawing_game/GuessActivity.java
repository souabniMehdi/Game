package com.example.souabni.drawing_game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class GuessActivity extends AppCompatActivity {

    private Bitmap b;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0,nb_players = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        nb_players = ((Players) this.getApplication()).getNb_players_left();
//        //On récupère la bitmap que l'on a crée précedemment
//        if (getIntent().hasExtra("byteArray")) {
//            ImageView preview = new ImageView(this);
//            byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
//            b = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
//            preview.setImageBitmap(b);
//        }

        //------------------------------------------------------------------------------
        //On récupère la bitmap que l'on a crée précedemment
        if (getIntent().hasExtra("encode_byte_array")) {
            ImageView preview = new ImageView(this);
            try{
                byte[] byteArray = android.util.Base64.decode(getIntent().getStringExtra("encode_byte_array"), Base64.DEFAULT);
                b = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                preview.setImageBitmap(b);
            }catch(IllegalArgumentException e){}
            //byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
        }
        //---------------------------------------------------------------------------------

        //On vient changer la valeur de l'imageView avec celle de l'image contenue dans la bitmap
        ImageView mydecrptimg = (ImageView)findViewById(R.id.imageView);
        mydecrptimg.setImageBitmap(b);

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(5000,500) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(5000/500));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
                new_activity();
            }
        };
        mCountDownTimer.start();
    }

    private void new_activity(){

        nb_players = nb_players - 1;
        ((Players) this.getApplication()).setNb_players_left(nb_players);

        if (nb_players != 0){
            //We will test here if all players already played. I f yes, we will redirect them to the final summary activity.
            //Else we will redirect them to the guess activity
            Intent i = new Intent(this, DrawingActivity.class);
            EditText editTextDessin = (EditText) findViewById(R.id.EditTextDevinerDessin);
            String str = editTextDessin.getText().toString().trim();
            i.putExtra("Valeur devinée",str);

            //We save the guess into the advancement_of_game ArrayList
            ArrayList<String> arrayList = ((Players) this.getApplication()).getAdvancement_of_game();
            arrayList.add(str);
            ((Players)this.getApplication()).setAdvancement_of_game(arrayList);

            startActivity(i);
        }else {
            Intent i = new Intent(this, DisplayRandomImageActivity.class);
            EditText editTextDessin = (EditText) findViewById(R.id.EditTextDevinerDessin);
            String str = editTextDessin.getText().toString().trim();

            //We save the guess into the advancement_of_game ArrayList
            ArrayList<String> arrayList = ((Players) this.getApplication()).getAdvancement_of_game();
            arrayList.add(str);
            ((Players)this.getApplication()).setAdvancement_of_game(arrayList);

            startActivity(i);
        }
    }
}
