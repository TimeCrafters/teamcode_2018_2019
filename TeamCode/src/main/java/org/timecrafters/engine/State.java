package org.timecrafters.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by t420 on 9/29/2016.
 */


public abstract class State implements Runnable  {

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:ms");
    private Date date = new Date();
    private volatile boolean isFinished = false;
    private byte layer = 0;
    public static String TAG = "PROGRAM.STATE";
    public Engine engine;
    public Cont container;



    public void init(){}

    public abstract void exec() throws InterruptedException;

    @Override
    public void run(){
        while(!isFinished){
            try {
                exec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void telemetry() {
        /*
            Override and place telemetry instructions in here
         */
    }

    public void stop(){
        /*
        * Override this and put your ending crap in here
        * */
    }

    public void setFinished(boolean value){
        isFinished = value;
    }
    public boolean getIsFinished(){return isFinished;}

    public void sleep(long timems){
        try {
            Thread.sleep(timems);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}