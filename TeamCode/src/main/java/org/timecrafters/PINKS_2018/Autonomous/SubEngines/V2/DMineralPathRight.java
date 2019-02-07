package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: DMineralPathRight
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Use: Runs States to complete the Right Path
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/26/19 - edited power and distance variables on the phone file
 * 1/24/19 - added and reorganized States to follow a more specific drive path.
 * 1/10/19 - fixed issue of every States creating a new instance of PinksHardwareConfig
 * 1/1/19 - Created DMineralPathRight
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

public class DMineralPathRight extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration FileReader;

    public DMineralPathRight(Engine engine, MineralPosId mineralPosId, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    //Works the same as in the Engine: Runs through each State in the order they are added
    @Override
    public void setProcesses() {

        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRPointToGold"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRMineralBump"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRPointToDepot"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRDriveToDepot"));
        addState(new ExtendArm(engine, FileReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, FileReader, PinksHardwareConfig));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRReturnReverse"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRReturnArc"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRMineralStrait"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRPointToCrater"));
        addState(new Drive(engine, FileReader, PinksHardwareConfig, "DRDriveToCrater"));
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
