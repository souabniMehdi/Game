package com.example.souabni.drawing_game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GuessActivity extends AppCompatActivity {

    private Bitmap b;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        //On récupère la bitmap que l'on a crée précedemment
        if (getIntent().hasExtra("byteArray")) {
            ImageView preview = new ImageView(this);
            byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
            b = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            preview.setImageBitmap(b);
        }

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
        Intent i = new Intent(this, DrawingActivity.class);
        EditText editTextDessin = (EditText) findViewById(R.id.EditTextDevinerDessin);
        i.putExtra("Valeur devinée",editTextDessin.getText().toString().trim());
        startActivity(i);
    }
}
