package me.autio.autio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.Player;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends ActionBarActivity{

    private static final String API_ENDPOINT = "http://104.237.150.113:3000/api/v1";

    private static final String CLIENT_ID = "7793202b53f64455ad566ade6425711d";

    public Context mContext;

    public Config playerConfig;

    public Player mPlayer;

    public Spotify spotify;

    public String[] ids;

    public AsyncHttpClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String access_token = getResources().getString(R.string.access_token);

        playerConfig = new Config(this, access_token, CLIENT_ID);
        spotify = new Spotify();

        mContext = this.getApplicationContext();

        client = new AsyncHttpClient();

        EditText et = (EditText) findViewById(R.id.search);
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                Log.i("Spotifysearch", q);

                final String query = q;

                String url = API_ENDPOINT + "/search?q=" + q;
                client.get(url, new JsonHttpResponseHandler() {
                    SearchList adapter;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when response HTTP status is "200 OK"
                        JSONArray results = null;
                        try {
                            results = response.getJSONArray("results");

                            Log.i("SearchSpotify", "yes");

                            String[] titles = new String[results.length()];
                            String[] artists = new String[results.length()];
                            String[] artworks = new String[results.length()];
                            ids = new String[results.length()];

                            for(int i = 0; i < results.length(); i++) {
                                ids[i] = results.getJSONObject(i).getString("id");
                                titles[i] = results.getJSONObject(i).getString("name");
                                artists[i] = results.getJSONObject(i).getString("artist");
                                artworks[i] = results.getJSONObject(i).getJSONObject("artwork").getString("url");
                            }

                            if(query.length() > 3) {
                                adapter = new SearchList(SearchActivity.this, titles, artists, artworks);
                            } else {
                                adapter = new SearchList(SearchActivity.this, new String[]{}, new String[]{}, new String[]{});
                            }

                            ListView list = (ListView)findViewById(R.id.list);
                            list.setAdapter(adapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int p, long id) {
                                    final String spotify_id = ids[p];

                                    mPlayer = spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                                        @Override
                                        public void onInitialized() {
                                            mPlayer.play("spotify:track:" + spotify_id);
                                        }

                                        @Override
                                        public void onError(Throwable throwable) {
                                            Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                                        }
                                    });
                                }
                            });

                        } catch (JSONException e) {
                            Log.e("SearchSpotify", "nope");
                        }

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }



}


