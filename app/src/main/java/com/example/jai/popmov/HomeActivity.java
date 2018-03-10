package com.example.jai.popmov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    String[] items = new String[20];
    RecyclerView rv;
    RecyclerViewAdapter rva;
    String sortBy = "popularity.desc";
    String resultFromInternet;
    JSONArray multipleMovies;
    JSONObject singleMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //populate recyclerview with dummy strings
        /*for (int i=0; i<=9;i++)
        {
            items[i]="item "+i;
        }*/

        rv = findViewById(R.id.rv);
        rva = new RecyclerViewAdapter(this,items,multipleMovies);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(rva);

        sort();
    }

    private void sort() {
        //this method will sort according to the selected option
        if (FetchMovie())
            displayMovies(items,multipleMovies);
        else
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show();
    }

    private void displayMovies(String [] imageURLs, JSONArray temp) {
        //we'll display data here
        rva = new RecyclerViewAdapter(this,imageURLs,multipleMovies);
        rv.swapAdapter(rva, true);
        // rva.notifyDataSetChanged();
        System.out.println(imageURLs[0]);
        //rv.setAdapter(rva);
    }

    private boolean FetchMovie()
    {
        //now calling asyncktask
        Fetchdata fd = new Fetchdata();
        try
        {
            resultFromInternet = fd.execute(sortBy).get();
            if (resultFromInternet != null)
            {
                //a temporary object
                JSONObject temp = new JSONObject(resultFromInternet);
                multipleMovies = temp.getJSONArray("results");
                for (int i = 0; i < multipleMovies.length(); i++)
                {
                    singleMovie = multipleMovies.getJSONObject(i);
                    items[i] = "http://image.tmdb.org/t/p/w185"+singleMovie.getString("poster_path");
                }
                return true;
            }
            //if the string returned from backgroundthread was null then return false
            else
            return  false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            sortBy = "popularity.desc";
            sort();
            Toast.makeText(this, "Movies sorted by Popularity", Toast.LENGTH_SHORT).show();
        }
        else
        {
            sortBy="vote_count.desc";
            sort();
            Toast.makeText(this, "Movies sorted by Ratings", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}
