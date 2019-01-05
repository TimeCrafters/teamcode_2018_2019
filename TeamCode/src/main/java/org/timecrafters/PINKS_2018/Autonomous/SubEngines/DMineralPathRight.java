package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step09DRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step10DRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step11DRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step12DRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathRight extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;

    public DMineralPathRight(Engine engine, Step07MineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DRMineralBump(engine));
        addState(new Step10DRDriveToDepot(engine));
        addState(new StepPlaceMarker(engine));
        addState(new Step11DRReturnArc(engine));
        addState(new Step12DRMineralStrait(engine));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
