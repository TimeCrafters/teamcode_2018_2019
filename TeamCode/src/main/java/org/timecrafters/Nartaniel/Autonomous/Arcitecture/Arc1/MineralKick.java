package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class MineralKick extends SubEngine {
    public Engine engine;
    public ArchitectureControl Control;

    public MineralKick(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        Control = control;
    }

    @Override
    public void setProcesses() {

        addState(new CompleteStepIndicator_(engine, "Mineral Kick", 1));
    }

    @Override
    public void evaluate() {
        setRunable(Control.RunMineralKick);
    }
}
