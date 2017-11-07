package com.matbar.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView mScore1;
    TextView mScore2;
    Button mButton1;
    Button mButton2;
    Toast toast;
    int set=1;
    static final String TAG=MainActivity.class.getSimpleName();
    static final String KEY_SCORE_1="player_1_score";
    static final String KEY_SCORE_2="player_2_score";
    static final String KEY_POINTS_1_1="player_1_set_1";
    static final String KEY_POINTS_1_2="player_1_set_2";
    static final String KEY_POINTS_1_3="player_1_set_3";
    static final String KEY_POINTS_2_1="player_2_set_1";
    static final String KEY_POINTS_2_2="player_2_set_2";
    static final String KEY_POINTS_2_3="player_2_set_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScore1 = (TextView)findViewById(R.id.player_1_points);
        mScore2 = (TextView)findViewById(R.id.player_2_points);
        mButton1 =(Button) findViewById(R.id.player_1_add_point);
        mButton2 = (Button) findViewById(R.id.player_2_add_point);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increasePoints(mScore1,mScore2,1);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increasePoints(mScore2,mScore1,2);
            }
        });
        if (savedInstanceState!=null)
        {
            mScore1.setText(savedInstanceState.getString(KEY_SCORE_1));
            mScore2.setText(savedInstanceState.getString(KEY_SCORE_2));
            ((TextView)findViewById(R.id.player_1_set_1)).setText(savedInstanceState.getString(KEY_POINTS_1_1));
            ((TextView)findViewById(R.id.player_1_set_2)).setText(savedInstanceState.getString(KEY_POINTS_1_2));
            ((TextView)findViewById(R.id.player_1_set_3)).setText(savedInstanceState.getString(KEY_POINTS_1_3));
            ((TextView)findViewById(R.id.player_2_set_1)).setText(savedInstanceState.getString(KEY_POINTS_2_1));
            ((TextView)findViewById(R.id.player_2_set_2)).setText(savedInstanceState.getString(KEY_POINTS_2_2));
            ((TextView)findViewById(R.id.player_2_set_3)).setText(savedInstanceState.getString(KEY_POINTS_2_3));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.reset)
        {
            reset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void reset()
    {
        mScore1.setText("0");
        mScore2.setText("0");
        ((TextView)findViewById(R.id.player_1_set_1)).setText("0");
        ((TextView)findViewById(R.id.player_1_set_2)).setText("0");
        ((TextView)findViewById(R.id.player_1_set_3)).setText("0");
        ((TextView)findViewById(R.id.player_2_set_1)).setText("0");
        ((TextView)findViewById(R.id.player_2_set_2)).setText("0");
        ((TextView)findViewById(R.id.player_2_set_3)).setText("0");
        set=1;
    }
    private void increasePoints(TextView increaseView, TextView compareView, int nr)
    {

        if (set<4) {
            String compare = compareView.getText().toString();
            if (compare != "A") {
                String currentPoints = increaseView.getText().toString();
                if (currentPoints == "A" || (compare != "40" && currentPoints == "40")) {
                    winGame(nr,invertNumber(nr));
                } else {
                    String incrementedPoints;
                    int points = Integer.parseInt(currentPoints);

                    switch (points) {
                        case 30: {
                            incrementedPoints = "40";
                            break;
                        }
                        case 40: {
                            incrementedPoints = "A";
                            break;
                        }
                        default: {
                            incrementedPoints = Integer.toString(points + 15);
                            break;
                        }
                    }
                    increaseView.setText(incrementedPoints);
                }
            } else {
                compareView.setText("40");
            }
        }
        else
        {
            if (toast!=null)
                toast.cancel();
            toast=Toast.makeText(this,"Game is over",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private int invertNumber(int nr)
    {
        if(nr==1)
            return 2;
        else
            return 1;
    }
    private void winGame(int winnerNr, int loserNr)
    {
        Log.v(TAG, Integer.toString(getResources().getIdentifier("player_"+winnerNr+"_set_"+set,"id",getPackageName())));
        Log.v(TAG, Integer.toString(R.id.player_1_set_1));
        Log.v(TAG, Integer.toString(getResources().getIdentifier("player_"+loserNr+"_set_"+set,"id",getPackageName())));
        Log.v(TAG, Integer.toString(R.id.player_2_set_1));
        TextView winnerView=(TextView)findViewById(getResources().getIdentifier("player_"+winnerNr+"_set_"+set,"id",getPackageName()));
        TextView loserView=(TextView)findViewById(getResources().getIdentifier("player_"+loserNr+"_set_"+set,"id",getPackageName()));
        Log.v(TAG,Boolean.toString(winnerView==null));
        Log.v(TAG,Boolean.toString(loserView==null));
        Log.v(TAG, winnerView.getText().toString());
        Log.v(TAG, loserView.getText().toString());
        mScore1.setText("0");
        mScore2.setText("0");

        winnerView.setText(Integer.toString(Integer.valueOf(winnerView.getText().toString())+1));
        checkSet(Integer.parseInt(winnerView.getText().toString()),Integer.parseInt(loserView.getText().toString()));
    }
    private void checkSet(int wins1, int wins2)
    {
        if (Math.abs(wins1-wins2)>=2&&(wins1>=6||wins2>=6)||(wins1==7||wins2==7))
        {
            set++;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SCORE_1,mScore1.getText().toString());
        outState.putString(KEY_SCORE_2,mScore2.getText().toString());
        outState.putString(KEY_POINTS_1_1,((TextView)findViewById(R.id.player_1_set_1)).getText().toString());
        outState.putString(KEY_POINTS_1_2,((TextView)findViewById(R.id.player_1_set_2)).getText().toString());
        outState.putString(KEY_POINTS_1_3,((TextView)findViewById(R.id.player_1_set_3)).getText().toString());
        outState.putString(KEY_POINTS_2_1,((TextView)findViewById(R.id.player_2_set_1)).getText().toString());
        outState.putString(KEY_POINTS_2_2,((TextView)findViewById(R.id.player_2_set_2)).getText().toString());
        outState.putString(KEY_POINTS_2_3,((TextView)findViewById(R.id.player_2_set_3)).getText().toString());
    }
}
