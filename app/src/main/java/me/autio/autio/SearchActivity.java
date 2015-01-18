package me.autio.autio;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
<<<<<<< HEAD
import android.widget.EditText;
=======
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
<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> origin/master

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

                            for(int i = 0; i < results.length(); i++) {
                                titles[i] = results.getJSONObject(i).getString("name");
                                artists[i] = results.getJSONObject(i).getString("artist");
                                artworks[i] = results.getJSONObject(i).getJSONObject("artwork").getString("url");
                            }

<<<<<<< HEAD
                            SearchList adapter = new
                                    SearchList(SearchActivity.this, titles, artists, artworks);
=======
                            if(query.length() > 3) {
                                adapter = new SearchList(SearchActivity.this, titles, artists, artworks);
                            } else {
                                adapter = new SearchList(SearchActivity.this, new String[]{}, new String[]{}, new String[]{});
                            }

>>>>>>> origin/master
                            ListView list = (ListView)findViewById(R.id.list);
                            list.setAdapter(adapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    //Toast.makeText(SearchActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (JSONException e) {
                            Log.e("SearchSpotify", "nope");
                        }

<<<<<<< HEAD
                        
                        String[] titles = new String[results.length()];
                        String[] artists = new String[results.length()];
                        String[] artworks = new String[results.length()];

                        /*for(int i = 0; i < results.length(); i++) {
                            titles[i] =
                        }*/


=======
>>>>>>> origin/master
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


