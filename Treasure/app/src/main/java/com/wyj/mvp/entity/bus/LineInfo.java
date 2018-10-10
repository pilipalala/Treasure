package com.wyj.mvp.entity.bus;

/**
 * @author wangyujie
 * @date 2018/9/26.9:48
 * @describe http://xxbs.sh.gov.cn:8080/weixinpage/HandlerBus.ashx?action=One&name=189%E8%B7%AF
 *
 */
public class LineInfo {
    @Override
    public String toString() {
        return "LineInfo{" +
                "end_earlytime='" + end_earlytime + '\'' +
                ", end_latetime='" + end_latetime + '\'' +
                ", end_stop='" + end_stop + '\'' +
                ", line_id='" + line_id + '\'' +
                ", line_name='" + line_name + '\'' +
                ", start_earlytime='" + start_earlytime + '\'' +
                ", start_latetime='" + start_latetime + '\'' +
                ", start_stop='" + start_stop + '\'' +
                '}';
    }

    /**
     * end_earlytime : 05:45
     * end_latetime : 23:00
     * end_stop : 纪王
     * line_id : 018900
     * line_name : 189
     * start_earlytime : 06:15
     * start_latetime : 23:50
     * start_stop : 莘庄地铁站(北广场)
     */

    private String end_earlytime;
    private String end_latetime;
    private String end_stop;
    private String line_id;
    private String line_name;
    private String start_earlytime;
    private String start_latetime;
    private String start_stop;

    public String getEnd_earlytime() {
        return end_earlytime;
    }

    public void setEnd_earlytime(String end_earlytime) {
        this.end_earlytime = end_earlytime;
    }

    public String getEnd_latetime() {
        return end_latetime;
    }

    public void setEnd_latetime(String end_latetime) {
        this.end_latetime = end_latetime;
    }

    public String getEnd_stop() {
        return end_stop;
    }

    public void setEnd_stop(String end_stop) {
        this.end_stop = end_stop;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getStart_earlytime() {
        return start_earlytime;
    }

    public void setStart_earlytime(String start_earlytime) {
        this.start_earlytime = start_earlytime;
    }

    public String getStart_latetime() {
        return start_latetime;
    }

    public void setStart_latetime(String start_latetime) {
        this.start_latetime = start_latetime;
    }

    public String getStart_stop() {
        return start_stop;
    }

    public void setStart_stop(String start_stop) {
        this.start_stop = start_stop;
    }
}
