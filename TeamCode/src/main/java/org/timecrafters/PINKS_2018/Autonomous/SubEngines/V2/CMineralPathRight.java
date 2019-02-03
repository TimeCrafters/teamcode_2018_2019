package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.States.Drive;
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

        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRPointToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRDriveToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRTurnToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRMineralBump"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRReturnReverse"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRReturnArc"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRMineralStrait"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRTurnToDepot"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRDriveToDepot"));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRDriveToCrater"));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
