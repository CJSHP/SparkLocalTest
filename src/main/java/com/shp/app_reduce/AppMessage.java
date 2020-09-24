package com.shp.app_reduce;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class AppMessage implements Serializable {

    private String appName;
    private int startTime;
    private int endTime;

    public AppMessage(String appName, int startTime, int endTime) {
        this.appName = appName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
