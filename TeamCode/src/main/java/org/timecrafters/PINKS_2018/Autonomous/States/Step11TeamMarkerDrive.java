package org.timecrafters.PINKS_2018.Autonomous.States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step11TeamMarkerDrive extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private boolean PosDepot;
    private int DriveStep;
    private boolean FirstRun;
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



    public Step11TeamMarkerDrive(Engine engine, ArchitectureControl control, boolean posDepot) {
        this.engine = engine;
        this.Control = control;
        this.PosDepot = posDepot;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;
        DriveStep = 1;
        FirstRun = true;
    }

    @Override
    public void exec() {

        if (Control.RunTeamMarkerDrive) {
            if (FirstRun) {
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                FirstRun = false;
            }

            //the PosDepot Variable enables the different drive patterns for
            if (DriveStep == 1 && PosDepot) {

                LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftPowerArc");
                RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable("RightPowerArc");
                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInArc");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInArc");

                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (DriveStep == 1 && !PosDepot) {

                LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable("CLeftPowerArc");
                RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable("CRightPowerArc");
                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("CLeftInArc");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("CRightInArc");

                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (DriveStep == 2 && PosDepot) {

                LeftPower = 0.7;
                RightPower = 0.7;

                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInReverse");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInReverse");

                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (DriveStep == 2 && !PosDepot) {

                LeftPower = 0.7;
                RightPower = 0.7;

                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("CLeftIN");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("CRightIN");

                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (DriveStep == 3) {
                Complete = true;
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step14Park");
                engine.telemetry.update();
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

        RightCurrentTick = RightDrive.getCurrentPosition();
        LeftCurrentTick = LeftDrive.getCurrentPosition();

        distanceTicksLeft = DistanceConverter(distanceINLeft,4);
        distanceTicksRight = DistanceConverter(distanceINRight,4);

        if (Math.abs(RightCurrentTick) >= distanceTicksRight) {
            RightDrive.setPower(0);
        } else {
            RightDrive.setPower(RightPower);
        }

        if (Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
            LeftDrive.setPower(0);
        } else {
            LeftDrive.setPower(LeftPower);
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
        engine.telemetry.addLine("Running RunPark");
        engine.telemetry.addData("Drive Step", DriveStep);
        engine.telemetry.addData("RightPower", RightDrive.getPower());
        engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
        engine.telemetry.addData("Right Target IN", distanceINRight);
        engine.telemetry.addData("Left Target IN", distanceINLeft);
        engine.telemetry.addData("Right Target Tick", distanceTicksRight);
        engine.telemetry.addData("Left Target Tick", distanceTicksLeft);


    }

}