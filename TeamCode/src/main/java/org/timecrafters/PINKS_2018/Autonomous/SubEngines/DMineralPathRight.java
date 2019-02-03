package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRPointToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Depot.DRReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathRight extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathRight(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new DRPointToGold(engine, AppReader, PinksHardwareConfig));
        addState(new DRMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new DRPointToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new DRDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new DRReturnReverse(engine, AppReader, PinksHardwareConfig));
        addState(new DRReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new DRMineralStrait(engine, AppReader, PinksHardwareConfig));
        addState(new DRPointToCrater(engine, AppReader, PinksHardwareConfig));
        addState(new DRDriveToCrater(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
