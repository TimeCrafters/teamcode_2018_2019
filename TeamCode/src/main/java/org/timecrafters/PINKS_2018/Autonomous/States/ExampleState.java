package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: ExampleState
 * Inputs: engine, appReader, pinksHardwareConfig
 * Outputs: none
 * Use: This is an example
 **********************************************************************************************/

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ExampleState extends State {
    private String StepID = "UniqueName";
    public StateConfiguration FileReader;
    public PinksHardwareConfig PinksHardwareConfig;


    public ExampleState(Engine engine, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "FileReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (FileReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            //

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}