package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc2;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DriveToPark_fromTMP_ extends SubEngine {
    Engine engine;
    public ArchitectureControl Control;

    public DriveToPark_fromTMP_(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {

        addState(new CompleteStepIndicator_(engine, "PinksDrive To Park (from TMP)", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunDriveToPark_fromTMP);
    }
}
