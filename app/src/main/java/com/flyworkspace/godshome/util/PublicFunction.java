package com.flyworkspace.godshome.util;

import android.content.Context;
import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by jinpengfei on 15/2/7.
 */
public class PublicFunction {
    public static String readFromAsset(Context context, String fileName) {
        String res = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static InputStream getInputStreamFromAsset(Context context, String fileName) {
        try {
            InputStream in = context.getAssets().open(fileName);
            return in;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
