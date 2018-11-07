package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.AutoDepot1;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DropRobot extends SubEngine {
    public Engine engine;
    public AutoDepot1.ArchitectureControl Control;

    public DropRobot(Engine engine, AutoDepot1.ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {


        addState(new CompleteStepIndicator_(engine, "Drop Robot", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunDropRobot);
    }
}
