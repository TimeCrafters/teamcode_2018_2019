package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: PlaceMarker
 * Inputs: engine, appReader, pinksHardwareConfig
 * Outputs: none
 * Use: Spins mineral collection wheel to drop team marker.
 * History:
 * 1/24/19 - added functionality to use mineral the collector
 * 1/1/19 - Created State: method of marker placement unknown
 **********************************************************************************************/

import com.qualcomm.robotcore.hardware.CRServo;
import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class PlaceMarker extends State {
    private String StepID = "PlaceMarker";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private CRServo MineralCollectionServo;
    private long PlaceTime;
    private double Power;

    public PlaceMarker(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {
        MineralCollectionServo = PinksHardwareConfig.MineralCollectServo;

        PlaceTime = AppReader.get(StepID).variable("PlaceTime");
        Power = AppReader.get(StepID).variable("Power");
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            //Our Team marker is stored in our mineral collection system and is removed by rotating
            //the collection wheel so it falls out.

            MineralCollectionServo.setPower(Power);
            sleep(PlaceTime);
            MineralCollectionServo.setPower(0);

            setFinished(true);

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}