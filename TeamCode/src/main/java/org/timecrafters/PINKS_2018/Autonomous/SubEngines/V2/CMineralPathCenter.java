package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: CMineralPathCenter
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Use: Runs States to complete the Center Path for Crater Side
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/27/19 - added and reorganized States and edited power and distance variables on the phone file.
 * 1/10/19 - fixed issue of every states creating a new instance of PinksHardwareConfig
 * 1/3/19 - Created CMineralPathCenter
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

public class CMineralPathCenter extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration FileReader;

    public CMineralPathCenter(Engine engine, MineralPosId mineralPosId, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    //Works the same as in the Engine: Runs through each State in the order they are added

    @Override
    public void setProcesses() {

        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCMineralBump"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCReturnReverse"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCReturnArc"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCMineralStrait"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCTurnToDepot"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCDriveToDepot"));
        addState(new ExtendArm(engine, FileReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, FileReader, PinksHardwareConfig));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "CCDriveToCrater"));
        addThreadedState(new Paddle(engine, FileReader, PinksHardwareConfig, false));
    }

    //Runs through before setProcesses to determine if the subEngine should be run. If not, it is
    //skipped.
    @Override
    public void evaluate() {

        //If the gold mineral is in the Center position, run this subEngine.
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
