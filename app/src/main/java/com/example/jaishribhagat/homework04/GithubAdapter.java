package com.example.jaishribhagat.homework04;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaishribhagat.homework04.info.Contract;
import com.squareup.picasso.Picasso;


/**
 * Created by Jaishri on 6/29/2017.
 */

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ItemHolder>{

    private Cursor cursor;
    ItemClickListener listener;
    private Context context;
    public static final String Tag = "myadapter";


    public GithubAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }


    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);

        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView publishedAt;
        ImageView img;

        ItemHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            publishedAt = (TextView) view.findViewById(R.id.publishedAt);
            img = (ImageView) view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }


        // get values from database and setting it to the UI.
        public void bind(int pos) {
            cursor.moveToPosition(pos);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            publishedAt.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DATE)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL));
            Log.d(Tag, url+"  hihi");
            if(url != null){
                Picasso.with(context)
                        .load(url)
                        .into(img);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }


}

