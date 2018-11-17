package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

public class DriveToDetect extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    VuforiaLocalizer Vuforia;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private List<VuforiaTrackable> AllTargets = new ArrayList<VuforiaTrackable>();
    private boolean targetVisible;

    final int CAMERA_FORWARD_DISPLACEMENT  = 110;   // eg: Camera is 110 mm in front of robot center
    final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
    final int CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

    OpenGLMatrix lastKnownLocation = null;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";


    public DriveToDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        setupVuforia();
    }

    @Override
    public void exec() {
        if (Control.RunDriveToDetect) {
            targetVisible = false;
            for (VuforiaTrackable trackable : AllTargets) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    engine.telemetry.addData("Visible Target", trackable.getName());
                    engine.telemetry.update();
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastKnownLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            if (Complete) {
                engine.telemetry.addLine("Completed DriveToDetect");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }


    public void setupVuforia() {
        int cameraMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = CAMERA_CHOICE;
        Vuforia = ClassFactory.getInstance().createVuforia(parameters);

        visionTargets = Vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = visionTargets.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = visionTargets.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = visionTargets.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = visionTargets.get(3);
        backSpace.setName("Back-Space");

        AllTargets.addAll(visionTargets);

        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        for (VuforiaTrackable trackable : AllTargets)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        visionTargets.activate();

    }
}
