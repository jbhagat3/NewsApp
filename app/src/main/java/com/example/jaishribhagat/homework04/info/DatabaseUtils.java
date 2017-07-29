package com.example.jaishribhagat.homework04.info;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.*;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_DATE;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.example.jaishribhagat.homework04.info.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by jaishribhagat on 7/28/17.
 */

public class DatabaseUtils
{

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_DATE + " DESC"
        );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<Article> articles) {

        db.beginTransaction();
        try {
            for (Article a : articles) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_AUTHOR, a.getAuthor());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_THUMBURL, a.getUrlToImage());
                cv.put(COLUMN_NAME_URL, a.getUrl());
                cv.put(COLUMN_NAME_DATE, a.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

}
