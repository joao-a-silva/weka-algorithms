/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author joaoantoniosilva
 */
public class DateTime {

    private long initTime;
    private long endTime;

    private long timeFold;
    private long totalTime;

    public long getInitialTime() {
        this.initTime = System.currentTimeMillis();
        return initTime;
    }

    public long getEndTime() {
        this.endTime = System.currentTimeMillis();
        return endTime;
    }

    public long getStepTime() {
        this.timeFold += this.endTime - this.initTime;
        return (this.endTime - this.initTime)/1000;
    }

    public long getTimeFold() {
        this.totalTime += timeFold;
        return timeFold/1000;
    }

    public long getTotalTime() {
        return totalTime/1000;
    }

    public void getDate() {
        Calendar c = Calendar.getInstance();
        
               

        System.out.println("Data/Hora atual: " + c.getTime());
    }

    public void initTimeFold() {
        this.timeFold = 0;
    }

}
