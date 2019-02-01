package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.Drive;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathCenter extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathCenter(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCMineralBump"));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCReverse"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCTurn"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCMineralStrait"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCPointToCrater"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DCDriveToCrater"));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
