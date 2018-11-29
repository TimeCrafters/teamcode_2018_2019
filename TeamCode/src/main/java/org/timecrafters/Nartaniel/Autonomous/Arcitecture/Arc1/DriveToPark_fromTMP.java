package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriveToPark_fromTMP extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;




    public DriveToPark_fromTMP(Engine engine, ArchitectureControl control) {
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
                engine.telemetry.addLine("Completed Step14Park");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

}
