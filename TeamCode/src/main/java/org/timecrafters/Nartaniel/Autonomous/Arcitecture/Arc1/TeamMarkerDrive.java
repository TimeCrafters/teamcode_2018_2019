package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class TeamMarkerDrive extends SubEngine {
    public Engine engine;
    public ArchitectureControl Control;

    public TeamMarkerDrive(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {

        addState(new CompleteStepIndicator(engine, "Team Marker Place", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunTeamMarkerDrive);
    }
}
