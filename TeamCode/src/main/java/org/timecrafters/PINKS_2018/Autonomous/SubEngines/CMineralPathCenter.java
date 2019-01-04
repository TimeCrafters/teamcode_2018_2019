package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step09CCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step10CCReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step11CCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathCenter extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;

    public CMineralPathCenter(Engine engine, Step07MineralPosId mineralPosId) {
        this.engine = engine;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09CCMineralBump(engine));
        addState(new Step10CCReturnArc(engine));
        addState(new Step11CCMineralStrait(engine));

    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
