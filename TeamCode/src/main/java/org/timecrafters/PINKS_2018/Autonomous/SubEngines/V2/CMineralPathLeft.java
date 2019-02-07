package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: CMineralPathLeft
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Use: Runs States to complete the Left Path for Crater Side
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/27/19 - added and reorganized States and edited power and distance variables on the phone file.
 * 1/10/19 - fixed issue of every states creating a new instance of PinksHardwareConfig
 * 1/3/19 - Created CMineralPathLeft
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

public class CMineralPathLeft extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration FileReader;

    public CMineralPathLeft(Engine engine, MineralPosId mineralPosId, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    //Works the same as in the Engine: Runs through each State in the order they are added

    @Override
    public void setProcesses() {

        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLPointToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLDriveToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLTurnToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLMineralBump"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLReturnReverse"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLReturnArc"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLMineralStrait"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLTurnToDepot"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLDriveToDepot"));
        addState(new ExtendArm(engine, FileReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, FileReader, PinksHardwareConfig));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CLDriveToCrater"));
        addThreadedState(new Paddle(engine, FileReader, PinksHardwareConfig, false));
    }

    //Runs through before setProcesses to determine if the subEngine should be run. If not, it is
    //skipped.
    @Override
    public void evaluate() {

        //If the gold mineral is in the Left position, run this subEngine.
        if (GoldPosIdentifier.GoldPosition == 1) {
            setRunable(true);
        }
    }
}
