package org.timecrafters.PINKS_2018.Autonomous.States;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step13OptionBDriveToPark_fromTMP extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;




    public Step13OptionBDriveToPark_fromTMP(Engine engine, ArchitectureControl control) {
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
                engine.telemetry.addLine("Completed Step13OptionBDriveToPark_fromTMP");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

}
