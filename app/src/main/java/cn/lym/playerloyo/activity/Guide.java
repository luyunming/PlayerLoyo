package cn.lym.playerloyo.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.adapter.GuideViewPager;

public class Guide extends Activity implements OnClickListener{

    private ViewPager viewPager;//页卡内容
    private List<View> views;// Tab页面列表
    private View view0,view1,view2;//各个页卡
    LayoutInflater inflater;
    private Button viewPager0ToNext;
    private Button viewPager1ToNext;
    private Button viewPager2ToNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        InitViewPager();
        InitOnClickListener();
    }

    private void InitViewPager() {
        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        inflater = getLayoutInflater();
        view0=inflater.inflate(R.layout.view_of_guide_0, null);
        view1=inflater.inflate(R.layout.view_of_guide_1, null);
        view2=inflater.inflate(R.layout.view_of_guide_2, null);
        views.add(view0);
        views.add(view1);
        views.add(view2);
        viewPager.setAdapter(new GuideViewPager(views));
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_viewpager0_to_next:
                viewPager.setCurrentItem(1);
                break;
            case R.id.button_viewpager1_to_next:
                viewPager.setCurrentItem(2);
                break;
            case R.id.button_viewpager2_to_next:
                toActivity(Guide.this,CurrentPlay.class);
                break;
            default:
                break;
        }

    }

    public void InitOnClickListener(){
        viewPager0ToNext = (Button) view0.findViewById(R.id.button_viewpager0_to_next);
        viewPager1ToNext = (Button) view1.findViewById(R.id.button_viewpager1_to_next);
        viewPager2ToNext = (Button) view2.findViewById(R.id.button_viewpager2_to_next);
        viewPager0ToNext.setOnClickListener(this);
        viewPager1ToNext.setOnClickListener(this);
        viewPager2ToNext.setOnClickListener(this);
    }

    public void toActivity(Context context,Class s){
        Intent intent = new Intent(context,s);
        startActivity(intent);
        finish();
    }
}