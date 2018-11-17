package org.timecrafters.scott.hardwareConfig;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_state_hardware_config extends State{

    public scott_state_hardware_config(Engine engine){
        this.engine = engine;
    }
    /* names of hardware on phone */
    private String szLeftMotor           = "leftDrive";      // REV exp hub 2 motor port 0
    private String szRightMotor          = "rightDrive";     // REV exp hub 3 motor port 0
    private String szLaserArmServo       = "laserArm";       // REV exp hub 3 servo port 5 180 servo
    private String szMineralDetect0      = "distance0";      // REV exp hub 3 i2c bus 0 port 1
    private String szMineralDetect1      = "distance1";      // REV exp hub 3 i2c bus 1 port 0
    private String szMineralDetect2      = "distance2";      // REV exp hub 3 i2c bus 2 port 0
    private String szMineralDetect3      = "distance3";      // REV exp hub 3 i2c bus 3 port 0
    private String szMineralArmMotor     = "mineralArm";     // REV exp hub 2 motor port 1
    private String szElbowServo          = "elbow";          // REV exp hub 2 servo port 1 CR servo
    private String szMineralCollectServo = "mineralCollect"; // REV exp hub 2 servo port 2 CR servo
    private String szDropLeft            = "dropLeft";       // REV exp hub 2 servo port 0 180 servo
    private String szDropRight           = "dropRight";      // REV exp hub 3 servo port 0 180 servo

    /*create pointers for hardware*/
    public DcMotor        pLeftMotor;
    public DcMotor        pRightMotor;
    public Servo          pLaserArmServo;
    public DistanceSensor pMineralDetect0;
    public DistanceSensor pMineralDetect1;
    public DistanceSensor pMineralDetect2;
    public DistanceSensor pMineralDetect3;
    public DcMotor        pMineralArmMotor;
    public CRServo        pElbowServo;
    public CRServo        pMineralCollectServo;
    public Servo          pDropLeft;
    public Servo          pDropRight;

    /* create some useful variables */
    private Long          lCurrentTime;
    private Long          lStartTimeInit;

    @Override
    public void init() {
        engine.telemetry.addData("init started",":)");
        lStartTimeInit = System.currentTimeMillis();
        /* get pointers */
        pLeftMotor           = engine.hardwareMap.dcMotor.get(szLeftMotor);
        pRightMotor          = engine.hardwareMap.dcMotor.get(szRightMotor);
        pLaserArmServo       = engine.hardwareMap.servo.get(szLaserArmServo);
        pMineralDetect0      = engine.hardwareMap.get(DistanceSensor.class,szMineralDetect0);
        pMineralDetect1      = engine.hardwareMap.get(DistanceSensor.class,szMineralDetect1);
        pMineralDetect2      = engine.hardwareMap.get(DistanceSensor.class,szMineralDetect2);
        pMineralDetect3      = engine.hardwareMap.get(DistanceSensor.class,szMineralDetect3);
        pMineralArmMotor     = engine.hardwareMap.dcMotor.get(szMineralArmMotor);
        pElbowServo          = engine.hardwareMap.crservo.get(szElbowServo);
        pMineralCollectServo = engine.hardwareMap.crservo.get(szMineralCollectServo);
        pDropLeft            = engine.hardwareMap.servo.get(szDropLeft);
        pDropRight           = engine.hardwareMap.servo.get(szDropRight);

        /* initialize hardware modes*/
        pLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        pRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        pLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /* initialize hardware positions*/
        pLeftMotor.setPower(0.0);
        pRightMotor.setPower(0.0);
        pLaserArmServo.setPosition(0.0);
        pMineralArmMotor.setPower(0.0);
        pElbowServo.setPower(0.0);
        pMineralCollectServo.setPower(0.0);
        pDropLeft.setPosition(1.0);
        pDropRight.setPosition(0.0);

        /* report to screen */
        lCurrentTime = System.currentTimeMillis();
        engine.telemetry.addData("init time", lCurrentTime-lStartTimeInit);
        engine.telemetry.update();
    }

    @Override
    public void exec() {
        setFinished(true);
    }

}
