package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.States.Drive;
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

        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLPointToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLDriveToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLTurnToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLMineralBump"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLReturnReverse"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLReturnArc"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLMineralStrait"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLTurnToDepot"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLDriveToDepot"));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CLDriveToCrater"));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
