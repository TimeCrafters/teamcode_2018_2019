package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DriveToPark_fromTMP extends SubEngine {
    public Engine engine;
    public ArchitectureControl Control;

    public DriveToPark_fromTMP(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {

        addState(new CompleteStepIndicator(engine, "Drive To Park (from TMP)", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunDriveToPark_fromTMP);
    }
}
