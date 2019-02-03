package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLPointToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Depot.DLPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathLeft extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathLeft(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new DLPointToGold(engine, AppReader, PinksHardwareConfig));
        addState(new DLMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new DLPointToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new DLDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new DLPointToCrater(engine,AppReader, PinksHardwareConfig));
        addState(new DLDriveToCrater(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
