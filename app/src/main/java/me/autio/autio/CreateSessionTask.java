package me.autio.autio;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by qasim on 15-01-17.
 */
class CreateSessionTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    private static final String API_ENDPOINT = "http://104.237.150.113:3000/api/v1";
    private Context mContext;

    public CreateSessionTask(Context context){
        mContext = context;
    }

    protected String doInBackground(String... urls) {
        // Get first name
        String firstName = null;
        Cursor c = mContext.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
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
        String url = API_ENDPOINT + "?action=create&first_name="+firstName;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.i("LoginSpotify", "Response: " + response.toString());
            Log.i("LoginSpotify", "Body: " + response.body().string());
            return response.body().string();
        } catch (IOException e) {
            Log.e("LoginSpotify", e.toString());
            return null;
        }
    }

    protected void onPostExecute(String obj) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
