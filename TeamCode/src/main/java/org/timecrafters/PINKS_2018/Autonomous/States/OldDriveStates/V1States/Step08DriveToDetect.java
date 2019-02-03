package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
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

/**********************************************************************************************
 * Name: DriveToDetect
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: none
 **********************************************************************************************/

public class Step08DriveToDetect extends State {
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



    //Originally we planned on useing VuForia to aline the robot's position on the feild. However,
    //we couldn't get the phone to decern images more that two feet away.






    int CAMERA_FORWARD_DISPLACEMENT;  // eg: Camera is 110 mm in front of robot center
    int CAMERA_VERTICAL_DISPLACEMENT; // eg: Camera is 200 mm above ground
    int CAMERA_LEFT_DISPLACEMENT; // eg: Camera is ON the robot's center line

    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor


    OpenGLMatrix lastKnownLocation = null;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";


    public Step08DriveToDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
        CAMERA_FORWARD_DISPLACEMENT  = Control.AppReader.get("RunDriveToDetect").variable("CamraForward");
        CAMERA_VERTICAL_DISPLACEMENT = Control.AppReader.get("RunDriveToDetect").variable("CamraVertical");
        CAMERA_LEFT_DISPLACEMENT     = Control.AppReader.get("RunDriveToDetect").variable("CamraLeft");
    }


    public void init() {
        setupVuforia();
    }

    @Override
    public void exec() {
        if (Control.RunDriveToDetect) {
            boolean LocaltargetVisible = false;

            for (VuforiaTrackable trackable : AllTargets) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    //engine.telemetry.addData("Visible Target", trackable.getName());
                    LocaltargetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastKnownLocation = robotLocationTransform;
                    }
                    break;
                }
            }
            targetVisible = LocaltargetVisible;


            if (Complete) {
                engine.telemetry.addLine("Completed Step08DriveToDetect");
                engine.telemetry.update();
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

    public void telemetry() {



        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastKnownLocation.getTranslation();
            engine.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastKnownLocation, EXTRINSIC, XYZ, DEGREES);
            engine.telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
        else {
            engine.telemetry.addData("Visible Target", "none");
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

        //Set Picture Locations on the field

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        //set Camra position on Robot

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
