package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step09DRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step10DRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step11DRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.Step12DRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathRight extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathRight(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DRMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10DRDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new StepPlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Step11DRReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new Step12DRMineralStrait(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
