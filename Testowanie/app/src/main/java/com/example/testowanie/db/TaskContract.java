package com.example.testowanie.db;

import android.provider.BaseColumns;

public class TaskContract { //task contract class defines constants which are used to access the data in the database
    // Helper class TaskDbHelper is needed to open the database
    public static final String DB_NAME = "com.example.testowanie.db";
    public static final int DB_VERSION = 1;

    public class TaskMeetingEntry implements BaseColumns{
        public static final String TABLE = "meeting";
        public static final String COL_TASK_DATETIME = "time";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_PLACE = "place";
        public static final String COL_TASK_PARTICIPANTS = "participants";
    }

    public class TaskPhoneEntry implements BaseColumns{
        public static final String TABLE = "phone";
        public static final String COL_TASK_DATETIME = "time";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_PERSON = "person";
        public static final String COL_TASK_PHONENUMBER = "phone";
    }

    public class TaskEmailEntry implements BaseColumns{
        public static final String TABLE = "email";
        public static final String COL_TASK_DATETIME = "time";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_PERSON = "person";
        public static final String COL_TASK_EMAILADDRESS = "email";
    }

    public class TaskNoteEntry implements BaseColumns{
        public static final String TABLE = "note";
        public static final String COL_TASK_DATETIME = "time";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_NOTE = "note";
    }
}
