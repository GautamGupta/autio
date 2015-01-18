package me.autio.autio;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.zxing.common.StringUtils;
import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.authentication.SpotifyAuthentication;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;
import com.spotify.sdk.android.playback.PlayerState;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class LoginSpotify extends ActionBarActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "7793202b53f64455ad566ade6425711d";
    private static final String REDIRECT_URI = "autio://callback";
    private static final String API_ENDPOINT = "http://104.237.150.113:3000/api/v1/";

    private Player mPlayer;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_spotify);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String access_token = sharedPref.getString(getString(R.string.access_token), "");
        if (access_token.isEmpty()) {
            SpotifyAuthentication.openAuthWindow(CLIENT_ID, "token", REDIRECT_URI,
                    new String[]{"user-read-private", "streaming"}, null, this);
        } else {
            Log.i("LoginSpotify", access_token);
            newSession();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = SpotifyAuthentication.parseOauthResponse(uri);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.access_token), response.getAccessToken());
            editor.commit();
            Log.i("LoginSpotify", response.getAccessToken());
            newSession();

            /*
            Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
            Spotify spotify = new Spotify();
            mPlayer = spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                @Override
                public void onInitialized() {
                    mPlayer.addConnectionStateCallback(LoginSpotify.this);
                    mPlayer.addPlayerNotificationCallback(LoginSpotify.this);
                    mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });
            */
        }
    }

    public void newSession() {
        // Get first name
        String firstName = null;
        Cursor c = getApplicationContext().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        String[] columnNames = c.getColumnNames();
        boolean b = c.moveToFirst();
        int position = c.getPosition();
        if (count == 1 && position == 0) {
            for (int j = 0; j < columnNames.length; j++) {
                String columnName = columnNames[j];
                if (columnName.equalsIgnoreCase("display_name"))
                    firstName = c.getString(c.getColumnIndex(columnName));
            }
        }
        c.close();
        Log.i("LoginSpotify", "First: " + firstName);

        // API call
        String url = API_ENDPOINT;
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormEncodingBuilder()
                .add("action", "create")
                .add("first_name", firstName)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.i("LoginSpotify", "Response: " + response.toString());
        } catch (IOException e) {
            Log.e("LoginSpotify", e.toString());
            return;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("LoginSpotify", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("LoginSpotify", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("LoginSpotify", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("LoginSpotify", "Temporary error occurred");
    }

    @Override
    public void onNewCredentials(String s) {
        Log.d("LoginSpotify", "User credentials blob received");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("LoginSpotify", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("LoginSpotify", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("LoginSpotify", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
