package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step09CLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step10CLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Crater.Step11CLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathLeft extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathLeft(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09CLMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10CLReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new Step11CLMineralStrait(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
