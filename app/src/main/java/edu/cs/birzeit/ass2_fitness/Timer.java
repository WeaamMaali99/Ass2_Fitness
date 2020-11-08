package edu.cs.birzeit.ass2_fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private Button btnBack;
    private TextView txtView;
    private EditText edttextTimer;
    private Button btnaddtime;

    private Button btnstart;
    private Button btnPause;
    private Button btnStop;
    private CountDownTimer countDownTimer;

    private  long  StartTime;
    private long seconds ;
    private long end;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        btnBack=findViewById(R.id.btnBack);
        txtView = findViewById(R.id.txtView);
        btnPause=findViewById(R.id.btnPause);
        btnstart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);
        btnaddtime=findViewById(R.id.btnadd);
        edttextTimer=findViewById(R.id.edtMint);
        btnPause.setVisibility(View.INVISIBLE);

    }



    public void onClickAdd(View view) {

        String input = edttextTimer.getText().toString();
        long millinput = Long.parseLong(input)*60000;
        addTime(millinput);
        edttextTimer.setText("");
    }

    public void addTime(long mint){
        StartTime=mint;
        seconds = StartTime;
        runCountDownText();


    }


    public void onClickStart(View view) {
        if (running){
            pauseTimer();
        }else
        {
            startTimer();
        }
        btnPause.setVisibility(View.VISIBLE);
        btnstart.setVisibility(View.INVISIBLE);

    }



  /*  private void runTimer(){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = seconds % 3600 /60;
                int snds = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, snds);
                txtView.setText(time);
                if(running){
                    ++seconds;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

*/

    public void btnBack(View view) {
        Intent intent =new Intent(this,open.class);
        startActivity(intent);
    }

    public void onClickPause(View view) {
        running=false;
        pauseTimer();
        btnPause.setVisibility(View.INVISIBLE);
        btnstart.setVisibility(View.VISIBLE);

    }


    public void onClickStop(View view) {
        running=false;

    }
    public void startTimer(){
        end =System.currentTimeMillis() + seconds;
        countDownTimer = new CountDownTimer(seconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds=millisUntilFinished;
                runCountDownText();
            }

            @Override
            public void onFinish() {
                running=false;

            }

        }.start();
        running=true;

    }

    public void pauseTimer(){
        countDownTimer.cancel();
        running=false;

        //    btnstart.setVisibility(View.VISIBLE);

         //   btnPause.setVisibility(View.INVISIBLE);

    }
    private void runCountDownText(){
        int hours= (int)(seconds/1000)/3600;
        int minutes=(int )((seconds /1000)%3600)/60;
        int sec = (int)(seconds /1000)%60;
        String time;
        time = String.format(Locale.getDefault(),
                    "%d:%02d:%02d",hours,  minutes, sec);

        txtView.setText(time);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(" StartTime", StartTime);
        editor.putLong("seconds", seconds);
        editor.putBoolean("running", running);
        editor.putLong("end", end);


        editor.apply();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        StartTime = prefs.getLong("StartTime", 600000);
        seconds = prefs.getLong("seconds",seconds);
        running= prefs.getBoolean("running", false);
        runCountDownText();

        if (running) {
            end = prefs.getLong("end", 0);
            seconds = end- System.currentTimeMillis();
            if (seconds < 0) {
                seconds = 0;
                running = false;
                runCountDownText();

            } else {
                startTimer();
            }
        }
    }

}