package me.autio.autio;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.graphics.Picture;
import android.os.Bundle;
import android.widget.ImageView;


import android.app.Activity;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.spotify.sdk.android.authentication.SpotifyAuthentication;

/**
 * Created by Ivan on 2015-01-17.
 */
public class QueueActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
    }

}
