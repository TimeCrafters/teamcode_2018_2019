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

public class MineralPosId extends State {
    private String StepID = "MineralPosId";
    public StateConfiguration FileReader;
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



    public MineralPosId(Engine engine, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
    }

    public void init() {

        ObjectDetector = PinksHardwareConfig.ObjectDetector;

        CRBoundary = FileReader.get(StepID).variable("CRB");
        LCBoundary = FileReader.get(StepID).variable("LCB");
        ObserveTime = FileReader.get(StepID).variable("ScanTime");

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
        if (FileReader.allow(StepID)) {
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

            Objects = ObjectDetector.getUpdatedRecognitions();

            if (Objects != null) {

                //First find the gold mineral. This version ignores silver minerals since they
                //aren't reliably recognised and not nessesary in determining the position of the
                //Gold mineral.
                for (Recognition object : Objects) {
                    if (object.getLabel().equals("Gold Mineral")) {
                        //.getBottom returns a value for where the object is on the screen. We
                        //switched to getBottom from getLeft when we switched to mounting the phone
                        //in landscape. This math finds the middle of the object, eliminating the
                        //effect of variations in the perceived width of the mineral.
                        GoldPosInView = (object.getBottom() + object.getTop()) / 2;

                        //If The Gold Mineral is found and it's position in view determined, find
                        //what range it falls into to determine if it is at Left, Center, or Right.
                        if (GoldPosInView >= LCBoundary) {
                            //LC = Left-Center Boundary
                            //Position 1 corresponds to the Left Position
                            GoldPosition = 1;
                        } else if (CRBoundary >= GoldPosInView) {
                            //CR = Center-Right Boundary
                            //Position 3 corresponds to the Right Position
                            GoldPosition = 3;
                        } else {
                            //Position 2 corresponds to the Center Position
                            GoldPosition = 2;
                        }
                    }
                }

                //When the Robot Drops, the camera tends to only see the Center and Right Minerals.
                //This means that if the gold mineral hasn't been found after a sufficient time, It
                //can be safely determined to be in the Left position.
                if (GoldPosition == 0 && CurrentTime - StartTime > ObserveTime) {
                    GoldPosition = 1;
                }

                //When the position of gold is determined, deactivate Tensor Flow and finish the
                //State
                if (GoldPosition != 0) {
                    ObjectDetector.deactivate();
                    setFinished(true);
                }

                engine.telemetry.addData("GoldPosition", GoldPosition);

            }

        } else {
            //since the rest of the program depends on the decision of this step, if the step is
            //toggled off, we run whatever path we set on the phone.
            GoldPosition = FileReader.get(StepID).variable("DefaultPath");
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}
