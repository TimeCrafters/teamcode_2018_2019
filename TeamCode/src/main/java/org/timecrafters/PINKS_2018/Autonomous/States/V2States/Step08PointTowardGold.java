package org.timecrafters.PINKS_2018.Autonomous.States.V2States;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.List;

/**********************************************************************************************
 * Name: PointTowardGold
 * Inputs: engine, mineralPosId, AppReader, PinksHardwareConfig
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

public class Step08PointTowardGold extends State {
    private String StepID = "PointTowardGold";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    public Step07MineralPosId GoldPosIdentifier;
    private VuforiaLocalizer VuForia;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private boolean FirstRun;
    private float GoldPosOnScreen;
    private float TargetPos;
    private float TargetAlowance;




    public Step08PointTowardGold(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    public void init() {

        VuForia = PinksHardwareConfig.pVuForia;

        ObjectDetector = PinksHardwareConfig.pObjectDetector;

        TargetPos = AppReader.get(StepID).variable("Mid");
        TargetAlowance = AppReader.get(StepID).variable("Allowance");

        FirstRun = true;
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            if (FirstRun) {
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

            if (GoldPosOnScreen < TargetPos - TargetAlowance) {
                engine.telemetry.addLine("LeftSide");
            } else if (GoldPosOnScreen > TargetPos + TargetAlowance) {
                engine.telemetry.addLine("RightSide");
            } else {
                engine.telemetry.addLine("Target");
            }

            engine.telemetry.addData("getBottom", GoldPosOnScreen );



        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}