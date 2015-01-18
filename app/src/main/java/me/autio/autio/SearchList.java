package me.autio.autio;

/**
 * Created by qasim on 15-01-18.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class SearchList extends ArrayAdapter<String>{
    private final Activity context;

    private final String[] name;
    private final String[] artist;
    private final String[] artwork;

    public SearchList(Activity context, String[] name, String[] artist, String[] artwork) {
        super(context, R.layout.search_result, name);

        this.context = context;

        this.name = name;
        this.artist = artist;
        this.artwork = artwork;
    }

    @Override
    public View getView(int p, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.search_result, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        TextView txtArtist = (TextView) rowView.findViewById(R.id.txtArtist);
        ImageView imgArtwork = (ImageView) rowView.findViewById(R.id.imgArtwork);

        txtTitle.setText(name[p]);
        txtArtist.setText(artist[p]);
        new DownloadImageTask((ImageView) rowView.findViewById(R.id.imgArtwork)).execute(artwork[p]);

        return rowView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

