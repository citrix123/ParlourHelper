package in.blogspot.iamnewtithis.parlourhelper;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button StartTimer ;
    Button ResetTimer ;

    TextView Timer;

    Spinner facial_spinner;


    public static int min = 0;
    public static int sec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        StartTimer = (Button)findViewById(R.id.StartTimer);
        ResetTimer = (Button)findViewById(R.id.ResetTimer);

        Timer = (TextView)findViewById(R.id.Timer);

        facial_spinner = (Spinner)findViewById(R.id.facial_spinner);

//      Fill Spinner with Values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.facial_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facial_spinner.setAdapter(adapter);
        facial_spinner.setOnItemSelectedListener(this);

        final CountDownTimer[] start = new CountDownTimer[1];

//      Timer event start.
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
                        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 150);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView)view;
        Toast.makeText(this, "You Selected "+myText.getText() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

