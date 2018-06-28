package by.yarik.yarikweather.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.yarik.yarikweather.R;

public class StringUtils {

    public static String getAppId(Context context) {
        return context.getResources().getString(R.string.api_key);
    }

    public static String listToString(List<String> list) {
        String str = "";
        for(int i = 0; i < list.size(); i++) {
            str += list.get(i) + (i == list.size() - 1 ? "" : ",");
        }
        return str;
    }
    public static List<String> StringToList(String str) {
        List<String> list = Arrays.asList(str.split(","));
        return new ArrayList<>(list);
    }
}
