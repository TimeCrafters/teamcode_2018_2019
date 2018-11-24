package org.timecrafters.Nartaniel.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.container.InputChecker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TeleOpStateV2 extends State {
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private CRServo CollectionServo;
    private CRServo ElbowServo;
    private DcMotor Shoulder;
    private double SpeedMultiplier;
    private InputChecker ButtonUpCheck1;
    private InputChecker ButtonUpCheck2;
    private boolean SlowToggle;
    private boolean CollectionToggle;

    public TeleOpStateV2(Engine engine) {
        this.engine = engine;
        ButtonUpCheck1 = new InputChecker(engine.gamepad1);
        ButtonUpCheck2 = new InputChecker(engine.gamepad2);

    }

    public void init() {
        RightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
        LeftDrive = engine.hardwareMap.dcMotor.get("leftDrive");
        CollectionServo = engine.hardwareMap.crservo.get("mineralCollect");
        ElbowServo = engine.hardwareMap.crservo.get("elbow");
        Shoulder = engine.hardwareMap.dcMotor.get("mineralArm");
        LeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        SlowToggle = false;
        SpeedMultiplier = 0.7;
    }

    @Override
    public void exec() throws InterruptedException {
        ButtonUpCheck1.update();
        ButtonUpCheck2.update();

        //Sets Motors to Changeable Power
        RightDrive.setPower(engine.gamepad1.right_stick_y * SpeedMultiplier);
        LeftDrive.setPower(engine.gamepad1.left_stick_y * SpeedMultiplier);

        //A "Sprint" button
        if (engine.gamepad1.left_bumper) {SpeedMultiplier = 1;}

        //A "Sneak" Toggle
        if (ButtonUpCheck1.check("right_bumper")) {
            SlowToggle = !SlowToggle;
            if (SlowToggle) {SpeedMultiplier = 0.3;}
        }

        //Sets to Original speed
        if (!SlowToggle && !engine.gamepad1.left_bumper) {
            SpeedMultiplier = 0.7;
        }

        //Collection Toggle
        if (ButtonUpCheck2.check("a")) {
            CollectionToggle = !CollectionToggle;
            if (CollectionToggle) {
                CollectionServo.setPower(1);
            } else {
                CollectionServo.setPower(0);
            }
        }

        //Arm Control with joysticks
        Shoulder.setPower(engine.gamepad2.left_stick_y);
        ElbowServo.setPower(engine.gamepad2.right_stick_x);





    }

    public void telemetry() {
        engine.telemetry.addData("Toggle", SlowToggle);
        engine.telemetry.addData("SpeedMultiplier", SpeedMultiplier);
    }
}
