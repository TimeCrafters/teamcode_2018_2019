package org.timecrafters.PINKS_2018.States;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step11TeamMarkerDrive extends State {
    private boolean Complete = false;
    public Step05ArchitectureControl Control;


    public Step11TeamMarkerDrive(Engine engine, Step05ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() {
        if (Control.RunTeamMarkerDrive) {
            Complete = true;
            if (Complete) {
                engine.telemetry.addLine("Completed Step11TeamMarkerDrive");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }


}
