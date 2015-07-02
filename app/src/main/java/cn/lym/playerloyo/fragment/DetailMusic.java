package cn.lym.playerloyo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.lym.playerloyo.R;

public class DetailMusic extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView returnGeneral;
    private TextView currentCatalogue;
    private ImageView searchInThere;
    private ImageView menuMore;
    private ListView theseMusic;
    private ShareDetail shareDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_musics_in_music_list, container, false);
        init();
        shareDetail.initDetail();
        setAllOnClickListener();
        return view;
    }

    public void init() {
        returnGeneral = (ImageView) view.findViewById(R.id.image_view_fragment_detail_return);
        currentCatalogue = (TextView) view.findViewById(R.id.text_view_fragment_general_current_catalogue);
        searchInThere = (ImageView) view.findViewById(R.id.image_view_fragment_detail_search_in_there);
        menuMore = (ImageView) view.findViewById(R.id.image_view_fragment_detail_menu_more);
        theseMusic = (ListView) view.findViewById(R.id.list_view_fragment_detail);
    }

    public void setAllOnClickListener() {
        returnGeneral.setOnClickListener(this);
        searchInThere.setOnClickListener(this);
        menuMore.setOnClickListener(this);
        theseMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_fragment_detail_return:
                shareDetail.returnToGeneral();
                break;
            default:
                break;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        shareDetail = (ShareDetail) activity;
    }

    public interface ShareDetail {
        void returnToGeneral();

        void initDetail();
    }
}
