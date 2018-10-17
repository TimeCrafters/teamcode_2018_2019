package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Drive extends State {
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private double Power;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceIN;
    private int distanceTicks;
    private double whealCircumference;

    public Drive(Engine engine, double power, int distanceIN, int whealCircumferenceXPi) {
        this.engine = engine;
        this.Power = power;
        this.distanceIN = distanceIN;
        this.whealCircumference = whealCircumferenceXPi;


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
    }

    private void DistanceConverter() {
        distanceTicks = (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }


    @Override
    public void exec() {
        RightCurrentTick = RightDrive.getCurrentPosition();
        LeftCurrentTick = LeftDrive.getCurrentPosition();

        if (RightCurrentTick <= distanceTicks) {
            RightDrive.setPower(Power);
            LeftDrive.setPower(Power);
        }else {
            RightDrive.setPower(0);
            LeftDrive.setPower(0);
            setFinished(true);
        }


    }

    public void telemetry() {
        engine.telemetry.addData("Curret Right Tick", RightDrive.getCurrentPosition());
        engine.telemetry.addData("Curret Left Tick", LeftDrive.getCurrentPosition());
        engine.telemetry.addData("Power", Power);
        engine.telemetry.addData("Target Ticks", distanceTicks);
    }
}
