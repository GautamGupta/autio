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

/**
 * Created by Ivan on 2015-01-17.
 */
public class SpotifyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_spotify);
        SVG s = SVGParser.getSVGFromResource(getResources(), R.drawable.log_in_mobile);
        Picture picture = s.getPicture();
        Drawable drawable = s.createPictureDrawable();
    }

    public void btnLogin(View view){
        Intent intent = new Intent(this, LoginSpotify.class);
        startActivity(intent);
    }
}
