package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: Extends Mineral collection arm so that t
 * History:
 * 1/24/19 - Created ExtendArm
 **********************************************************************************************/

import com.qualcomm.robotcore.hardware.CRServo;
import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ExtendArm extends State {
    private String StepID = "ExtendArm";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private CRServo ElbowServo;
    private long ExtendTime;
    private double Power;




    public ExtendArm(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

        ElbowServo = PinksHardwareConfig.ElbowServo;

        ExtendTime = AppReader.get(StepID).variable("ExtendTime");
        Power = AppReader.get(StepID).variable("Power");
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            //Since you can't "getPosition" with CRServos, we just use time and an Elbow locking
            //mechanism.
            ElbowServo.setPower(Power);
            sleep(ExtendTime);
            ElbowServo.setPower(0);

            setFinished(true);

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}