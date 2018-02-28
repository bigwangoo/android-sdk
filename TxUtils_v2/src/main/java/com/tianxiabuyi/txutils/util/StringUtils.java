package com.tianxiabuyi.txutils.util;

import com.tianxiabuyi.txutils.TxUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jjj on 2017/9/28.
 *
 * @description:
 */

public class StringUtils {

    public static String getString(int id) {
        return TxUtils.getInstance().getContext().getString(id);
    }

    /**
     * 是否含有表情
     * @param content
     * @return
     */
    public static boolean isEmote(String content) {
        String NOT_EMOJI = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern pattern = Pattern.compile(NOT_EMOJI);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
