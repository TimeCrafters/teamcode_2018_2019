package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step09DLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step10DLDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step11DLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.LeftPath.Depot.Step12DLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathLeft extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathLeft(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DLMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10DLDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new StepPlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Step11DLReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new Step12DLMineralStrait(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
