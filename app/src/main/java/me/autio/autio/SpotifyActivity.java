package me.autio.autio;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.graphics.Picture;
import android.os.Bundle;
import android.widget.ImageView;


import android.app.Activity;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by Ivan on 2015-01-17.
 */
public class SpotifyActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView title = (TextView)findViewById(R.id.audio_title);
        //SVG s = SVGParser.getSVGFromResource(getResources(), R.drawable.);
        //Picture picture = s.getPicture();
        //Drawable drawable = s.createPictureDrawable();
        setContentView(R.layout.activity_main);

    }

    protected void logInSpotify(){
        //Gautam magic goes here
    }
}
