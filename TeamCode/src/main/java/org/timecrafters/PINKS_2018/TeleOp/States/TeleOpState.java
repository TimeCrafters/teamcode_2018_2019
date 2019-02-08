package org.timecrafters.PINKS_2018.TeleOp.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.container.InputChecker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import static java.lang.Math.abs;

public class TeleOpState extends State {
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private CRServo collectionServo;
    private CRServo ElbowServo;
    private DcMotor mineralArm;
    private DcMotor clipArm;
    private DcMotor winchUp;
    private InputChecker ButtonUpCheck1;
    private InputChecker ButtonUpCheck2;
    private boolean SlowToggle;
    private boolean collectionToggle;
    private boolean winchToggle;
    private double mineraldivide;
    private double mineralArmPower;
    private boolean rightTriggerValue;
    private boolean leftTriggerValue;
    private double leftStickValue;
    private long captureTime;
    private double capturePower;
    private int winchPosition;
    private boolean winchManuelMode = true;
    private boolean gameMode = false;
    private double collectionPower;
    private long collectionTime;
    private  int mineralArmTargetPosition;
    private  int mineralArmCurrentPosition;
    private  boolean mineralArmPostitionSet;
    private long mineralModeTime;
    private boolean captureToggle;
    private double capturePosition;
    private boolean mineralModeLastRead;
    private int winchTargetPosition;
    private Servo mineralCapture;
    private boolean y1LastRead;
    private boolean a1LastRead;
    private double mineralArmPowerMaxUp;
    private boolean y2LastRead;
    private int clipArmTargetPostition;
    private double clipArmPower;
    private boolean clipIsUp;
    private Servo servoRotation;
    private double servoRotationPosition;
    private boolean servoRotationLastRead;
    private boolean servoRotationLastRead2;
    private boolean servoClampLastRead;
    private boolean servoClampLastRead2;
    private Servo servoClamp;
    private double servoClampPosition;
    private int clipArmLastPosition;
    private boolean clipArmLastRead;
    private boolean clipArmLastRead2;
    private boolean clipArmToggle;
    private double drivePercentage;
    private String speed;
    private CRServo Paddle;



    public TeleOpState(Engine engine) {
        this.engine = engine;
        ButtonUpCheck1 = new InputChecker(engine.gamepad1);
        ButtonUpCheck2 = new InputChecker(engine.gamepad2);

    }

    public void init() {
        RightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
        LeftDrive = engine.hardwareMap.dcMotor.get("leftDrive");
        collectionServo = engine.hardwareMap.crservo.get("mineralCollect");
        ElbowServo = engine.hardwareMap.crservo.get("elbow");
        mineralArm = engine.hardwareMap.dcMotor.get("mineralArm");
        clipArm = engine.hardwareMap.dcMotor.get("clipArm");
        winchUp = engine.hardwareMap.dcMotor.get("winchUp");
        mineralCapture = engine.hardwareMap.servo.get("mineralCapture");
        servoRotation = engine.hardwareMap.servo.get("servoRotation");
        servoClamp = engine.hardwareMap.servo.get("servoClamp");
        frontLeftDrive = engine.hardwareMap.dcMotor.get("frontLeftDrive");
        frontRightDrive = engine.hardwareMap.dcMotor.get("frontRightDrive");
        Paddle = engine.hardwareMap.crservo.get("Paddle");

        winchUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mineralArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mineralArmCurrentPosition = mineralArm.getCurrentPosition();
        winchPosition = winchUp.getCurrentPosition();
        winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mineralCapture.setPosition(capturePosition);

        mineralModeLastRead = false;
        SlowToggle = false;
        a1LastRead = false;
        y1LastRead = false;
        y2LastRead = false;
        clipIsUp = false;

        capturePosition = 0;
        mineralArmPower = 0.1;
        mineralArmPowerMaxUp = 0.4;
        servoRotation.setPosition(0);
        servoRotationPosition = 0.0;
        servoClamp.setPosition(1);
        servoClampPosition = 1;

        clipArmLastPosition = clipArm.getCurrentPosition();
        drivePercentage = 1;


    }

    @Override
    public void exec() throws InterruptedException {
//--------------------------------------------------------------------------------------------------
        //code for elbow and mineral collection

        //elbow code - moving servo left and right when the dpad left or right is true
        if (engine.gamepad1.dpad_left) {
            ElbowServo.setPower(-1);
        }else if (engine.gamepad1.dpad_right) {
            ElbowServo.setPower(1);
        }else{
            ElbowServo.setPower(0);
        }

        //mineral collect toggle - using a toggle to go between 100% power and 0% power on the servo
        //that collects minerals

        collectionServo.setPower(collectionPower);
        if (System.currentTimeMillis() >= collectionTime) {
            if (engine.gamepad2.a && collectionToggle == false) {
                collectionToggle = true;
                collectionTime = System.currentTimeMillis() + 500;
                collectionPower = -1.0;
            } else if (engine.gamepad2.a && collectionToggle == true) {
                collectionToggle = false;
                collectionTime = System.currentTimeMillis() + 500;
                collectionPower = 0;
            }
        }
//--------------------------------------------------------------------------------------------------


//**************************************************************************************************
        //CODE FOR RUNNING MINERAL COLLECTION ARM!!!!!


        mineralArmCurrentPosition = abs(mineralArm.getCurrentPosition());
        rightTriggerValue = engine.gamepad2.right_bumper;
        leftTriggerValue = engine.gamepad2.left_bumper;

        if (mineralArmCurrentPosition < 288 && rightTriggerValue == true){
            mineralArmPower = 0.75 * (288.0-(double)mineralArmCurrentPosition)/288.0 + 0.25;
        }
        //0.75 * mineralArmCurrentPosition/288 + 0.25
        if (mineralArmCurrentPosition >= 288 && rightTriggerValue == true){
            mineralArmPower = 0.25;
        }
        if (mineralArmCurrentPosition >= 576 && leftTriggerValue == true){
            mineralArmPower = -1.0;
        }
        if (mineralArmCurrentPosition < 576 && mineralArmCurrentPosition > 288 && leftTriggerValue == true){
            mineralArmPower = -0.75 * ((double)mineralArmCurrentPosition-288.0)/288.0 - 0.25;
        }

        if (mineralArmCurrentPosition < 288 && leftTriggerValue == true){
            mineralArmPower = -0.25;
        }

        if (leftTriggerValue == false && rightTriggerValue == false){
            mineralArmPower = 0.0;
        }

        //mineralArm.setTargetPosition(mineralArmTargetPosition);
        mineralArm.setPower(mineralArmPower);
//**************************************************************************************************

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //code for clip arm

        if (engine.gamepad2.x != clipArmLastRead && engine.gamepad2.x == true){
            //drives arm down
            clipArm.setPower(-0.75);
        }
        clipArmLastRead = engine.gamepad2.x;

        if (engine.gamepad2.b != clipArmLastRead2 && engine.gamepad2.b == true){
            //drives arm up
            clipArm.setPower(0.75);
    }

        if (engine.gamepad2.b == false &&
                engine.gamepad2.x == false ){
            //arm power set to zero
            clipArm.setPower(0);
        }

        // winch code

        //getting current position when sticks aren't centered
        if (engine.gamepad2.right_stick_y > 0.5 || engine.gamepad2.right_stick_y < -0.5){
            winchPosition = winchUp.getCurrentPosition();
        }

        if (engine.gamepad2.right_stick_y > 0.5){
            //going up
            winchTargetPosition = winchPosition - 50;
        }else if (engine.gamepad2.right_stick_y < -0.5){
            //going down
            winchTargetPosition = winchPosition + 50;
        }else{
            //setting fixed position
            winchTargetPosition = winchPosition;
        }
        //setting power and position of winch
        winchUp.setTargetPosition(winchTargetPosition);
        winchUp.setPower(1);

          //drive train controls
        if (engine.gamepad1.y != y1LastRead &&
                engine.gamepad1.y == true){
            if (drivePercentage == 1.0){
                speed = "slow";
                drivePercentage = 2.0;
            }else if (drivePercentage == 2.0){
                drivePercentage = 1.0;
                speed = "fast";
            }
        }
        y1LastRead = engine.gamepad1.y;

        RightDrive.setPower((engine.gamepad1.right_stick_y)/drivePercentage);
        LeftDrive.setPower((engine.gamepad1.left_stick_y * -1)/drivePercentage);
        frontRightDrive.setPower((engine.gamepad1.right_stick_y)/drivePercentage);
        frontLeftDrive.setPower((engine.gamepad1.left_stick_y * -1)/drivePercentage);


        //toggle for mineral capture
        if (System.currentTimeMillis() >= captureTime) {
            if (engine.gamepad2.y && captureToggle == false) {
                captureToggle = true;
                captureTime = System.currentTimeMillis() + 500;
              capturePosition = 0.25;
            } else if (engine.gamepad2.y && captureToggle == true) {
                captureToggle = false;
                captureTime = System.currentTimeMillis() + 500;
               mineralCapture.setPosition(0);
                capturePosition = 0;
            }
        }
        //setting mineral capture position
        mineralCapture.setPosition(capturePosition);

        //controls for latching on the lander

        //toggle for positive servo position
        if (engine.gamepad2.dpad_down != servoRotationLastRead && engine.gamepad2.dpad_down == true && servoRotationPosition > 0){
            servoRotationPosition = servoRotationPosition -0.05;

        }
        servoRotationLastRead = engine.gamepad2.dpad_down;

        //toggle for negative servo position
        if (engine.gamepad2.dpad_up != servoRotationLastRead2 && engine.gamepad2.dpad_up == true && servoRotationPosition < 1){
            servoRotationPosition = servoRotationPosition +0.05;

        }
        servoRotationLastRead2 = engine.gamepad2.dpad_up;

        //setting servo position
        servoRotation.setPosition(servoRotationPosition);
//--------------------------------------------------------------------------------------------------
        // controls for letting go of hook on clip arm

        //toggle for positive servo position
        if (engine.gamepad2.dpad_left != servoClampLastRead && engine.gamepad2.dpad_left == true && servoClampPosition >0){
            servoClampPosition = servoClampPosition -0.05;

        }
        servoClampLastRead = engine.gamepad2.dpad_left;

        //toggle for negative servo position
        if (engine.gamepad2.dpad_right != servoClampLastRead2 && engine.gamepad2.dpad_right == true&& servoClampPosition < 1){
            servoClampPosition = servoClampPosition +0.05;

        }
        servoClampLastRead2 = engine.gamepad2.dpad_right;

        //setting servo position
        servoClamp.setPosition(servoClampPosition);

//--------------------------------------------------------------------------------------------------
        //paddle controle

        if (engine.gamepad1.a) {
            Paddle.setPower(1.0);
        } else if (engine.gamepad1.b) {
            Paddle.setPower(-1.0);
        } else {
            Paddle.setPower(0);
        }


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public void telemetry() {
        engine.telemetry.addData("drive speed",speed);
        engine.telemetry.addData("mineral arm position", mineralArmCurrentPosition);
        engine.telemetry.addData("arm motor power", mineralArmPower);
        engine.telemetry.addData("RightDrive Trigger Value", rightTriggerValue);
        engine.telemetry.addData("LeftDrive Trigger Value", leftTriggerValue);
        engine.telemetry.addData("game-mode?",gameMode);
        engine.telemetry.addData("tick of winch",winchPosition);
        engine.telemetry.addData("mineral max power",mineralArmPowerMaxUp);
        engine.telemetry.addData("clamp position", servoRotation.getPosition());
        engine.telemetry.addData("clip target position", clipArmLastPosition);
        engine.telemetry.addData("current clip position",clipArm.getCurrentPosition());
        engine.telemetry.addData("clip power!!!",clipArm.getPower());
            }
}
