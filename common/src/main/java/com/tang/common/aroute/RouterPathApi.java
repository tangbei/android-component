package com.tang.common.aroute;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/19
 * Description: java类作用描述
 */
public class RouterPathApi {

    public static class Home {
        private static final String HOME_ROUTER = "/home/";
        public static final String HOME_FIRST = HOME_ROUTER + "home_first";
    }

    public static class Find {
        private static final String FIND_ROUTER = "/find/";
        public static final String FIND_FIRST = FIND_ROUTER + "find_first";
    }

    public static class Live {
        private static final String LIVE_ROUTER = "/live/";
        public static final String LIVE_FIRST = LIVE_ROUTER + "live_first";
    }

    public static class News {
        private static final String NEWS_ROUTER = "/news/";
        public static final String NEWS_FIRST = NEWS_ROUTER + "news_first";
    }

    public static class Mine {
        private static final String MINE_ROUTER = "/mine/";
        public static final String MINE_FIRST = MINE_ROUTER + "mine_first";
    }
}
