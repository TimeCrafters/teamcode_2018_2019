package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRDriveToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater.CRTurnToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathRight extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathRight(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new CRPointToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CRDriveToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CRTurnToGold(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CRMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new CRReturnReverse(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CRReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new CRMineralStrait(engine, AppReader, PinksHardwareConfig));
        addState(new CRTurnToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CRDriveToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new CRDriveToCrater(engine, AppReader, PinksHardwareConfig)); //!

    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
