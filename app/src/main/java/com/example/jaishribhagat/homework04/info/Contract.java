package com.example.jaishribhagat.homework04.info;

import android.provider.BaseColumns;

/**
 * Created by jaishribhagat on 7/28/17.
 */

public class Contract
{
    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_THUMBURL = "urlToImage";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_DATE = "publishedAt";
    }
}
