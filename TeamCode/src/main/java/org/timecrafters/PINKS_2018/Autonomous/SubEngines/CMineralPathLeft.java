package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step09CLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step10CLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step11CLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathLeft extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;

    public CMineralPathLeft(Engine engine, Step07MineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09CLMineralBump(engine));
        addState(new Step10CLReturnArc(engine));
        addState(new Step11CLMineralStrait(engine));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
