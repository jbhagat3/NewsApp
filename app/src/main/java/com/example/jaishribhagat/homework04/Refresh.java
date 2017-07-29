package com.example.jaishribhagat.homework04;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaishribhagat.homework04.info.Article;
import com.example.jaishribhagat.homework04.info.DBHelper;
import com.example.jaishribhagat.homework04.info.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jaishribhagat on 7/28/17.
 */


//connects with the database and add new data replacing the existing data.
public class Refresh
{
    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<Article> result = null;
        URL url = NetworkUtils.makeURL("the-next-web", "latest", "5f18b2b1ce7b4b7283bc1e7f90a36682");

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
