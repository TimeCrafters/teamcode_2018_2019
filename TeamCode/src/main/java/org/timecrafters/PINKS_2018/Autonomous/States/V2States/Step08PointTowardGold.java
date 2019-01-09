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
 * Inputs: engine,
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




    public Step08PointTowardGold(Engine engine, Step07MineralPosId mineralPosId ) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
        this.PinksHardwareConfig = new PinksHardwareConfig(engine);
        this.GoldPosIdentifier = mineralPosId;
    }

    public void init() {

//        //Vuforia initializing
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//        parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        VuForia = ClassFactory.getInstance().createVuforia(parameters);
//
//        //Tensor Flow Object Detection initializing
//        int tfodMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        ObjectDetector = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, VuForia);
//        ObjectDetector.loadModelFromAsset("RoverRuckus.tflite", "Gold Mineral", "Silver Mineral");

        FirstRun = true;
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

//            if (FirstRun) {
//                ObjectDetector.activate();
//            }
//
//            Objects = ObjectDetector.getUpdatedRecognitions();
//
//            if (Objects != null) {
//                for (Recognition object : Objects) {
//                    if (object.getLabel().equals("Gold Mineral")) {
//                        engine.telemetry.addData("getTop", object.getTop());
//                        engine.telemetry.addData("getBottom", object.getBottom());
//                    }
//                }
//            }



        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

}