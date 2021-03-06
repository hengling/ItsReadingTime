package com.ahengling.itsreadingtime.util;

/**
 * Created by adolfohengling on 2/22/18.
 */

public class Constants {

    public static class DATABASE {
        public static final String NAME = "personal_library.db";
    }

    public static class DEFAULT {
        public static final String DATE_FORMAT = "dd/MM/yyyy";
        public static final String TIME_FORMAT = "HH:mm";
        public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    }

    public static class NOTIFICATION {
        public static final String CHANNEL_ID = "irt_notification";
        public static final String CHANNEL_DESCRIPTION = "ItsReadingTime Notifications Channel";
    }

    public static class EXTRAS {
        public static final String BOOK_ID = "BOOK_ID";
    }

    public static class EVENTS {
        public static class BOOK {
            public static final String DELETED = "BOOK_DELETED";
        }
    }

}
