package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRPointToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRPointToGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.RightPath.Depot.DRReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.Drive;
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

        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DRPointToGold"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "DRMineralBump"));
        addState(new DRPointToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new DRDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new StepExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new StepPlaceMarker(engine, AppReader, PinksHardwareConfig));
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
