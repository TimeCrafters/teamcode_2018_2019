package org.timecrafters.PINKS_2018.Autonomous.States;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.List;

public class Step09MineralDetectV2 extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private VuforiaLocalizer VuForia;
    private TFObjectDetector ObjectDetector;
    private List<Recognition> Objects;
    private Recognition Object;
    private boolean FirstRun;

    public Step09MineralDetectV2(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
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
    }

    @Override
    public void exec() {
        if (Control.RunMineralDetect) {
            if (FirstRun) {
                FirstRun = false;
                ObjectDetector.activate();
            }

            Objects = ObjectDetector.getUpdatedRecognitions();

            if (Objects != null) {
                engine.telemetry.addLine("HAZAH!");
            }



            if (Complete) {
                engine.telemetry.addLine("Completed Step09MineralDetect");
                engine.telemetry.update();
                Complete = false;
                ObjectDetector.shutdown();
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addLine("Running Scan");
        if (Object != null) {
            engine.telemetry.addData("Object", Object.getLeft());
        }
    }
}
