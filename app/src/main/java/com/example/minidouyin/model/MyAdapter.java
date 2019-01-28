package com.example.minidouyin.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.minidouyin.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Feed> feedList;
    private int mNumberItems;

    private final ListItemClickListener mOnClickListener;

    public MyAdapter(List<Feed> messages, ListItemClickListener listener) {
        feedList = messages;
        mNumberItems = messages.size();
        mOnClickListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Feed feed = feedList.get(i);
        myViewHolder.updateUI(feed);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView author;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            author = itemView.findViewById(R.id.author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //通过代理
                    mOnClickListener.onListItemClick(getAdapterPosition());
                }
            });
        }
        public void updateUI(Feed feed){
            author.setText(feed.getUser_name());
            String url = feed.getCover_image();
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }

    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
