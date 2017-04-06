package edu.uw.inputlistdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.y;
import static edu.uw.inputlistdemo.MovieDownloader.downloadMovieData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        // Model
//        String[] data = new String[99];
//        for(int i = 99 ; i > 0; i--) {
//            data[99 - i] = i + " bottles of beer on the wall";
//        }

        // run this in a background thread because you dont want the main
        // thread to be stuck accessing the internet
//        String[] data = MovieDownloader.downloadMovieData("Power Rangers");

        ArrayList<String> data = new ArrayList<String>();


        // View
        // See list_item.xml and list_layout.xml


        // Controller
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, data);
        // convert this set of data by putting the string representation of
        // each data item into txtItem, which is inside of the list_item layout

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


//        Button searchButton = (Button)findViewById(R.id.btnSearch);

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v(TAG, "You clicked me!");
//            }
//        });
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        // Effectively the "runnable"
        protected String[] doInBackground(String... params) {

//            Log.v(TAG, params[0]);
            String[] results = MovieDownloader.downloadMovieData(params[0]);

//            for(String movie : results) {
//                Log.v(TAG, movie);
//            }

            return results;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            if(movies != null) {
                adapter.clear(); // empty adapter for incoming new data
                for(String movie : movies) {
                    adapter.add(movie);
                }
            }
        }
    }

    public void handleButtonSearch(View v) {
        EditText searchQuery = (EditText)findViewById(R.id.txtSearch);
        String searchText = searchQuery.getText().toString();

        MovieDownloadTask myTask = new MovieDownloadTask();
        myTask.execute(searchText);     // dont call doInBackground because that will make it run on the main thread,
                                        // execute makes it run in the background
    }

}
