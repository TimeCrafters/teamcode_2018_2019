package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step09CCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step10CCReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.Step11CCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathCenter extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathCenter(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09CCMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10CCReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new Step11CCMineralStrait(engine, AppReader, PinksHardwareConfig));

    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
