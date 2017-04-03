package hu.ait.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.markContent)
    LinearLayout markContent;

    @BindView(R.id.btstart)
    Button btStart;

    @BindView(R.id.btstop)
    Button btStop;

    @BindView(R.id.tvtime)
    TextView tvTime;

    int seconds = 0;
    int prevSec = 0;
    int prevMin = 0;

    private Timer mainTimer = null;

    private class MyShowTimerTask extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    seconds++;
                    int currentSecond = seconds%60;
                    int currentMinute = (seconds - currentSecond)/60;
                    tvTime.setText(getResources().getString(R.string.display_time, currentMinute, currentSecond));
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btstart)
    public void startTimer(){

        seconds = 0;

//        Thread t = new Thread(){
//
//            @Override
//            public void run() {
//
//                while (!isInterrupted()){
//
//                    try {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                seconds++;
//
//                                //the timer runs
//                            }
//                        });
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        t.start();

        if (mainTimer == null) {
            mainTimer = new Timer();

            mainTimer.schedule(new MyShowTimerTask(), 0, 1000);
        }

    }

    @OnClick(R.id.btmark)
    public void pressed(Button btn){
        View viewTodo = getLayoutInflater().inflate(R.layout.activity_mark, null, false);

        //Display the time

        TextView tvMark = ((TextView) viewTodo.findViewById(R.id.tvMark));

        int difference = seconds - prevSec;

        int txtsecond = difference%60;
        int txtminute = (difference - txtsecond)/60;

        String text = getResources().getString(R.string.display_time, txtminute, txtsecond);
        tvMark.setText(text);

        prevSec = seconds;

        markContent.addView(viewTodo,0); //will always add to the first
    }


    @OnClick(R.id.btstop)
    public void stop(){

        seconds = 0;

        if (mainTimer != null) {
            mainTimer.cancel();
            mainTimer = null;
        }


        tvTime.setText(getResources().getString(R.string.display_time, 0, 0));
    }

}
