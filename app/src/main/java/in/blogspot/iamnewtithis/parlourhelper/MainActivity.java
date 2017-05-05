package in.blogspot.iamnewtithis.parlourhelper;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button StartTimer ;
    Button ResetTimer ;

    TextView Timer;
    public static int min = 0;
    public static int sec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartTimer = (Button)findViewById(R.id.StartTimer);
        ResetTimer = (Button)findViewById(R.id.ResetTimer);

        Timer = (TextView)findViewById(R.id.Timer);
        final CountDownTimer[] start = new CountDownTimer[1];

        StartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer.setEnabled(false);
                start[0] = new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
//                        Need to Update Label
                        sec ++;
                        if (sec > 59){
                            sec = 0;
                            min ++;
                        }
                        String str = Integer.toString(min) + ":" + Integer.toString(sec) ;
                        Timer.setText(str);
                    }

                    @Override
                    public void onFinish() {
//                        Need to beep here
                        sec = 0;
                        min = 0;
                        Timer.setText("00:00");
                    }
                }.start();
            }
        });

        ResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start[0].cancel();
                StartTimer.setEnabled(true);
                sec = 0;
                min = 0;
                Timer.setText("00:00");
            }
        });

    }
}
