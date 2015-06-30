package cn.lym.playerloyo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lym on 15-6-23.
 */
public class GuideViewPager extends PagerAdapter {
    private List<View> views;

    public GuideViewPager(List<View> Views) {
        this.views = Views;
    }

    @Override
    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(views.get(position));
    }


    @Override
    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    // 获取要滑动的控件的数量
    public int getCount() {
        return  views.size();
    }

    @Override
    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
}