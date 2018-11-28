package org.timecrafters.PINKS_2018.TeleOp.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    private long winchTime;
    private double winchPower;
    private int winchPosition;
    private boolean winchManuelMode = true;
    private boolean gameMode = false;
    private double collectionPower;
    private long collectionTime;
    private  int mineralArmTargetPosition;
    private  int mineralArmCurrentPosition;
    private  boolean mineralArmPostitionSet;
    private long mineralModeTime;
    private boolean mineralModeToggle;
    private boolean mineralModeLastRead;

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
       // LeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        SlowToggle = false;
        winchUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mineralArmCurrentPosition = mineralArm.getCurrentPosition();
        mineralModeLastRead = false;

    }

    @Override
    public void exec() throws InterruptedException {
        ButtonUpCheck1.update();
        ButtonUpCheck2.update();



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
        if (engine.gamepad1.y != mineralModeLastRead && engine.gamepad1.y == true){
            gameMode = !gameMode;
        }
        mineralModeLastRead = engine.gamepad1.y;

        if (gameMode == true) {

            rightTriggerValue = engine.gamepad2.right_trigger;
            leftTriggerValue = engine.gamepad2.left_trigger;

            if (leftTriggerValue < 0.05 && rightTriggerValue < 0.05) {
                //no triggers
                mineralArmPower = 1;
            } else {
                //at least one trigger
                mineralArmCurrentPosition = mineralArm.getCurrentPosition();
            }

            if (rightTriggerValue > 0.05) {
                //down
                mineralArmPower = 0.25 * rightTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition - 10;
            }

            if (leftTriggerValue > 0.05) {
                //up
                mineralArmPower = 0.75 * leftTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition + 10;
            }
        }else {
            //start of gamepad 1

            rightTriggerValue = engine.gamepad1.right_trigger;
            leftTriggerValue = engine.gamepad1.left_trigger;

            if (leftTriggerValue < 0.05 && rightTriggerValue < 0.05) {
                //no triggers
                mineralArmPower = 1;
            } else {
                //at least one trigger
                mineralArmCurrentPosition = mineralArm.getCurrentPosition();
            }

            if (rightTriggerValue > 0.05) {
                //down
                mineralArmPower = 0.25 * rightTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition + 10;
            }

            if (leftTriggerValue > 0.05) {
                //up
                mineralArmPower = 0.75 * leftTriggerValue;
                mineralArmTargetPosition = mineralArmCurrentPosition - 10;
            }
        }

        mineralArm.setTargetPosition(mineralArmTargetPosition);
        mineralArm.setPower(mineralArmPower);
//**************************************************************************************************

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //clip arm & winch up controls

        if (engine.gamepad2.left_stick_y >= 0) {
            clipArm.setPower(engine.gamepad2.left_stick_y/4);
        }else{
            clipArm.setPower(engine.gamepad2.left_stick_y);
        }

/*
        if (engine.gamepad2.right_stick_y != 0) {
            winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            winchUp.setPower(engine.gamepad2.right_stick_y);
            winchManuelMode = true;
        }else if (engine.gamepad2.right_stick_y == 0 ){
            if (winchManuelMode == false) {
                winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                winchUp.setPower(winchPower);
                winchUp.setTargetPosition(winchPosition);
            }else{
                winchUp.setPower(0);
            }
        }

        if (System.currentTimeMillis() >= winchTime) {
            if (engine.gamepad2.y && winchToggle == false) {
                winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                winchToggle = true;
                winchTime = System.currentTimeMillis() + 500;
                winchPower = 1.0;
                winchManuelMode = false;
            } else if (engine.gamepad2.y && winchToggle == true) {
                winchToggle = false;
                winchTime = System.currentTimeMillis() + 500;
                winchPower = 1.0;
                winchPosition = winchUp.getCurrentPosition();
                winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                winchUp.setTargetPosition(winchPosition);
                winchManuelMode = false;
            }
        }
*/
        if (engine.gamepad2.right_stick_y != 0){
            winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            winchUp.setPower(engine.gamepad2.right_stick_y);
            winchPosition = winchUp.getCurrentPosition();
        }else{
           winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           winchUp.setTargetPosition(winchPosition);
           winchUp.setPower(1);
        }

        //drive train controls
        RightDrive.setPower(engine.gamepad1.right_stick_y);

        LeftDrive.setPower(engine.gamepad1.left_stick_y * -1);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public void telemetry() {
        engine.telemetry.addData("mineral arm position", mineralArmCurrentPosition);
        engine.telemetry.addData("arm motor power", mineralArmPower);
        engine.telemetry.addData("Right Trigger Value", rightTriggerValue);
        engine.telemetry.addData("Left Trigger Value", leftTriggerValue);
        engine.telemetry.addData("game-mode?",gameMode);
    }
}
