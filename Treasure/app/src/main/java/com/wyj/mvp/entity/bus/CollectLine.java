package com.wyj.mvp.entity.bus;


/**
 * @author wangyujie
 * @date 2018/10/19.14:37
 * @describe 添加描述
 */
public class CollectLine extends CollectStation {
    private String distance;
    private String time;
    private String stationId;
    private String stationName;
    private String lineName;
    //true = 1; false = 0
    private String direction;
    private String startStation;
    private String endStation;
    private String lineId;
    private String stopId;
    private int stopdis;

    public int getStopdis() {
        return stopdis;
    }

    public void setStopdis(int stopdis) {
        this.stopdis = stopdis;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStation(CollectStation station) {
        this.stationId = station.getStationId();
        this.stationName = station.getStationName();
        this.lineName = station.getLineName();
        this.stationId = station.getStationId();
        this.direction = station.getDirection();
        this.startStation = station.getStartStation();
        this.endStation = station.getEndStation();
        this.lineId = station.getLineId();
        this.stopId = station.getStopId();
    }

    @Override
    public String getStationId() {
        return stationId;
    }

    @Override
    public String getStationName() {
        return stationName;
    }

    @Override
    public String getLineName() {
        return lineName;
    }

    @Override
    public String getDirection() {
        return direction;
    }

    @Override
    public String getStartStation() {
        return startStation;
    }

    @Override
    public String getEndStation() {
        return endStation;
    }

    @Override
    public String getLineId() {
        return lineId;
    }

    @Override
    public String getStopId() {
        return stopId;
    }
}
