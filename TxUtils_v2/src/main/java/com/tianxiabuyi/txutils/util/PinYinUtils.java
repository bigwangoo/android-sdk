package com.tianxiabuyi.txutils.util;

import android.annotation.SuppressLint;

import java.util.ArrayList;

/**
 * Created by jjj on 2017/5/24.
 *
 * @description:
 */

public class PinYinUtils {
    //汉字返回拼音，字母原样返回，都转换为小写
    @SuppressLint("DefaultLocale")
    public static String getFullPinYin(String input) {
        ArrayList<Hanzi2PinyinUtils.Token> tokens = Hanzi2PinyinUtils.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Hanzi2PinyinUtils.Token token : tokens) {
                if (Hanzi2PinyinUtils.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    //汉字返回拼音，字母原样返回，都转换为大写
    @SuppressLint("DefaultLocale")
    public static String getFullPinYin2(String input) {
        ArrayList<Hanzi2PinyinUtils.Token> tokens = Hanzi2PinyinUtils.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Hanzi2PinyinUtils.Token token : tokens) {
                if (Hanzi2PinyinUtils.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    //汉字返回拼音，字母原样返回，都转换为小写
    @SuppressLint("DefaultLocale")
    public static String getFirstPinYin(String input) {
        ArrayList<Hanzi2PinyinUtils.Token> tokens = Hanzi2PinyinUtils.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Hanzi2PinyinUtils.Token token : tokens) {
                if (Hanzi2PinyinUtils.Token.PINYIN == token.type) {
                    if (token.target != null && !"".equals(token.target))
                        sb.append(token.target.charAt(0));
                } else {
                    if (token.source != null && !"".equals(token.source))
                        sb.append(token.source.charAt(0));
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    //汉字返回拼音，字母原样返回，都转换为小写
    @SuppressLint("DefaultLocale")
    public static String getFirstPinYin2(String input) {
        ArrayList<Hanzi2PinyinUtils.Token> tokens = Hanzi2PinyinUtils.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Hanzi2PinyinUtils.Token token : tokens) {
                if (Hanzi2PinyinUtils.Token.PINYIN == token.type) {
                    if (token.target != null && !"".equals(token.target))
                        sb.append(token.target.charAt(0));
                } else {
                    if (token.source != null && !"".equals(token.source))
                        sb.append(token.source.charAt(0));
                }
            }
        }
        return sb.toString().toUpperCase();
    }
}
