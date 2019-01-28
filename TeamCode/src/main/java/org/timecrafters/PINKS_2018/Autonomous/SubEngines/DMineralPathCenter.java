package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.DCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
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
        addState(new DCMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new StepExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new StepPlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new DCReverse(engine, AppReader, PinksHardwareConfig));
        addState(new DCTurn(engine, AppReader, PinksHardwareConfig));
        addState(new DCMineralStrait(engine, AppReader, PinksHardwareConfig));
        addState(new DCPointToCrater(engine, AppReader, PinksHardwareConfig));
        addState(new DCDriveToCrater(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
