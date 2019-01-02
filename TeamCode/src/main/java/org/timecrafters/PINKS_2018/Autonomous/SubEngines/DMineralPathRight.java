package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Step09DRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Step10DRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Step11DRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Step12DRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07DMineralPosId;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathRight extends SubEngine {
    Engine engine;
    private Step07DMineralPosId GoldPosIdentifier;

    public DMineralPathRight(Engine engine, Step07DMineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DRMineralBump(engine));
        addState(new Step10DRDriveToDepot(engine));
        addState(new Step11DRReturnArc(engine));
        addState(new Step12DRMineralStrait(engine));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
