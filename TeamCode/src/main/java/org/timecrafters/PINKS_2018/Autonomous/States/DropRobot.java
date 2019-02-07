package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine, appReader, pinksHardwareConfig
 * Outputs: none
 * Use: Rotates drop latch servos to release the robot from the lander
 * History:
 * 1/1/19 - Created new DropRobot State for new Autonomous
 **********************************************************************************************/

import com.qualcomm.robotcore.hardware.Servo;
import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DropRobot extends State {
    private String StepID = "DropRobot";
    public StateConfiguration FileReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private Servo servoLeft;
    private Servo servoRight;
    private long DropTime;


    public DropRobot(Engine engine, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {
        //The variables we edit from the phone go here
        DropTime = FileReader.get(StepID).variable("DropTime");


        servoLeft = PinksHardwareConfig.DropLeft;
        servoRight = PinksHardwareConfig.DropRight;

        servoLeft.setPosition(1);
        servoRight.setPosition(0);

    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "FileReader.allow" returns true or false depending on if we have
        //a step toggled on or off.
        if (FileReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            //opens the drop latch servos before closing them shortly after the robot lands.

            servoLeft.setPosition(.6);
            servoRight.setPosition(.5);

            sleep(DropTime);

            servoLeft.setPosition(1);
            servoRight.setPosition(0);

            setFinished(true);

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}
