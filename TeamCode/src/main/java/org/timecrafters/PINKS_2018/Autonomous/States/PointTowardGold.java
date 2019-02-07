package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: PointTowardGold
 * Inputs: engine, mineralPosId, FileReader, PinksHardwareConfig
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

import android.util.Log;
import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksDrive;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import java.util.List;

public class PointTowardGold extends State {
    private String StepID = "PointTowardGold";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    public MineralPosId GoldPosIdentifier;
    private VuforiaLocalizer VuForia;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private boolean FirstRun;
    private float GoldPosOnScreen;
    private float TargetPos;
    private float TargetAlowance;
    private PinksDrive Drive;
    private double Power;




    public PointTowardGold(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    public void init() {

        VuForia = PinksHardwareConfig.VuForia;

        ObjectDetector = PinksHardwareConfig.ObjectDetector;

        Drive = new PinksDrive(PinksHardwareConfig);

        TargetPos = AppReader.get(StepID).variable("Mid");
        TargetAlowance = AppReader.get(StepID).variable("Allowance");
        Power = AppReader.get(StepID).variable("Power");

        FirstRun = true;
    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "FileReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            if (FirstRun && !AppReader.allow("MineralPosId")) {
                ObjectDetector.activate();
            }

            Objects = ObjectDetector.getUpdatedRecognitions();


            //Looks at the Recognitions, finds the gold object, and determines it's position on screen
            if (Objects != null) {
                for (Recognition object : Objects) {
                    if (object.getLabel().equals("Gold Mineral")) {
                        GoldPosOnScreen = (object.getBottom() + object.getTop()) / 2;
                    }
                }
            }

            //Rightward values are less than Leftward values

            if (GoldPosOnScreen < TargetPos - TargetAlowance && GoldPosOnScreen != 0) {
                engine.telemetry.addLine("RightSide");

                Drive.RightDrive.setPower(Power);
                Drive.LeftDrive.setPower(0);
                Log.i("PointTowardGold", "Right");
            } else if (GoldPosOnScreen > TargetPos + TargetAlowance || GoldPosOnScreen == 0) {
                engine.telemetry.addLine("LeftSide");

                Drive.LeftDrive.setPower(Power);
                Drive.RightDrive.setPower(0);
                Log.i("PointTowardGold", "Left");
            } else {
                engine.telemetry.addLine("Target");
                Log.i("PointTowardGold", "Target");
                Drive.RightDrive.setPower(0);
                Drive.LeftDrive.setPower(0);
                Drive.reset();

                ObjectDetector.deactivate();
                setFinished(true);
            }

            engine.telemetry.addData("GoldPosOnScreen", GoldPosOnScreen );



        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}