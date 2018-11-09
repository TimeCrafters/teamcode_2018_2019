package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DriveToDetect_ extends SubEngine {
    public Engine engine;
    public ArchitectureControl Control;

    public DriveToDetect_(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {

        addState(new CompleteStepIndicator_(engine, "Drive to Detect", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunDriveToDetect);
    }
}
