package com.wyj.mvp.entity.bus;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author wangyujie
 * @date 2018/10/19.14:37
 * @describe 添加描述
 */
@Entity
public class CollectStation {
    @Id(autoincrement = true) // id自增长
    private Long busId;

    @Index(unique = true) // 唯一性
    //站点id_站点名字 比如 2_莘庄
    private String stationId;
    private String stationName;
    private String lineName;
    //true = 1; false = 0
    private String direction;
    private String startStation;
    private String endStation;
    private String lineId;
    private String stopId;
    @Generated(hash = 122365862)
    public CollectStation(Long busId, String stationId, String stationName,
            String lineName, String direction, String startStation,
            String endStation, String lineId, String stopId) {
        this.busId = busId;
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineName = lineName;
        this.direction = direction;
        this.startStation = startStation;
        this.endStation = endStation;
        this.lineId = lineId;
        this.stopId = stopId;
    }
    @Generated(hash = 1395190079)
    public CollectStation() {
    }
    public Long getBusId() {
        return this.busId;
    }
    public void setBusId(Long busId) {
        this.busId = busId;
    }
    public String getStationId() {
        return this.stationId;
    }
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    public String getStationName() {
        return this.stationName;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getLineName() {
        return this.lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public String getDirection() {
        return this.direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getStartStation() {
        return this.startStation;
    }
    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }
    public String getEndStation() {
        return this.endStation;
    }
    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }
    public String getLineId() {
        return this.lineId;
    }
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
    public String getStopId() {
        return this.stopId;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
}
