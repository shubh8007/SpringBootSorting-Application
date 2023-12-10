// ResponsePayload.java
package com.app.Controller;

public class ResponseDTO {
    private int[][] sortedArrays;
    private long timeNs;

    public ResponseDTO(int[][] sortedArrays, long timeNs) {
        this.sortedArrays = sortedArrays;
        this.timeNs = timeNs;
    }

    public int[][] getSortedArrays() {
        return sortedArrays;
    }

    public void setSortedArrays(int[][] sortedArrays) {
        this.sortedArrays = sortedArrays;
    }

    public long getTimeNs() {
        return timeNs;
    }

    public void setTimeNs(long timeNs) {
        this.timeNs = timeNs;
    }
}
