package org.timecrafters.PINKS_2018.Autonomous.States;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step10MineralKick extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;


    public Step10MineralKick(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() {
        if (Control.RunMineralKick) {
            Complete = true;
            if (Complete) {
                engine.telemetry.addLine("Completed Step10MineralKick");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }


}
