package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.engine.Engine;

public class PinksHardwareConfig {

    /* names of hardware on phone */
    public Engine engine;
    private String szLeftMotor = "leftDrive";      // REV exp hub 2 motor port 0
    private String szRightMotor = "rightDrive";     // REV exp hub 3 motor port 0
    private String szLeftFrontMotor = "frontLeftDrive";
    private String szRightFrontMotor = "frontRightDrive";
    private String szLaserArmServo = "laserArm";       // REV exp hub 3 servo port 5 180 servo
    private String szMineralDetect0 = "distance0";      // REV exp hub 3 i2c bus 0 port 1
    private String szMineralDetect1 = "distance1";      // REV exp hub 3 i2c bus 1 port 0
    private String szMineralDetect2 = "distance2";      // REV exp hub 3 i2c bus 2 port 0
    private String szMineralDetect3 = "distance3";      // REV exp hub 3 i2c bus 3 port 0
    private String szMineralArmMotor = "mineralArm";     // REV exp hub 2 motor port 1
    private String szElbowServo = "elbow";          // REV exp hub 2 servo port 1 CR servo
    private String szMineralCollectServo = "mineralCollect"; // REV exp hub 2 servo port 2 CR servo
    private String szDropLeft = "dropLeft";       // REV exp hub 2 servo port 0 180 servo
    private String szDropRight = "dropRight";      // REV exp hub 3 servo port 0 180 servo
    private String szClipArm = "clipArm";       // Rev exp hub 3 motor port 1
    private String szWinchUp = "winchUp";       // Rev exp hub 3 motor port 2
    private String szLeftUSSensor = "LeftUS";   // I2C
    private String szRightUSSensor = "RightUS"; // I2C
    private String szPaddle = "Paddle";        // Hub 3 port 5


    /*create pointers for hardware*/
    public DcMotor pLeftMotor;
    public DcMotor pRightMotor;
    public DcMotor pFrontLeftMotor;
    public DcMotor pFrontRightMotor;
    public Servo pLaserArmServo;
    public DistanceSensor pMineralDetect0;
    public DistanceSensor pMineralDetect1;
    public DistanceSensor pMineralDetect2;
    public DistanceSensor pMineralDetect3;
    public DcMotor pMineralArmMotor;
    public CRServo pElbowServo;
    public CRServo pMineralCollectServo;
    public Servo pDropLeft;
    public Servo pDropRight;
    public DcMotor pClipArm;
    public DcMotor pWinchUp;
    public ModernRoboticsI2cRangeSensor pLeftUSSensor;
    public ModernRoboticsI2cRangeSensor pRightUSSensor;
    public CRServo pPaddle;
    public VuforiaLocalizer pVuForia;
    public TFObjectDetector pObjectDetector;


    /* create some useful variables */
    private Long lCurrentTime;
    private Long lStartTimeInit;

    public PinksHardwareConfig(Engine engine) {
        this.engine = engine;
        engine.telemetry.addLine("init started :)");
        lStartTimeInit = System.currentTimeMillis();
        /* get pointers */
        pLeftMotor = engine.hardwareMap.dcMotor.get(szLeftMotor);
        pRightMotor = engine.hardwareMap.dcMotor.get(szRightMotor);
        pFrontLeftMotor = engine.hardwareMap.dcMotor.get(szLeftFrontMotor);
        pFrontRightMotor = engine.hardwareMap.dcMotor.get(szRightFrontMotor);
        //pLaserArmServo = engine.hardwareMap.servo.get(szLaserArmServo);
        //pMineralDetect0 = engine.hardwareMap.get(DistanceSensor.class, szMineralDetect0);
        //pMineralDetect1 = engine.hardwareMap.get(DistanceSensor.class, szMineralDetect1);
        //pMineralDetect2 = engine.hardwareMap.get(DistanceSensor.class, szMineralDetect2);
        //pMineralDetect3 = engine.hardwareMap.get(DistanceSensor.class, szMineralDetect3);
        pMineralArmMotor = engine.hardwareMap.dcMotor.get(szMineralArmMotor);
        pElbowServo = engine.hardwareMap.crservo.get(szElbowServo);
        pMineralCollectServo = engine.hardwareMap.crservo.get(szMineralCollectServo);
        pDropLeft = engine.hardwareMap.servo.get(szDropLeft);
        pDropRight = engine.hardwareMap.servo.get(szDropRight);
        pClipArm = engine.hardwareMap.dcMotor.get(szClipArm);
        pWinchUp = engine.hardwareMap.dcMotor.get(szWinchUp);
        pLeftUSSensor = engine.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, szLeftUSSensor);
        //pRightUSSensor = engine.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, szRightUSSensor);
        pPaddle = engine.hardwareMap.crservo.get(szPaddle);

        /* initialize hardware modes*/
        pLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pFrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pFrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        pLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        pRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        pFrontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        pFrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        pLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pFrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pFrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /* initialize hardware positions*/
        pLeftMotor.setPower(0.0);
        pRightMotor.setPower(0.0);
        //pLaserArmServo.setPosition(0.0);
        pMineralArmMotor.setPower(0.0);
        pElbowServo.setPower(0.0);
        pMineralCollectServo.setPower(0.0);
        pDropLeft.setPosition(1.0);
        pDropRight.setPosition(0.0);
        pRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Vuforia initializing
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        pVuForia = ClassFactory.getInstance().createVuforia(parameters);

        //Tensor Flow Object Detection initializing
        int tfodMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        pObjectDetector = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, pVuForia);
        pObjectDetector.loadModelFromAsset("RoverRuckus.tflite", "Gold Mineral", "Silver Mineral");

    }
}
