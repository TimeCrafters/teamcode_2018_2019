package org.timecrafters.PINKS_2018.Autonomous.States.V2States;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

public class StepPlaceMarker extends State {
    private String StepID = "PlaceMarker";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;



    public StepPlaceMarker(Engine engine) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
        this.PinksHardwareConfig = new PinksHardwareConfig(engine);
    }

    public void init() {

    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {

            engine.telemetry.addLine("Running Step"+StepID);
            sleep(1000);
            setFinished(true);

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}