package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step09DLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step10DLDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step11DLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step12DLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathLeft extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;

    public DMineralPathLeft(Engine engine, Step07MineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DLMineralBump(engine));
        addState(new Step10DLDriveToDepot(engine));
        addState(new StepPlaceMarker(engine));
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
