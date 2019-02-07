package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.RightPath.Crater;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksDrive;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

public class CRTurnToGold extends State {
    private String StepID = "CRTurnToGold";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private PinksDrive Drive;
    private double LeftPower;
    private double RightPower;
    private int LeftInches;
    private int RightInches;


    public CRTurnToGold(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {
        Drive = new PinksDrive(PinksHardwareConfig);

        LeftPower = AppReader.get(StepID).variable("LeftPower");
        RightPower = AppReader.get(StepID).variable("RightPower");
        LeftInches = AppReader.get(StepID).variable("LeftIN");
        RightInches = AppReader.get(StepID).variable("RightIN");
    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "FileReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            Drive.go(LeftPower, RightPower, LeftInches, RightInches);

            setFinished(Drive.HasReachedTarget());

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}