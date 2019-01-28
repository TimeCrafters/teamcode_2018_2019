package org.timecrafters.PINKS_2018.Autonomous.States.V2States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

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
    private CRServo MineralCollectionServo;
    private long PlaceTime;
    private double Power;




    public StepPlaceMarker(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

        //We used the robot's mineral collector to store and release the team marker
        MineralCollectionServo = PinksHardwareConfig.pMineralCollectServo;

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