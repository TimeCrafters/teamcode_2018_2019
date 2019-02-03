package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLDriveToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.LeftPath.Crater.CLTurnToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathLeft extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathLeft(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new CLPointToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLDriveToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLTurnToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new CLReturnReverse(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new CLMineralStrait(engine, AppReader, PinksHardwareConfig));
        addState(new CLTurnToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLDriveToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CLDriveToCrater(engine, AppReader, PinksHardwareConfig)); //!
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
