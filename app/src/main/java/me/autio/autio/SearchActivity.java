package me.autio.autio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Ivan on 2015-01-17.
 */
public class SearchActivity extends ActionBarActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }
    EditText et = (EditText) findViewById(R.id.search);
    StringBuilder query = new StringBuilder();
    et.setOnEditorActionListener (new TextView.OnEditorActionListener(){
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchSpotify(query.toString());
                handled = true;
                query = new StringBuilder();
            }
            else
            {
                if(query.length() > 0){
                    searchSpotify(query.toString());
                }
                query.append(event.getCharacters().substring(event.getCharacters().lenth() - 1));
                handled = false;
            }
            return handled;
        }
    });

}


