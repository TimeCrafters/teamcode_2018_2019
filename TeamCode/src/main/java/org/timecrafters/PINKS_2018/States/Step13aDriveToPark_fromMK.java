package org.timecrafters.PINKS_2018.States;


import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step13aDriveToPark_fromMK extends State {
    private boolean Complete = false;
    public Step05ArchitectureControl Control;


    public Step13aDriveToPark_fromMK(Engine engine, Step05ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() {

        if (Control.RunDriveToPark_fromMK) {
            Complete = true;
            if (Complete) {
                engine.telemetry.addLine("Completed Step13aDriveToPark_fromMK");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }


}
