package app.com.mazzi.mazzicoffee;

import android.provider.BaseColumns;

public class DatabaseContract{
    public DatabaseContract(){

    }
    public static abstract class Info implements BaseColumns{
        public static final String TABLE_NAME="employ";
        public static final String USER_NAME="user_name";
        public static final String USER_PASS="user_pass";
        public static final String USER_INFO="user_info";
    }
}
