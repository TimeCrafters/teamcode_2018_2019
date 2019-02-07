package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: MineralPosId
 * Inputs: engine, PinksHardwareConfig, FileReader
 * Outputs: GoldPosition
 * Use: Observe the set of minerals and determine the position of gold as 1, 2 ,or 3 (Left, Center,
 * RightDrive) Using TensorFlow
 * History:
 * 2/2/19 - removed old sample based code and "two minerals present" conditions since the latest
 *  addition works for those conditions
 * 1/20/19 - Made Position Identification work when silver minerals aren't detected
 * 1/19/19 - Made Position Identification work when the leftmost mineral is out of frame
 * 12/27/18 - Used sample code to create basic position identifier
 * 12/15/18 - Tensor Flow Experiment
 * 12/13/18 - Created State
 **********************************************************************************************/

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import java.util.List;

public class MineralPosId_Bak2 extends State {
    private String StepID = "MineralPosId";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private boolean FirstRun;
    private float GoldPosInView;
    private float SilverPosInView;
    private float SilverTwoPosInView;
    private float CRBoundary;
    private float LCBoundary;
    public int GoldPosition;
    private long StartTime;
    private long CurrentTime;
    private long ObserveTime;



    public MineralPosId_Bak2(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

        ObjectDetector = PinksHardwareConfig.ObjectDetector;

        CRBoundary = AppReader.get(StepID).variable("CRB");
        LCBoundary = AppReader.get(StepID).variable("LCB");
        ObserveTime = AppReader.get(StepID).variable("ScanTime");

        FirstRun = true;
        GoldPosInView = -1;
        SilverPosInView = -1;
        SilverTwoPosInView = -1;
        GoldPosition = 0;
    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "FileReader.allow" returns true or false depending on if we have a step
        //toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);
            engine.telemetry.addData("GoldPosInView", GoldPosInView);
            engine.telemetry.addData("SilverPosInView", SilverPosInView);
            engine.telemetry.addData("SilverTwoPosInView", SilverTwoPosInView);


            CurrentTime = System.currentTimeMillis();

            if (FirstRun) {
                FirstRun = false;
                ObjectDetector.activate();
                StartTime = CurrentTime;
            }

            GoldPosition = 0;

            Objects = ObjectDetector.getUpdatedRecognitions();

            if (Objects != null) {
                //all three minerals are viable...
                if (Objects.size() == 3) {

                    for (Recognition object : Objects) {
                        if (object.getLabel().equals("Gold Mineral")) {
                            //.getBottom returns a value for where the object is on the screen. We switched
                            //to getBottom from getLeft when we switched to mounting the phone in landscape.
                            GoldPosInView = object.getBottom();
                        } else if (SilverPosInView == -1) {
                            SilverPosInView = object.getBottom();
                        } else {
                            SilverTwoPosInView = object.getBottom();
                        }

                        //With the values for the object's positions in view, we determine the position
                        //of the gold mineral.
                        if (GoldPosInView < SilverPosInView && GoldPosInView < SilverTwoPosInView) {
                            GoldPosition = 1;
                        } else if (GoldPosInView > SilverPosInView && GoldPosInView > SilverTwoPosInView) {
                            GoldPosition = 3;
                        } else {
                            GoldPosition = 2;
                        }
                    }
                }

                //It's Possible for the leftmost mineral to not fall into the field of veiw, however,
                //we can just extrapolate the gold minerals position based on what's present;
                if (Objects.size() == 2) {

                    for (Recognition object : Objects) {
                        if (object.getLabel().equals("Gold Mineral")) {

                            GoldPosInView = object.getBottom();
                        } else if (SilverPosInView == -1) {
                            SilverPosInView = object.getBottom();
                        } else {
                            //if two silvers are present, the leftmost mineral must be the gold one
                            GoldPosition = 1;
                        }

                        //With the values for the object's positions in view, we determine the position
                        //of the gold mineral. (assuming gold is visible)
                        if (GoldPosInView > SilverPosInView && GoldPosition == 0) {
                            GoldPosition = 2;
                        } else if (GoldPosInView < SilverPosInView && GoldPosition == 0) {
                            GoldPosition = 3;
                        }
                    }
                }

                //Should the Field Conditions render the Silver minerals undetectable, we can look
                //for just the gold mineral and determine it's position based on it's position in
                //veiw.
                if (Objects.size() == 1) {
                    if (Objects.get(0).getLabel().equals("Gold Mineral")) {
                        //.getBottom returns a value for where the object is on the screen. We switched
                        //to getBottom from getLeft when we switched to mounting the phone in landscape.
                        GoldPosInView = (Objects.get(0).getBottom() + Objects.get(0).getTop()) / 2;

                        if (GoldPosInView >= LCBoundary) {
                            GoldPosition = 1;
                        } else if (CRBoundary >= GoldPosInView) {
                            GoldPosition = 3;
                        } else {
                            GoldPosition = 2;
                        }
                    }
                }

                //If the silver minerals aren't being detected and the gold is outside the feild of
                //view, assume it's in the left position.
                if (Objects.isEmpty() && CurrentTime - StartTime > ObserveTime) {
                    GoldPosition = 1;
                }

                if (GoldPosition != 0) {
                    ObjectDetector.deactivate();
                    setFinished(true);
                }

                engine.telemetry.addData("GoldPosition", GoldPosition);

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
