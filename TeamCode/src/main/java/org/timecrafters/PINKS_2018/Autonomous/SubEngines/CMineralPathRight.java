package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step11CLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Crater.Step09CRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Crater.Step10CRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Crater.Step11CRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathRight extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathRight(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09CRMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10CRReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new Step11CRMineralStrait(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
