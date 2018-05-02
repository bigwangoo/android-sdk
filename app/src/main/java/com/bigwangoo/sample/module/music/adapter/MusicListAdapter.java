package com.txby.sample_kit.demo.music.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.apps.R;
import com.txby.sample_kit.demo.music.module.SongBean;

import java.util.List;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class MusicListAdapter extends BaseQuickAdapter<SongBean, BaseViewHolder> {

    public MusicListAdapter(@Nullable List<SongBean> data) {
        super(R.layout.apps_item_music_txt, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongBean item) {
        helper.setText(R.id.tv_song, item.getTitle())
                .setText(R.id.tv_singer, item.getArtist());
    }
}
