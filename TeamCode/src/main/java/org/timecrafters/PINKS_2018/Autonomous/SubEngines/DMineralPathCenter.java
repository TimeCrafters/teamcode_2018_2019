package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step09DCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step10DCDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step11DCReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step12DCTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step13DCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathCenter extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;

    public DMineralPathCenter(Engine engine, Step07MineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DCMineralBump(engine));
        addState(new Step10DCDriveToDepot(engine));
        addState(new StepPlaceMarker(engine));
        addState(new Step11DCReverse(engine));
        addState(new Step12DCTurn(engine));
        addState(new Step13DCMineralStrait(engine));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
