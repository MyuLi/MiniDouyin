package com.example.minidouyin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minidouyin.model.Constant;
import com.example.minidouyin.model.Feed;
import com.example.minidouyin.model.MyAdapter;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianye.xy@bytedance.com
 * 2019/1/9
 */
public class DetailPlayerActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> implements MyAdapter.ListItemClickListener{
    StandardGSYVideoPlayer detailPlayer;
    private  String video_url;
    private  String student_name;
    private  String student_id;
    private List<Feed> user_feedList = new ArrayList<>();
    private MyAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_detail_player);

        recyclerView = findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPlayerActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(user_feedList,this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        TextView nametext = (TextView)findViewById(R.id.tv_name);
        TextView idtext = (TextView)findViewById(R.id.tv_id);
        Intent intent = getIntent();
        video_url = intent.getStringExtra("video_url");
        student_id = intent.getStringExtra("student_id");
        student_name = intent.getStringExtra("student_name");
        nametext.setText(student_name);
        idtext.setText(student_id);
        detailPlayer = (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        initVideoList(student_name,student_id,video_url);

        initVideoBuilderMode();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(DetailPlayerActivity.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                //Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });

    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        //loadCover(imageView, url);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(video_url)
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }


    /**
     * 是否启动旋转横屏，true表示启动
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void initVideoList(String student_name,String student_id ,String video_url){
        for(int i = 0; i < Constant.feeds.size(); i++){
            if(Constant.feeds.get(i).getUser_name().equals(student_name)
                    && Constant.feeds.get(i).getStudent_id().equals(student_id)
            && Constant.feeds.get(i).getVideo() != video_url )
            {
                user_feedList.add(Constant.feeds.get(i));
            }
        }
    }



    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Log.d("getin","ok");
        Intent intent = new Intent(this,DetailPlayerActivity.class);
        startActivity(intent);
    }
}



