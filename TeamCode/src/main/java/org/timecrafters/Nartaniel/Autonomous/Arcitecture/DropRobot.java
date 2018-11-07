package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DropRobot extends State {
    private boolean FirstRun = true;
    public AutoDepot1.ArchitectureControl Control;


    public DropRobot(Engine engine, AutoDepot1.ArchitectureControl control) {
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
        engine.telemetry.addLine("Completed DropRobot" );
        sleep(1000);
        setFinished(true);
    }
}
