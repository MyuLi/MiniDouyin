package com.example.minidouyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author tianye.xy@bytedance.com
 * 2019/1/9
 */
public class DetailPlayerActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> {
    StandardGSYVideoPlayer detailPlayer;
    private  String video_url;
    private  String student_name;
    private  String student_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_detail_player);
        Intent intent = getIntent();
        video_url = intent.getStringExtra("video_url");
        student_id = intent.getStringExtra("student_id");
        student_name = intent.getStringExtra("student_name");


        detailPlayer = (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        initVideoBuilderMode();

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

//    private void loadCover(ImageView imageView, String url) {
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        Glide.with(this.getApplicationContext())
//                .setDefaultRequestOptions(
//                        new RequestOptions()
//                                .frame(3000000)
//                                .centerCrop()
//                                .error(R.mipmap.xxx2)
//                                .placeholder(R.mipmap.xxx1))
//                .load(url)
//                .into(imageView);
//    }

}
