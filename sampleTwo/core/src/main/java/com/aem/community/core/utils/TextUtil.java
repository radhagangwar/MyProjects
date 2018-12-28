package com.aem.community.core.utils;

import org.apache.jackrabbit.util.Text;

public class TextUtil {

    public static String getLanguageRoot(String path){
        return Text.getAbsoluteParent(path, 2);
    }

    public static String getLanguageCode(String path){
        String code = getLanguageRoot(path);
        //return code == null ? "en" : Text.getName(code) + "_" + Text.getName(Text.getRelativeParent(code, 1)).toUpperCase();
        return code;
    }
}
