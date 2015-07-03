package cn.lym.playerloyo.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.activity.MusicList;
import cn.lym.playerloyo.adapter.MusicListFragment;

public class GeneralMusic extends Fragment implements View.OnClickListener {
    private int offset = 0;
    private int currentPageIndex = 0;
    private int currentPageImageWidth;

    private ImageView returnCurrentPlay;
    private TextView currentCatalogue;
    private ImageView searchInThere;
    private ImageView currentPage;
    private ViewPager musicPager;
    private ArrayList<Fragment> fragments;
    private Fragment allMusic, accordingSinger, accordingFolder;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general_music_in_music_list, container, false);
        initImageView();
        initCurrentPageLocate();
        initTextView();
        initViewPager();
        setAllOnClickListener();
        return view;
    }

    private void initImageView() {
        currentPage = (ImageView) view.findViewById(R.id.image_view_fragment_general_current_page);
        returnCurrentPlay = (ImageView) view.findViewById(R.id.image_view_fragment_general_return_current_play);
        searchInThere = (ImageView) view.findViewById(R.id.image_view_fragment_general_search_in_there);
    }

    public void initCurrentPageLocate() {
        currentPageImageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.img_page_indicator).getWidth();// 获取图片宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); //获取屏幕分辨率信息
        int screenW = displayMetrics.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - currentPageImageWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        currentPage.setImageMatrix(matrix);// 设置动画初始位置
    }

    public void initTextView() {
        currentCatalogue = (TextView) view.findViewById(R.id.text_view_fragment_general_current_catalogue);
        currentCatalogue.setText("本地音乐");
    }

    private void initViewPager() {
        musicPager = (ViewPager) view.findViewById(R.id.view_pager_fragment_general);
        fragments = new ArrayList<>();
        allMusic = new AllMusicInMusicList();
        accordingSinger = new AccordingSingerInMusicList();
        accordingFolder = new AccordingFolderInMusicList();
        fragments.add(allMusic);
        fragments.add(accordingSinger);
        fragments.add(accordingFolder);
        musicPager.setAdapter(new MusicListFragment(getFragmentManager(), fragments));
        musicPager.setCurrentItem(0);
        musicPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int one = offset * 2 + currentPageImageWidth;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量

            public void onPageScrollStateChanged(int arg0) {


            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            public void onPageSelected(int arg0) {
                Animation animation = new TranslateAnimation(one * currentPageIndex, one * arg0, 0, 0);
                currentPageIndex = arg0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                currentPage.startAnimation(animation);
            }
        });
    }

    public void setAllOnClickListener() {
        returnCurrentPlay.setOnClickListener(this);
        searchInThere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_fragment_general_return_current_play:
                ((MusicList) getActivity()).sendSelectMusicToCurrentPlay();
        }
    }
}
