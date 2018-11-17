package org.timecrafters.PINKS_2018.States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step07PostDropUTurn extends State {
    private boolean Complete = false;
    public Step05ArchitectureControl Control;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private boolean FirstRun;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceINRight = 43;
    private int distanceINLeft = 22;
    private int distanceTicksRight;
    private int distanceTicksLeft;
    private double whealCircumference = 4;


    public Step07PostDropUTurn(Engine engine, Step05ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;

    }

    public void init() {
        LeftDrive = engine.hardwareMap.dcMotor.get("leftDrive");
        RightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
        RightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DistanceConverter();

        FirstRun = true;

    }

    private void DistanceConverter() {
        distanceTicksRight = (int) ((distanceINRight * 288) / (whealCircumference * Math.PI));
        distanceTicksLeft = (int) ((distanceINLeft * 288) / (whealCircumference * Math.PI));
    }

    @Override
    public void exec() {
        if (Control.RunPostDropUTurn) {
            if (FirstRun) {
                engine.telemetry.addLine("FIRSTRUN!!!");
                sleep(1000);
                FirstRun = false;
            }

            RightDrive.setPower(-0.925);
            LeftDrive.setPower(-0.358139534883721);

            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();


            if (Math.abs(RightCurrentTick) >= distanceTicksRight) {
                RightDrive.setPower(0);
            }

            if (Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
                LeftDrive.setPower(0);
            }

            if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
                Complete = true;
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step07PostDropUTurn");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("LeftCurrentTick", LeftCurrentTick);
        engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
        engine.telemetry.addData("distanceTicksRight", distanceTicksRight);
        engine.telemetry.addData("distanceTicksLeft", distanceTicksLeft);
        engine.telemetry.addData("RightPower", RightDrive.getPower());
        engine.telemetry.addData("LeftPower", LeftDrive.getPower());
        engine.telemetry.addData("RunMode", RightDrive.getMode());
    }
}

