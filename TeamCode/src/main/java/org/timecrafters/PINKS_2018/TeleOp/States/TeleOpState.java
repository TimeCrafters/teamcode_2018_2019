package org.timecrafters.PINKS_2018.TeleOp.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.container.InputChecker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TeleOpState extends State {
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
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
    private double rightTriggerValue;
    private double leftTriggerValue;
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
       // LeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        SlowToggle = false;
        winchUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mineralArmCurrentPosition = mineralArm.getCurrentPosition();
        mineralModeLastRead = false;
        winchPosition = winchUp.getCurrentPosition();
        winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        capturePosition = 0;
        mineralCapture.setPosition(capturePosition);
        a1LastRead = false;
        y1LastRead = false;
        y2LastRead = false;
        mineralArmPower = 0.1;
        mineralArmPowerMaxUp = 0.4;
        clipIsUp = false;
    }

    @Override
    public void exec() throws InterruptedException {
//--------------------------------------------------------------------------------------------------
        //code for elbow and mineral collect

        if (engine.gamepad1.dpad_left) {
            ElbowServo.setPower(-1);
        }else if (engine.gamepad1.dpad_right) {
            ElbowServo.setPower(1);
        }else{
            ElbowServo.setPower(0);
        }

        //mineral collect toggle

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
        //CODE FOR RUNNING MINERAL ARM!!!!!

        //power up and down
        if (engine.gamepad1.y != y1LastRead &&
                engine.gamepad1.y == true &&
                mineralArmPowerMaxUp < 1.0){
            mineralArmPowerMaxUp += 0.1;
        }
        y1LastRead = engine.gamepad1.y;

        if (engine.gamepad1.a != a1LastRead &&
                engine.gamepad1.a == true &&
                mineralArmPowerMaxUp > 0.0){
            mineralArmPowerMaxUp -= 0.1;
        }
        a1LastRead = engine.gamepad1.a;
        //Game mode toggle
        if (engine.gamepad1.x != mineralModeLastRead && engine.gamepad1.x == true){
            gameMode = !gameMode;
        }
        mineralModeLastRead = engine.gamepad1.x;

        //switch
        if (gameMode == true) {

            rightTriggerValue = engine.gamepad2.right_trigger;
            leftTriggerValue = engine.gamepad2.left_trigger;

            if (leftTriggerValue < 0.05 && rightTriggerValue < 0.05) {
                //no triggers
                mineralArmPower = 0.1;
            } else {
                //at least one trigger
                mineralArmCurrentPosition = mineralArm.getCurrentPosition();
            }

            if (rightTriggerValue > 0.05) {
                //down
                mineralArmPower = 0.25 * rightTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition - 75;
            }

            if (leftTriggerValue > 0.05) {
                //up
                mineralArmTargetPosition = mineralArmCurrentPosition + 75;
                mineralArmPower = mineralArmPowerMaxUp * leftTriggerValue;
            }
        }else{
            //start of gamepad 1
            rightTriggerValue = engine.gamepad1.right_trigger;
            leftTriggerValue = engine.gamepad1.left_trigger;

            if (leftTriggerValue < 0.05 && rightTriggerValue < 0.05) {
                //no triggers
                //mineralArmPower = 1;
            } else {
                //at least one trigger
                mineralArmCurrentPosition = mineralArm.getCurrentPosition();
            }

            if (rightTriggerValue > 0.05) {
                //down
                mineralArmPower = 0.25 * rightTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition + 75;
            }

            if (leftTriggerValue > 0.05) {
                //up
                mineralArmTargetPosition = mineralArmCurrentPosition - 75;
                mineralArmPower = mineralArmPowerMaxUp * leftTriggerValue;
            }
        }

        mineralArm.setTargetPosition(mineralArmTargetPosition);
        mineralArm.setPower(mineralArmPower);
//**************************************************************************************************

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //clip arm code
      /*  if (engine.gamepad2.y != y2LastRead &&
                engine.gamepad2.y == true &&
                clipIsUp == false){
            clipArmTargetPostition = 72;
            clipArmPower = 0.75;
            clipIsUp = true;
            y2LastRead = true;
        }
        if (engine.gamepad2.y != y2LastRead &&
                engine.gamepad2.y == true &&
                clipIsUp == true){
            clipArmTargetPostition = 0;
            clipArmPower = 0.25;
            clipIsUp = false;
            y2LastRead = true;
        }*/


        leftStickValue = engine.gamepad2.left_stick_y;

        clipArm.setPower(leftStickValue);
    /*    if (leftStickValue >= 0.1){
            clipArmTargetPostition = clipArm.getCurrentPosition() + 20;
            clipArmPower = 1*leftStickValue;
        }
        if (leftStickValue <= -0.1){
            clipArmTargetPostition = clipArm.getCurrentPosition() - 20;
            clipArmPower = 0.5*leftStickValue;
        }
        clipArm.setTargetPosition(clipArmTargetPostition);
        clipArm.setPower(clipArmPower);*/

 //winch code
if (engine.gamepad2.right_stick_y > 0.5 || engine.gamepad2.right_stick_y < -0.5){
    winchPosition = winchUp.getCurrentPosition();
}
        if (engine.gamepad2.right_stick_y > 0.5){
            winchTargetPosition = winchPosition + 50;
        }else if (engine.gamepad2.right_stick_y < -0.5){
            winchTargetPosition = winchPosition - 50;
        }else{
            winchTargetPosition = winchPosition;
        }

        winchUp.setTargetPosition(winchTargetPosition);
        winchUp.setPower(1);

/*if (engine.gamepad2.right_stick_y >= 0.05){
    winchUp.setPower(1.0*engine.gamepad2.right_stick_y);
}else if (engine.gamepad2.right_stick_y <= -0.05){
    winchUp.setPower(1.0*engine.gamepad2.right_stick_y);
}else{
    winchUp.setPower(0.1);
}*/

        //drive train controls
        RightDrive.setPower(engine.gamepad1.right_stick_y);
        LeftDrive.setPower(engine.gamepad1.left_stick_y * -1);

        if (System.currentTimeMillis() >= captureTime) {
            if (engine.gamepad2.x && captureToggle == false) {
                captureToggle = true;
                captureTime = System.currentTimeMillis() + 500;
              capturePosition = 0.25;
            } else if (engine.gamepad2.x && captureToggle == true) {
                captureToggle = false;
                captureTime = System.currentTimeMillis() + 500;
               mineralCapture.setPosition(0);
                capturePosition = 0;
            }
        }

        mineralCapture.setPosition(capturePosition);




//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public void telemetry() {
        engine.telemetry.addData("mineral arm position", mineralArmCurrentPosition);
        engine.telemetry.addData("arm motor power", mineralArmPower);
        engine.telemetry.addData("Right Trigger Value", rightTriggerValue);
        engine.telemetry.addData("Left Trigger Value", leftTriggerValue);
        engine.telemetry.addData("game-mode?",gameMode);
        engine.telemetry.addData("tick of winch",winchPosition);
        engine.telemetry.addData("mineral max power",mineralArmPowerMaxUp);
    }
}
