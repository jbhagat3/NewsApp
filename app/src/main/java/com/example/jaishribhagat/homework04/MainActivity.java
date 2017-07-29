package com.example.jaishribhagat.homework04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jaishribhagat.homework04.info.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, GithubAdapter.ItemClickListener
{
    private static final String TAG = "mainactivity";
    private ProgressBar progress;
    private GithubAdapter adapter;
    private RecyclerView recycle;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int NEWS_LOADER = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        recycle = (RecyclerView) findViewById(R.id.recyclerView);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);
        // Using sharedpreference to know if its getting installed for the first time.
        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);
    }

    //fetching data for the recycler view when resuming.

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new GithubAdapter(cursor, this);
        recycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Closing the pointers.
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itenNumber = item.getItemId();
        if (itenNumber == R.id.search) {
            load();
        }
        return true;
    }
//implemented all the required callbacks.
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                Refresh.refreshArticles(MainActivity.this);
                return null;
            }

        };
    }
//attaches data to recycler view and notifies the adapter.
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        adapter = new GithubAdapter(cursor, this);
        recycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }

    //used to open the url in web browser.
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //starts the async task loader
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }

    @Override
    public void onItemClick(int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
