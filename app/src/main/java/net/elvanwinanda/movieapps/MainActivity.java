package net.elvanwinanda.movieapps;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.elvanwinanda.movieapps.Adapater.MovAdapter;
import net.elvanwinanda.movieapps.Api.ApiData;
import net.elvanwinanda.movieapps.Api.MovieInt;
import net.elvanwinanda.movieapps.Model.MovieProcess;
import net.elvanwinanda.movieapps.Model.MovieResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "a7c8941568b4414ac29d949a548c0706";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btn_src;
    private EditText find_movie;
    static final String Note = "Note";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        find_movie = (EditText) findViewById(R.id.find_movie);

        btn_src = (Button) findViewById(R.id.btn_src);
        btn_src.setOnClickListener(this);

        getAllMovie();

    }

    private void getAllMovie(){
        MovieInt apiService = ApiData.getRetrofit().create(MovieInt.class);
        Call<MovieProcess> call = apiService.reposMovieList(API_KEY);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(50);
        progressDialog.setMessage("Loading");
        progressDialog.setTitle("Movieapps");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieProcess>() {
            @Override
            public void onResponse(Call<MovieProcess> call, Response<MovieProcess> response) {
                progressDialog.dismiss();

                List<MovieResult> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                mAdapter = new MovAdapter(MovieList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MovieProcess> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getSearchMovie(String Note){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(50);
        progressDialog.setMessage("Loading");
        progressDialog.setTitle("Movieapps");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        MovieInt apiService = ApiData.getRetrofit().create(MovieInt.class);
        Call<MovieProcess> call = apiService.reposSearch(API_KEY, Note);
        call.enqueue(new Callback<MovieProcess>() {
            @Override
            public void onResponse(Call<MovieProcess> call, Response<MovieProcess> response) {
                progressDialog.dismiss();
                List<MovieResult> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                mAdapter = new MovAdapter(MovieList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MovieProcess> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
        if (v.getId() == R.id.btn_src){
            String Note = find_movie.getText().toString();
            if (Note.isEmpty()){
                Note = "!";
                Log.e(TAG,"Data = "+Note);
                Toast.makeText(MainActivity.this, "Movie not found "+Note, Toast.LENGTH_SHORT).show();
                getSearchMovie(Note);

            }else {
                Log.e(TAG,"Data = "+Note);
                Toast.makeText(MainActivity.this, "Movies found "+Note, Toast.LENGTH_SHORT).show();
                getSearchMovie(Note);
            }

        }

    }

}