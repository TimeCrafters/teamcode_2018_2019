package org.timecrafters.PINKS_2018.States;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step14bDriveToPark_fromTMP extends State {
    private boolean Complete = false;
    public Step05ArchitectureControl Control;




    public Step14bDriveToPark_fromTMP(Engine engine, Step05ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() {
        if (Control.RunDriveToPark_fromTMP) {
            Complete = true;
            if (Complete) {
                engine.telemetry.addLine("Completed Step14bDriveToPark_fromTMP");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

}
