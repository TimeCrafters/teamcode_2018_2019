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
    private StateConfiguration FileReader;

    public CMineralPathRight(Engine engine, MineralPosId mineralPosId, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    //Works the same as in the Engine: Runs through each State in the order they are added

    @Override
    public void setProcesses() {

        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRPointToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRDriveToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRTurnToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRMineralBump"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRReturnReverse"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRReturnArc"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRMineralStrait"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRTurnToDepot"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRDriveToDepot"));
        addState(new ExtendArm(engine, FileReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, FileReader, PinksHardwareConfig));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CRDriveToCrater"));
        addThreadedState(new Paddle(engine, FileReader, PinksHardwareConfig, false));
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
