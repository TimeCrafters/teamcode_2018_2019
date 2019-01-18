package org.timecrafters.PINKS_2018.Autonomous.States.V2States;

import com.qualcomm.robotcore.hardware.CRServo;
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

public class Step06DeployPaddle extends State {
    private String StepID = "DeployPaddle";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private CRServo Paddle;
    private long DeployTime;
    private double Power;




    public Step06DeployPaddle(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {
        //The variables we edit from the phone go here
        DeployTime = AppReader.get(StepID).variable("DeployTime");
        Power = AppReader.get(StepID).variable("Power");

        Paddle = PinksHardwareConfig.pPaddle;

    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            engine.telemetry.addData("DeployTime", DeployTime);
            engine.telemetry.update();

            Paddle.setPower(Power);
            sleep(DeployTime);
            Paddle.setPower(0);

            setFinished(true );

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}
