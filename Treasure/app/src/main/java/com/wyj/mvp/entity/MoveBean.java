package com.wyj.mvp.entity;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2018/3/11
 * Time 17:31
 * TODO
 */
//http://api.douban.com/v2/movie/top250?start=0&count=10
public class MoveBean {


    public int count;
    public int start;
    public int total;
    public String title;
    public List<SubjectsBean> subjects;

    public static class SubjectsBean {

        public RatingBean rating;
        public String title;
        public int collect_count;
        public String original_title;
        public String subtype;
        public String year;
        public ImagesBean images;
        public String alt;
        public String id;
        public List<String> genres;
        public List<CastsBean> casts;
        public List<DirectorsBean> directors;

        public static class RatingBean {

            public int max;
            public double average;
            public String stars;
            public int min;
        }

        public static class ImagesBean {


            public String small;
            public String large;
            public String medium;
        }

        public static class CastsBean {
            public String alt;
            public AvatarsBean avatars;
            public String name;
            public String id;

            public static class AvatarsBean {

                public String small;
                public String large;
                public String medium;
            }
        }

        public static class DirectorsBean {


            public String alt;
            public AvatarsBeanX avatars;
            public String name;
            public String id;

            public static class AvatarsBeanX {


                public String small;
                public String large;
                public String medium;
            }
        }
    }
}
