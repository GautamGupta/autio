package me.autio.autio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Ivan on 2015-01-17.
 */
public class SpotifyActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView title = (TextView)findViewById(R.id.audio_title);
        setContentView(R.layout.activity_spotify);

    }

    protected void logInSpotify(){
        //Gautam magic goes here
    }
}
