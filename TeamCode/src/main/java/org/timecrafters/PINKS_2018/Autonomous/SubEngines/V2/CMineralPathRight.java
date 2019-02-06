package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: CMineralPathRight
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Use: Runs States to complete the Right Path for Crater Side
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/27/19 - added and reorganized States and edited power and distance variables on the phone file.
 * 1/10/19 - fixed issue of every states creating a new instance of PinksHardwareConfig
 * 1/3/19 - Created CMineralPathRight
 **********************************************************************************************/

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.Paddle;
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

    //Works the same as in the Engine: Runs through each State in the order they are added

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
        addThreadedState(new Paddle(engine, AppReader, PinksHardwareConfig, false));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CRDriveToCrater"));
    }

    //Runs through before setProcesses to determine if the subEngine should be run. If not, it is
    //skipped.
    @Override
    public void evaluate() {

        //If the gold mineral is in the Right position, run this subEngine.
        if (GoldPosIdentifier.GoldPosition == 3) {
            setRunable(true);
        }
    }
}
