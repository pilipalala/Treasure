package com.wyj.mvp.entity.bus;


import java.util.List;

public class LineStationInfo {
    @Override
    public String toString() {
        return "LineStationInfo{" +
                "lineResults0=" + lineResults0.toString() +
                ", lineResults1=" + lineResults1.toString() +
                '}';
    }

    /**
     * lineResults0 : {"direction":"true","stops":[{"zdmc":"莘庄地铁站(北广场)","id":"1"},{"zdmc":"莘庄","id":"2"},{"zdmc":"七莘路莘北路","id":"3"},{"zdmc":"七莘路疏影路","id":"4"},{"zdmc":"七莘路黎安路","id":"5"},{"zdmc":"七莘路顾戴路","id":"6"},{"zdmc":"七莘路华中路","id":"7"},{"zdmc":"七莘路华友路","id":"8"},{"zdmc":"七莘路中谊路","id":"9"},{"zdmc":"七莘路华茂路","id":"10"},{"zdmc":"七莘路富强街","id":"11"},{"zdmc":"七宝","id":"12"},{"zdmc":"七莘路星站路","id":"13"},{"zdmc":"七莘路沪青平公路","id":"14"},{"zdmc":"七莘路温虹路","id":"15"},{"zdmc":"润虹路七莘路","id":"16"},{"zdmc":"宁虹路申滨路","id":"17"},{"zdmc":"申滨路天山西路","id":"18"},{"zdmc":"天山西路华翔路","id":"19"},{"zdmc":"诸翟","id":"20"},{"zdmc":"纪翟路保乐路","id":"21"},{"zdmc":"纪翟路北青公路","id":"22"},{"zdmc":"朱家泾","id":"23"},{"zdmc":"纪翟路红卫路","id":"24"},{"zdmc":"纪翟路纪梅路","id":"25"},{"zdmc":"纪王","id":"26"}]}
     * lineResults1 : {"direction":"false","stops":[{"zdmc":"纪王","id":"1"},{"zdmc":"纪翟路纪梅路","id":"2"},{"zdmc":"纪翟路红卫路","id":"3"},{"zdmc":"朱家泾","id":"4"},{"zdmc":"纪翟路北青公路","id":"5"},{"zdmc":"纪翟路保乐路","id":"6"},{"zdmc":"诸翟","id":"7"},{"zdmc":"天山西路华翔路","id":"8"},{"zdmc":"申滨路天山西路","id":"9"},{"zdmc":"宁虹路申滨路","id":"10"},{"zdmc":"申昆路润虹路","id":"11"},{"zdmc":"七莘路温虹路","id":"12"},{"zdmc":"七莘路沪青平公路","id":"13"},{"zdmc":"七莘路星站路","id":"14"},{"zdmc":"七宝","id":"15"},{"zdmc":"七莘路富强街","id":"16"},{"zdmc":"七莘路华茂路","id":"17"},{"zdmc":"七莘路中谊路","id":"18"},{"zdmc":"七莘路华友路","id":"19"},{"zdmc":"七莘路华中路","id":"20"},{"zdmc":"七莘路顾戴路","id":"21"},{"zdmc":"七莘路黎安路","id":"22"},{"zdmc":"七莘路疏影路","id":"23"},{"zdmc":"七莘路莘北路","id":"24"},{"zdmc":"莘庄","id":"25"},{"zdmc":"莘庄地铁站(北广场)","id":"26"}]}
     */

    private LineResultsBean lineResults0;
    private LineResultsBean lineResults1;

    public LineResultsBean getLineResults0() {
        return lineResults0;
    }

    public void setLineResults0(LineResultsBean lineResults0) {
        this.lineResults0 = lineResults0;
    }

    public LineResultsBean getLineResults1() {
        return lineResults1;
    }

    public void setLineResults1(LineResultsBean lineResults1) {
        this.lineResults1 = lineResults1;
    }

    public static class LineResultsBean {
        @Override
        public String toString() {
            return "LineResultsBean{" +
                    "direction=" + direction +
                    ", stops=" + stops.size() +
                    '}';
        }

        /**
         * direction : true
         * stops : [{"zdmc":"莘庄地铁站(北广场)","id":"1"},{"zdmc":"莘庄","id":"2"},{"zdmc":"七莘路莘北路","id":"3"},{"zdmc":"七莘路疏影路","id":"4"},{"zdmc":"七莘路黎安路","id":"5"},{"zdmc":"七莘路顾戴路","id":"6"},{"zdmc":"七莘路华中路","id":"7"},{"zdmc":"七莘路华友路","id":"8"},{"zdmc":"七莘路中谊路","id":"9"},{"zdmc":"七莘路华茂路","id":"10"},{"zdmc":"七莘路富强街","id":"11"},{"zdmc":"七宝","id":"12"},{"zdmc":"七莘路星站路","id":"13"},{"zdmc":"七莘路沪青平公路","id":"14"},{"zdmc":"七莘路温虹路","id":"15"},{"zdmc":"润虹路七莘路","id":"16"},{"zdmc":"宁虹路申滨路","id":"17"},{"zdmc":"申滨路天山西路","id":"18"},{"zdmc":"天山西路华翔路","id":"19"},{"zdmc":"诸翟","id":"20"},{"zdmc":"纪翟路保乐路","id":"21"},{"zdmc":"纪翟路北青公路","id":"22"},{"zdmc":"朱家泾","id":"23"},{"zdmc":"纪翟路红卫路","id":"24"},{"zdmc":"纪翟路纪梅路","id":"25"},{"zdmc":"纪王","id":"26"}]
         */

        private boolean direction;
        private List<StationInfo> stops;

        public boolean getDirection() {
            return direction;
        }

        public void setDirection(boolean direction) {
            this.direction = direction;
        }

        public List<StationInfo> getStops() {
            return stops;
        }

        public void setStops(List<StationInfo> stops) {
            this.stops = stops;
        }


    }


}
