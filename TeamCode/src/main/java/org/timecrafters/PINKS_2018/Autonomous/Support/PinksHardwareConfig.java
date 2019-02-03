package org.timecrafters.PINKS_2018.Autonomous.Support;

/**********************************************************************************************
 * Name: PinksHardwareConfig
 * Inputs: engine,
 * Outputs: none
 * Use: Keeps all of our hardwareMap and Init Values in one place.
 * History:
 * 2/3/19 - Removed now unused Ultrasonic sensors and Laser distance sensors from old Mineral Scan
 * 11/27/18 - Added Vuforia and TensorFlow Init
 * 11/4/18 - Added Ultra sonic sensors
 * 11/13/18 - Created PinksHardwareConfig
 **********************************************************************************************/


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.engine.Engine;

public class PinksHardwareConfig {

    public Engine engine;
    public DcMotor LeftMotor;
    public DcMotor RightMotor;
    public DcMotor FrontLeftMotor;
    public DcMotor FrontRightMotor;
    public DcMotor MineralArmMotor;
    public CRServo ElbowServo;
    public CRServo MineralCollectServo;
    public Servo DropLeft;
    public Servo DropRight;
    public DcMotor ClipArm;
    public DcMotor WinchUp;
    public CRServo Paddle;
    public VuforiaLocalizer VuForia;
    public TFObjectDetector ObjectDetector;


    public PinksHardwareConfig(Engine engine) {
        this.engine = engine;

        // REV exp hub 2 motor port 0
        LeftMotor = engine.hardwareMap.dcMotor.get("leftDrive");

        // REV exp hub 3 motor port 0
        RightMotor = engine.hardwareMap.dcMotor.get("rightDrive");

        FrontLeftMotor = engine.hardwareMap.dcMotor.get("frontLeftDrive");

        FrontRightMotor = engine.hardwareMap.dcMotor.get("frontRightDrive");

        // REV exp hub 2 motor port 1
        MineralArmMotor = engine.hardwareMap.dcMotor.get("mineralArm");

        // REV exp hub 2 servo port 1 CR servo
        ElbowServo = engine.hardwareMap.crservo.get("elbow");

        // REV exp hub 2 servo port 2 CR servo
        MineralCollectServo = engine.hardwareMap.crservo.get("mineralCollect");

        // REV exp hub 2 servo port 0 180 servo
        DropLeft = engine.hardwareMap.servo.get("dropLeft");

        // REV exp hub 3 servo port 0 180 servo
        DropRight = engine.hardwareMap.servo.get("dropRight");

        // Rev exp hub 3 motor port 1
        ClipArm = engine.hardwareMap.dcMotor.get("clipArm");

        // Rev exp hub 3 motor port 2
        WinchUp = engine.hardwareMap.dcMotor.get("winchUp");

        // Rev exp hub 3 servo port 5 CR servo
        Paddle = engine.hardwareMap.crservo.get("Paddle");


        //Set RunModes
        LeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        RightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        LeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Hardware Positions
        LeftMotor.setPower(0.0);
        RightMotor.setPower(0.0);
        MineralArmMotor.setPower(0.0);
        ElbowServo.setPower(0.0);
        MineralCollectServo.setPower(0.0);
        DropLeft.setPosition(1.0);
        DropRight.setPosition(0.0);


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

        engine.telemetry.addLine("Init Finished :)");
    }
}
