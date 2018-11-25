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
    private long winchTime;
    private double winchPower;
    private int winchPosition;
    private boolean winchManuelMode = true;
    private double collectionPower;
    private long collectionTime;
    private  int mineralArmPosition;
    private  boolean mineralArmPostitionSet;

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
        mineralArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mineralArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        winchUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
                collectionPower = 1.0;
            } else if (engine.gamepad2.a && collectionToggle == true) {
                collectionToggle = false;
                collectionTime = System.currentTimeMillis() + 500;
                collectionPower = 0;
            }
        }
//--------------------------------------------------------------------------------------------------


//**************************************************************************************************
        //code for running the mineral arm

        if (engine.gamepad2.right_trigger == 0 && engine.gamepad2.left_trigger == 0 && !mineralArmPostitionSet){
            //finding the current position
            mineralArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mineralArmPosition = mineralArm.getCurrentPosition();
            mineralArmPostitionSet = true;
        }

        if (engine.gamepad2.right_trigger != 0 || engine.gamepad2.left_trigger != 0 ){
            //running the motor from controller
            mineralArmPostitionSet = false;
            mineralArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            //clockwise turning for mineral arm
            mineralArm.setPower(-engine.gamepad2.right_trigger);
            //counter clockwise turning for mineral arm
            mineralArm.setPower(engine.gamepad2.left_trigger);
        }
        if (mineralArmPostitionSet == true) {
            //setting the position when stopped
            mineralArm.setTargetPosition(mineralArmPosition);
            mineralArm.setPower(0.2);
        }
//**************************************************************************************************

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //clip arm & winch up controls

        if (engine.gamepad2.left_stick_y >= 0) {
            clipArm.setPower(engine.gamepad2.left_stick_y/4);
        }else{
            clipArm.setPower(engine.gamepad2.left_stick_y);
        }


        if (engine.gamepad2.right_stick_y != 0) {
            winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            winchUp.setPower(engine.gamepad2.right_stick_y);
            winchManuelMode = true;
        }else if (engine.gamepad2.right_stick_y == 0){
            if (winchManuelMode == false) {
                winchUp.setPower(winchPower);
                winchUp.setTargetPosition(winchPosition);
            }else{
                winchUp.setPower(0);
            }
        }

        if (System.currentTimeMillis() >= winchTime) {
            if (engine.gamepad2.b && winchToggle == false) {
                winchUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                winchToggle = true;
                winchTime = System.currentTimeMillis() + 500;
                winchPower = 1.0;
                winchManuelMode = false;
            } else if (engine.gamepad2.b && winchToggle == true) {
                winchToggle = false;
                winchTime = System.currentTimeMillis() + 500;
                winchPower = 1.0;
                winchPosition = winchUp.getCurrentPosition();
                winchUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                winchUp.setTargetPosition(winchPosition);
                winchManuelMode = false;
            }
        }

        //drive train controls
        RightDrive.setPower(engine.gamepad1.right_stick_y);

        LeftDrive.setPower(engine.gamepad1.left_stick_y * -1);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public void telemetry() {
        engine.telemetry.addData("Toggle", SlowToggle);
        engine.telemetry.addData("mineral arm position", mineralArmPosition);
        engine.telemetry.addData("mineral arm position set", mineralArmPostitionSet);
        engine.telemetry.addData("arm motor", mineralArm.getPower());
        engine.telemetry.addData("arm motor trigger postition", engine.gamepad2.right_trigger);
        engine.telemetry.addData("winch encoder",winchUp.getCurrentPosition());
    }
}
