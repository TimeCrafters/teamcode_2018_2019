package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Step09DLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Step10DLDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Step11DLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Step12DLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07DMineralPosId;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathLeft extends SubEngine {
    Engine engine;
    private Step07DMineralPosId GoldPosIdentifier;

    public DMineralPathLeft(Engine engine, Step07DMineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DLMineralBump(engine));
        addState(new Step10DLDriveToDepot(engine));
        addState(new Step11DLReturnArc(engine));
        addState(new Step12DLMineralStrait(engine));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
