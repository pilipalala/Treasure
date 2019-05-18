package com.wyj.live;

import java.util.List;

/**
 * @author wangyujie
 * @date 2019/5/10.19:08
 * @describe 添加描述
 */
public class LivePingTai {

    private List<PingtaiBean> pingtai;

    public List<PingtaiBean> getPingtai() {
        return pingtai;
    }

    public void setPingtai(List<PingtaiBean> pingtai) {
        this.pingtai = pingtai;
    }

    public static class PingtaiBean {
        /**
         * address : jsonxingguang.txt
         * xinimg : http://ww1.sinaimg.cn/large/87c01ec7gy1fqi47x1heoj2020020748.jpg
         * Number : 22
         * title : 星光
         */

        private String address;
        private String xinimg;
        private String Number;
        private String title;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getXinimg() {
            return xinimg;
        }

        public void setXinimg(String xinimg) {
            this.xinimg = xinimg;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
