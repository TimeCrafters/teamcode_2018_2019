package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: DMineralPathCenter
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Use: Runs States to complete the Center Path
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/26/19 - edited power and distance variables on the phone file
 * 1/24/19 - added and reorganized States to follow a more specific drive path.
 * 1/10/19 - fixed issue of every States creating a new instance of PinksHardwareConfig
 * 1/1/19 - Created DMineralPathCenter
 **********************************************************************************************/

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.States.Drive;
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

    //Works the same as in the Engine: Runs through each State in the order they are added

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
