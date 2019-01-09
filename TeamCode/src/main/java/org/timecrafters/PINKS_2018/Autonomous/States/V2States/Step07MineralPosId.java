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
 * Name: DropRobot
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

public class Step07MineralPosId extends State {
    private String StepID = "MineralPosId";
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private VuforiaLocalizer VuForia;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private boolean FirstRun;
    private float GoldLeft;
    private float SilverLeft;
    private float SecondSilverLeft;
    public int GoldPosition;


    public Step07MineralPosId(Engine engine) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
        this.PinksHardwareConfig = new PinksHardwareConfig(engine);
    }

    public void init() {

        //Vuforia initializing
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuForia = ClassFactory.getInstance().createVuforia(parameters);

        //Tensor Flow Object Detection initializing
        int tfodMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        ObjectDetector = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, VuForia);
        ObjectDetector.loadModelFromAsset("RoverRuckus.tflite", "Gold Mineral", "Silver Mineral");

        FirstRun = true;
        GoldLeft = -1;
        SilverLeft = -1;
        SecondSilverLeft = -1;
        GoldPosition = 0;
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        //toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            if (FirstRun) {
                FirstRun = false;
                ObjectDetector.activate();
            }


            Objects = ObjectDetector.getUpdatedRecognitions();

            if (Objects != null) {
                //all three minerals are viable...
                if (Objects.size() == 3) {

                    for (Recognition object : Objects) {
                        if (object.getLabel().equals("Gold Mineral")) {
                            //.getBottom returns a value for where the object is on the screen. We switched
                            //to getBottom from getLeft when we switched to mounting the phone in landscape.
                            GoldLeft = object.getBottom();
                        } else if (SilverLeft == -1) {
                            SilverLeft = object.getBottom();
                        } else {
                            SecondSilverLeft = object.getBottom();
                        }

                        //With the values for the object's positions in view, we determine the position
                        //of the gold mineral.
                        if (GoldLeft < SilverLeft && GoldLeft < SecondSilverLeft) {
                            GoldPosition = 1;
                        } else if (GoldLeft > SilverLeft && GoldLeft > SecondSilverLeft) {
                            GoldPosition = 3;
                        } else {
                            GoldPosition = 2;
                        }

                        if (GoldPosition != 0) {
                            setFinished(true);
                        }
                    }
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
