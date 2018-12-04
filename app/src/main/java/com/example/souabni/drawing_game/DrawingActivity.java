package com.example.souabni.drawing_game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DrawingActivity extends AppCompatActivity {

    private PaintView paintView;
    private Button button,button2;

    //Pour gérer le compte à rebour
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0,nb_players = 0;
    TextView textViewDessinAFaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nb_players = ((Players) this.getApplication()).getNb_players_left();
        Toast.makeText(this,"(DrawingActivity) Nb_players : " + Integer.toString(nb_players),Toast.LENGTH_SHORT ).show();

        //HideTitleBar();
        this.setContentView(R.layout.activity_drawing);
        //HideNavigationBar();

        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (paintView.getPaths().size() >= 1 ){
                    paintView.undo();
                }
            }
        });

        textViewDessinAFaire = (TextView) findViewById(R.id.TextViewDessinAFaire);
        if (getIntent().hasExtra("Valeur devinée")) {
            Toast.makeText(this,"(DrawingActivity) " + getIntent().getStringExtra("Valeur devinée"),Toast.LENGTH_SHORT).show();
            textViewDessinAFaire.setText("Dessinez un(e) : " + getIntent().getStringExtra("Valeur devinée"));
        }else {
            textViewDessinAFaire.setText("Dessinez ce que vous voulez");
            Toast.makeText(this,"(DrawingActivity) Pas d'intent pour le moment",Toast.LENGTH_SHORT).show();
        }

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar2);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(5000,250) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(5000/250));
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
                next_activity();
            }
        };
        mCountDownTimer.start();


    }

    public void next_activity(){
        //On vient envoyer ici la valeur de la bitmap pour pouvoir l'afficher dans l'activite suivante.
        nb_players = nb_players - 1;
        ((Players) this.getApplication()).setNb_players_left(nb_players);
        if (nb_players != 0){
            //We will test here if all players already played. I f yes, we will redirect them to the final summary activity.
            //Else we will redirect them to the guess activity
            Intent i = new Intent(this, GuessActivity.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            paintView.getmBitmap().compress(Bitmap.CompressFormat.PNG, 50, bs);

            //We save the draw into the advancement_of_game ArrayList
            String encode_byte_array = android.util.Base64.encodeToString(bs.toByteArray(), Base64.DEFAULT);
            ArrayList<String> arrayList = ((Players) this.getApplication()).getAdvancement_of_game();
            arrayList.add(encode_byte_array);
            ((Players) this.getApplication()).setAdvancement_of_game(arrayList);
            i.putExtra("encode_byte_array",encode_byte_array);
            startActivity(i);
        }else {
            Intent i = new Intent(this, DisplayRandomImageActivity.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            paintView.getmBitmap().compress(Bitmap.CompressFormat.PNG, 50, bs);

            //We save the draw into the advancement_of_game ArrayList
            String encode_byte_array = android.util.Base64.encodeToString(bs.toByteArray(), Base64.DEFAULT);
            ((Players) this.getApplication()).Add_string(encode_byte_array);
            startActivity(i);
        }
        //---------------------------------------------------------------------------
        //i.putExtra("byteArray", bs.toByteArray());
    }

    //Dans le futur, implémenter cette fonction pour pouvoir cacher la barre du bas.
    @Override
    protected void onResume() {
        super.onResume();
        //HideTitleBar();
        //HideNavigationBar();
    }

    private void HideTitleBar(){
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void HideNavigationBar(){
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
