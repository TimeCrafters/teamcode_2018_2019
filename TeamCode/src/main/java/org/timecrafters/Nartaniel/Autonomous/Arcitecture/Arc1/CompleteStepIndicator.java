package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class CompleteStepIndicator extends State {
    private String Step;
    private long StartTime;
    private long PauseTime;
    private boolean FirstRun;

    public CompleteStepIndicator(Engine engine, String step, long pauseTime) {
        this.Step = step;
        this.engine = engine;
        this.PauseTime = pauseTime;

    }

    @Override
    public void exec() throws InterruptedException {
        if (FirstRun) {
            StartTime = System.currentTimeMillis();
            FirstRun = false;
        }

        if (System.currentTimeMillis() - StartTime >= 1000 * PauseTime) {
            setFinished(true);
        }


    }

    public void telemetry() {
        engine.telemetry.addData("Completed", Step);
    }
}
