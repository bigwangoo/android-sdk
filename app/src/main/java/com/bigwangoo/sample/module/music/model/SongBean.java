package com.bigwangoo.sample.module.music.model;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class SongBean {

    private String title;
    private String artist;

    public SongBean() {
    }

    public SongBean(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "SongBean{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
