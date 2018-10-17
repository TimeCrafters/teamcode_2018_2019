package org.timecrafters.Nartaniel.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.container.InputChecker;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TeleOpState extends State {
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private double SpeedMultiplier;
    private InputChecker ButtonUpCheck;
    private boolean SlowToggle;

    public TeleOpState(Engine engine) {
        this.engine = engine;
        ButtonUpCheck = new InputChecker(engine.gamepad1);

    }

    public void init() {
        RightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
        LeftDrive = engine.hardwareMap.dcMotor.get("leftDrive");
        LeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        SlowToggle = false;
        SpeedMultiplier = 0.7;
    }

    @Override
    public void exec() throws InterruptedException {
        ButtonUpCheck.update();
        RightDrive.setPower(engine.gamepad1.right_stick_y * SpeedMultiplier);
        LeftDrive.setPower(engine.gamepad1.left_stick_y * SpeedMultiplier);
        if (engine.gamepad1.left_bumper) {SpeedMultiplier = 1;}

        if (ButtonUpCheck.check("right_bumper")) {
            SlowToggle = !SlowToggle;
            if (SlowToggle) {SpeedMultiplier = 0.3;}
        }

        if (!SlowToggle && !engine.gamepad1.left_bumper) {
            SpeedMultiplier = 0.7;
        }


    }

    public void telemetry() {
        engine.telemetry.addData("Toggle", SlowToggle);
        engine.telemetry.addData("SpeedMultiplier", SpeedMultiplier);
    }
}
