package utils;

import com.sun.istack.internal.Nullable;

/**
 * Created by DELL on 2017/8/25.
 */
public class TextUtils {
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
