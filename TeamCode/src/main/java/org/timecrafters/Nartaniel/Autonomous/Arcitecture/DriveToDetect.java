package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriveToDetect extends State {
    private boolean FirstRun = true;
    public ArchitectureControl Control;


    public DriveToDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() throws InterruptedException {
        if (FirstRun) {
            FirstRun = false;
            if (!Control.RunDropRobot) { stop(); }

        }

    }

    public void stop() {
        engine.telemetry.addLine("Completed DriveToDetect" );
        sleep(1000);
        setFinished(true);
    }
}
