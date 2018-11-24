package org.timecrafters.PINKS_2018.Autonomous.States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step11TeamMarkerDrive extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private int DriveStep;
    private boolean FirstRun = true;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private double LeftPower;
    private double RightPower;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceINRight;
    private int distanceINLeft;
    private int distanceTicksRight;
    private int distanceTicksLeft;



    public Step11TeamMarkerDrive(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;
        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveStep = 1;
    }

    @Override
    public void exec() {

        if (Control.RunTeamMarkerDrive) {

            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();

            if (DriveStep == 1) {

                LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftPowerArc");
                RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable("RightPowerArc");
                RightPower = - RightPower;
                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInArc");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInArc");

                Drive(LeftPower, RightPower, distanceINLeft, distanceTicksRight);
            }

            if (DriveStep == 2) {

                LeftPower = 0.7;
                RightPower = 0.7;

                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInReverse");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInReverse");

                Drive(LeftPower, RightPower, distanceINLeft, distanceTicksRight);
            }

            if (DriveStep == 3) {
                Complete = true;
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step11TeamMarkerDrive");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }


    }

    private int DistanceConverter(int distanceIN, int whealCircumference) {
        return (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }

    private void Drive(double LeftPower, double RightPower, int distanceINLeft, int distanceINRight) {

        distanceTicksLeft = DistanceConverter(distanceINLeft,4);
        distanceTicksRight = DistanceConverter(distanceINRight,4);

        LeftDrive.setPower(LeftPower);
        RightDrive.setPower(RightPower);

        if (Math.abs(RightCurrentTick) >= distanceTicksRight) {
            RightDrive.setPower(0);
        }

        if (Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
            LeftDrive.setPower(0);
        }

        if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
            DriveStep++;


            LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addLine("Running RunTeamMarkerDrive");
        engine.telemetry.addData("Drive Step", DriveStep);
        engine.telemetry.addData("LeftPower", LeftPower);
        engine.telemetry.addData("RightPower", RightPower);
        engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
        engine.telemetry.addData("LeftCurrentTick", LeftCurrentTick);

    }

}
