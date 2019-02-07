package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: Paddle
 * Inputs: engine, appReader, pinksHardwareConfig
 * Outputs: none
 * Use: Rotates down the mineral sampling paddle.
 * History:
 * 1/17/19 - Created Paddle
 **********************************************************************************************/

import com.qualcomm.robotcore.hardware.CRServo;
import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Paddle extends State {
    private String StepID = "DeployPaddle";
    public StateConfiguration FileReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private CRServo Paddle;
    private long DeployTime;
    private double Power;
    private boolean Deploy;





    public Paddle(Engine engine, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig, boolean deploy) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.Deploy = deploy;
    }

    public void init() {
        //The variables we edit from the phone go here
        DeployTime = FileReader.get(StepID).variable("DeployTime");
        Power = FileReader.get(StepID).variable("Power");


        //The Paddle has the phone mounted to the back. when it's deployed, it can by used to both
        //identify the mineral's position using TensorFlow and consistently Sample the mineral.
        Paddle = PinksHardwareConfig.Paddle;

    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "FileReader.allow" returns true or false depending on if we have
        //a step toggled on or off.
        if (FileReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            engine.telemetry.addData("DeployTime", DeployTime);
            engine.telemetry.update();

            //This determines if the paddle is Deploying or Storing.
            if (Deploy) {
                Paddle.setPower(Power);
            } else {
                Paddle.setPower(-Power);
            }

            //Since you can't "getPosition" with CRServos, we just use time and a mechanical stop.
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
