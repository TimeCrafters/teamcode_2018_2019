package org.timecrafters.PINKS_2018.Autonomous.States.V2States;

import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine
 * Outputs: none
 * Use:
 **********************************************************************************************/

public class Step06DropRobot extends State {
    private String StepID = "DropRobot";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private Servo servoLeft;
    private Servo servoRight;
    private Servo Paddle;
    private long WaitTime;
    private int PadDeployPos;



    public Step06DropRobot(Engine engine) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
        this.PinksHardwareConfig = new PinksHardwareConfig(engine);
    }

    public void init() {
        servoLeft = PinksHardwareConfig.pDropLeft;
        servoRight = PinksHardwareConfig.pDropRight;
        Paddle = PinksHardwareConfig.pPaddle;

        //The variables we edit from the phone go here
        WaitTime = AppReader.get(StepID).variable("WaitTime");
        PadDeployPos = AppReader.get(StepID).variable("DeployPos");

        servoLeft.setPosition(1);
        servoRight.setPosition(0);
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            //This is the actual code for Dropping the Robot. It opens the drop latch servos before
            //closing them shortly after the robot lands.
            servoLeft.setPosition(.6);
            servoRight.setPosition(1);
            sleep(WaitTime);

            Paddle.setPosition(PadDeployPos);
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
