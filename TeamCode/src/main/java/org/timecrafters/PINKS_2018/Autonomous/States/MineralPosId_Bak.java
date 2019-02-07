package org.timecrafters.PINKS_2018.Autonomous.States;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.List;

/**********************************************************************************************
 * Name: MineralPosId
 * Inputs: engine, PinksHardwareConfig, FileReader
 * Outputs: GoldPosition
 * Use: Observe the set of minerals and determine the position of gold as 1, 2 ,or 3 (Left, Center,
 * RightDrive)
 **********************************************************************************************/

public class MineralPosId_Bak extends State {
    private String StepID = "MineralPosId";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private VuforiaLocalizer VuForia;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private boolean FirstRun;
    private float GoldPosOnScreen;
    private float SilverPosOnScreen;
    private float SecondSilverPosOnScreen;
    public int GoldPosition;


    public MineralPosId_Bak(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

        VuForia = PinksHardwareConfig.VuForia;

        ObjectDetector = PinksHardwareConfig.ObjectDetector;

        FirstRun = true;
        GoldPosOnScreen = -1;
        SilverPosOnScreen = -1;
        SecondSilverPosOnScreen = -1;
        GoldPosition = 0;
    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "FileReader.allow" returns true or false depending on if we have a step
        //toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            if (FirstRun) {
                FirstRun = false;
                ObjectDetector.activate();
            }


            Objects = ObjectDetector.getUpdatedRecognitions();

            if (Objects != null) {


                for (Recognition object : Objects) {
                    if (object.getLabel().equals("Gold Mineral")) {
                        //.getBottom returns a value for where the object is on the screen. We switched
                        //to getBottom from getLeft when we switched to mounting the phone in landscape.
                        GoldPosOnScreen = (object.getBottom() + object.getTop()) / 2;
                    }
                }





                if (GoldPosition != 0) {
                    setFinished(true);
                }

            }

        } else {
            //since the rest of the program depends on the decision of this step, if the step is
            //toggled off, we run whatever path we set on the phone.
            GoldPosition = AppReader.get(StepID).variable("DefaultPath");
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}
