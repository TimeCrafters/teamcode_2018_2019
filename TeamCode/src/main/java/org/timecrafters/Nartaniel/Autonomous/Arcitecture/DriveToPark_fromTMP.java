package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriveToPark_fromTMP extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private long StartTime;
    private boolean FirstRun = true;



    public DriveToPark_fromTMP(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {

    }

    @Override
    public void exec() throws InterruptedException {
        if (FirstRun) {
            FirstRun = false;
            StartTime = System.currentTimeMillis();
        }


        if (1000 >= System.currentTimeMillis() - StartTime ) {
            setFinished(true);
        }



    }

    @Override
    public void telemetry() {
        
        engine.telemetry.addLine("Completed PostDropUTurn");
    }
}
