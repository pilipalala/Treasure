package com.wyj.mvp.entity.zhihu;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2018/3/11
 * Time 17:37
 * TODO
 */

public class ZhiHuDaily {

    /**
     * date : 20180311
     * stories : [{"title":"你 DIY 过哪些有意思的东西？","ga_prefix":"031117","images":["https://pic3.zhimg.com/v2-0ff7f57755abc35538ddaeab625734ea.jpg"],"multipic":true,"type":0,"id":9672548},{"images":["https://pic2.zhimg.com/v2-24ef2efbeab1e43eacdf3304ea81ccb5.jpg"],"type":0,"id":9673219,"ga_prefix":"031116","title":"能细腰的平板支撑，一旦做不好就成了「伤腰支撑」"},{"images":["https://pic1.zhimg.com/v2-7ff0eb445a0040a85c96924d4088c740.jpg"],"type":0,"id":9672180,"ga_prefix":"031116","title":"我一个医学生，为什么也要学数理化？"},{"images":["https://pic1.zhimg.com/v2-aa201378666364048746cdb5047b2728.jpg"],"type":0,"id":9672695,"ga_prefix":"031115","title":"上完厕所冲水时，马桶盖到底要不要盖上？"},{"images":["https://pic1.zhimg.com/v2-82301ca8756c83af98bc3429cdd15568.jpg"],"type":0,"id":9672260,"ga_prefix":"031112","title":"大误 · 诶？你们的眼睛怎么回事"},{"images":["https://pic2.zhimg.com/v2-77fff1d84469f98a9a81602dc17885e9.jpg"],"type":0,"id":9672261,"ga_prefix":"031110","title":"别再用烂大街模板了，做 PPT 学好这一招就够了"},{"images":["https://pic2.zhimg.com/v2-838748adbaf6e77874bfe6a2ea9c8a39.jpg"],"type":0,"id":9672973,"ga_prefix":"031109","title":"那些坑爹的书"},{"title":"亲爱的刘看山 · 吉祥物的一天","ga_prefix":"031108","images":["https://pic4.zhimg.com/v2-570b14ab0cbbd70493753826e2311d03.jpg"],"multipic":true,"type":0,"id":9673160},{"images":["https://pic3.zhimg.com/v2-b1b223992aca1989138a8e8e0bdf9ed6.jpg"],"type":0,"id":9673174,"ga_prefix":"031108","title":"本周热门精选 · 学渣变学霸"},{"images":["https://pic3.zhimg.com/v2-4b1d466e82d09ec89dca45e8c4c9a4ca.jpg"],"type":0,"id":9672797,"ga_prefix":"031107","title":"为什么薪水翻了一倍，工作反倒轻松了？"},{"images":["https://pic4.zhimg.com/v2-f6c870a7f7831d1fe72d0699d3ad2267.jpg"],"type":0,"id":9673074,"ga_prefix":"031107","title":"微软为什么不告 WPS Office 侵权？"},{"images":["https://pic2.zhimg.com/v2-d0f615fd707a1155a96776b741ed83cd.jpg"],"type":0,"id":9673175,"ga_prefix":"031106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-772ec9db7c5c21fcf02ec409abaffb3b.jpg","type":0,"id":9673174,"ga_prefix":"031108","title":"本周热门精选 · 学渣变学霸"},{"image":"https://pic1.zhimg.com/v2-c3c320bfb6deb49f6d502d34729d8f2c.jpg","type":0,"id":9672973,"ga_prefix":"031109","title":"那些坑爹的书"},{"image":"https://pic1.zhimg.com/v2-eca616bb4e52b602b648109b8b51a53c.jpg","type":0,"id":9672797,"ga_prefix":"031107","title":"为什么薪水翻了一倍，工作反倒轻松了？"},{"image":"https://pic1.zhimg.com/v2-7db7b4f44fdd41340564fbcbb03ed73c.jpg","type":0,"id":9673160,"ga_prefix":"031108","title":"亲爱的刘看山 · 吉祥物的一天"},{"image":"https://pic3.zhimg.com/v2-2e0dd66b8800b7aaea311ff3c4aaab92.jpg","type":0,"id":9673157,"ga_prefix":"031008","title":"刘爸刘妈的日常 · 中年男人，皮一下很开心"}]
     */

    //日期
    public String date;

    @Override
    public String toString() {
        return "ZhiHuDaily{" +
                "date='" + date + '\'' +
                ", stories=" + stories.size() +
                ", top_stories=" + top_stories.size() +
                '}';
    }

    //当日新闻
    public List<StoriesBean> stories;
    public List<TopStoriesBean> top_stories;

    public static class StoriesBean {
        /**
         * title : 你 DIY 过哪些有意思的东西？
         * ga_prefix : 031117
         * images : ["https://pic3.zhimg.com/v2-0ff7f57755abc35538ddaeab625734ea.jpg"]
         * multipic : true
         * type : 0
         * id : 9672548
         */
        //新闻标题
        public String title;
        //供 Google Analytics 使用
        public String ga_prefix;
        public boolean multipic;
        public int type;
        //url 与 share_url 中最后的数字（应为内容的 id）
        public String id;
        //图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
        public List<String> images;

        @Override
        public String toString() {
            return "StoriesBean{" +
                    "title='" + title + '\'' +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", multipic=" + multipic +
                    ", type=" + type +
                    ", id=" + id +
                    ", images=" + images +
                    '}';
        }
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic4.zhimg.com/v2-772ec9db7c5c21fcf02ec409abaffb3b.jpg
         * type : 0
         * id : 9673174
         * ga_prefix : 031108
         * title : 本周热门精选 · 学渣变学霸
         */

        public String image;
        public int type;
        public String id;
        public String ga_prefix;
        public String title;

        @Override
        public String toString() {
            return "TopStoriesBean{" +
                    "image='" + image + '\'' +
                    ", type=" + type +
                    ", id=" + id +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
