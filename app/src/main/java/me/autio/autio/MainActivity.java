package me.autio.autio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the Join button
     */
    public void btnJoin(View view) {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.addExtra("SCAN_WIDTH", 500);
        integrator.addExtra("SCAN_HEIGHT", 500);
        integrator.addExtra("SCAN_MODE", "SCAN_MODE");
        integrator.addExtra("PROMPT_MESSAGE", R.string.scan_qr);
        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }

    /**
     * Receive the QR Code Data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                String session_id = result.getContents();
                Log.i("Barcode Result", session_id);

                //Update local shared preferences of session
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.session_id), session_id);
                editor.putString(getString(R.string.device_type), "client");
                editor.commit();
                joinSession(session_id);
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);

            } else {
                Log.i("Barcode Result", "Failed");
            }
        }
    }


    public void joinSession(String session_id) {

        // Get first name
        String firstName = null;
        Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
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
/*
        // Create session on server
        AsyncHttpClient client = new AsyncHttpClient();
        String url = API_ENDPOINT + "?action=join&first_name=" + firstName;

        // Request page
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String session_id = new JSONObject(response.toString()).getString("session_id");
                    Log.i("LoginSpotify", "Session id: " + session_id);
                    sharedPrefEditor.putString(getString(R.string.session_id), session_id);
                    sharedPrefEditor.commit();
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("LoginSpotify", "Error: " + e.toString());
                }
            }
        });
*/
    }

    /**
     * Called when the user clicks the Create button
     */
    public void btnCreate(View view) {
        Intent intent = new Intent(this, LoginSpotify.class);
        startActivity(intent);
    }
}
