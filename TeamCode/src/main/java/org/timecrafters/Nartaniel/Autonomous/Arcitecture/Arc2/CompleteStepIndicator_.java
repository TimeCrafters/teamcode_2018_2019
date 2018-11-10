package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc2;

import android.util.Log;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class CompleteStepIndicator_ extends State {
    private String Step;
    private long StartTime;
    private long PauseTime;
    private boolean FirstRun = true;

    public CompleteStepIndicator_(Engine engine, String step, long pauseTime) {
        this.Step = step;
        this.engine = engine;
        this.PauseTime = pauseTime;

    }




    public void exec() {

        if (FirstRun) {
            FirstRun = false;
            StartTime = System.currentTimeMillis();
        }


        if (System.currentTimeMillis() - StartTime >= 1000 * PauseTime) {
            setFinished(true);
        }


    }

    public void telemetry() {
        engine.telemetry.addData("Completed", Step);
        engine.telemetry.addData("time", (System.currentTimeMillis() - StartTime));
        engine.telemetry.addData("Start Time", StartTime);
        engine.telemetry.addData("FirstRun", FirstRun);

    }
}
