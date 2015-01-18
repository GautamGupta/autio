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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


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
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);

            } else {
                Log.i("Barcode Result", "Failed");
            }
        }
    }

    /**
     * Called when the user clicks the Create button
     */
    public void btnCreate(View view) {
        Intent intent = new Intent(this, LoginSpotify.class);
        startActivity(intent);
    }
}
