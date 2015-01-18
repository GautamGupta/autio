package me.autio.autio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Ivan on 2015-01-17.
 */
public class SearchActivity extends ActionBarActivity{

    private static final String API_ENDPOINT = "http://104.237.150.113:3000/api/v1";

    public Context mContext;

    public AsyncHttpClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = this.getApplicationContext();

        client = new AsyncHttpClient();

        EditText et = (EditText) findViewById(R.id.search);
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                Log.i("Spotifysearch", q);

                String url = API_ENDPOINT + "/search?q=" + q;
                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when response HTTP status is "200 OK"
                        JSONArray results = null;
                        try {
                            results = response.getJSONArray("results");
                            Log.i("SearchSpotify", "yes");
                        } catch (JSONException e) {
                            Log.e("SearchSpotify", "nope");
                        }

                        String[] titles = new String[results.length()];
                        String[] artists = new String[results.length()];
                        String[] artworks = new String[results.length()];

                        /*for(int i = 0; i < results.length(); i++) {
                            titles[i] =
                        }*/

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


