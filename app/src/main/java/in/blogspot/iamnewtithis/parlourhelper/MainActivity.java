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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button StartTimer ;
    Button ResetTimer ;
    TextView Timer;
    Spinner facial_spinner;

    Map<String, ArrayList<Integer>> FacialTime = new HashMap<String, ArrayList<Integer>>();
    Map<String, String> FacialsMapping = new HashMap<String, String>();

    public static int min = 0;
    public static int sec = 0;
    public static int TotalTime = 0;
    public static ArrayList<Integer> valueOfFacial;

    public void registerFacialTime(){
        FacialsMapping.put("Chocolate Facial" , "Normal Facial");
        FacialsMapping.put("Fruit Facial" , "Normal Facial");
        FacialsMapping.put("Shehnaz Facial" , "Special Facial");
        FacialsMapping.put("Gold Facial" , "Special Facial");
        FacialsMapping.put("Wine Facial" , "Special Facial");

        FacialTime.put("Special Facial", new ArrayList<Integer>(Arrays.asList(new Integer[]{7,7,6,9,9,7})));
        FacialTime.put("Normal Facial", new ArrayList<Integer>(Arrays.asList(new Integer[]{7,6,9,10,8})));
    }

    public ArrayList<Integer> getValueOfFacial(String Name){
        String FacialName;
        FacialName = FacialsMapping.get(Name);
        for (int i = 0; i < FacialTime.get(FacialName).size(); i++) {
            TotalTime += FacialTime.get(FacialName).get(i);
            FacialTime.get(FacialName).set(i, TotalTime);
            System.out.println(FacialTime.get(FacialName).get(i));
        }
        return FacialTime.get(FacialName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registerFacialTime();
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
//        for (int i = 0; i < valueOfFacial.size(); i++) {
//            System.out.println(valueOfFacial.get(i));
//        }
//      Timer event start.
        StartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer.setEnabled(false);
//                Use Total Time -> Get from the Spinner.
                TotalTime = TotalTime * 60 * 1000;
                System.out.println(TotalTime);
                start[0] = new CountDownTimer(TotalTime , 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
//                        Need to Update Label
                        sec ++;
                        if (sec > 59){
                            sec = 0;
                            min ++;
                        }
                        String str = Integer.toString(min) + ":" + Integer.toString(sec) ;
                        if (valueOfFacial.contains(min)){
                            System.out.println("I am going to beep");
                            toneGenerator.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 150);
                            valueOfFacial.set(valueOfFacial.indexOf(min), 0);
                        }
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
        TotalTime = 0;
        valueOfFacial = getValueOfFacial((String) myText.getText());
        for (int i = 0; i < valueOfFacial.size(); i++) {
            System.out.println(valueOfFacial.get(i));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

