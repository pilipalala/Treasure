package com.wyj.mvp.entity.bus;

import java.util.List;

public class CarsInfo {
    @Override
    public String toString() {
        return "CarsInfo{" +
                "cars=" + cars.get(0).toString() +
                '}';
    }

    private List<CarInfo> cars;

    public List<CarInfo> getCars() {
        return cars;
    }

    public void setCars(List<CarInfo> cars) {
        this.cars = cars;
    }

    public static class CarInfo {
        @Override
        public String toString() {
            return "CarInfo{" +
                    "terminal='" + terminal + '\'' +
                    ", stopdis=" + stopdis +
                    ", distance='" + distance + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        /**
         * terminal : 沪B-87145
         * stopdis : 1
         * distance : 1061
         * time : 166
         */

        private String terminal;
        private int stopdis;
        private String distance;
        private String time;

        public String getTerminal() {
            return terminal;
        }

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

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
    }
}
