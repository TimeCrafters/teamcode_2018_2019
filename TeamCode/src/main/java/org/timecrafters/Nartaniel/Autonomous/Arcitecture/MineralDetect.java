package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class MineralDetect extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;


    public MineralDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() throws InterruptedException {
        if (Complete) {
            sleep(1000);
            setFinished(true);
        }



    }
    public void telemetry() {
        if (!Control.RunDropRobot) {
            engine.telemetry.addLine("Completed PostDropUTurn");
            Complete = true;
        }

    }


}
