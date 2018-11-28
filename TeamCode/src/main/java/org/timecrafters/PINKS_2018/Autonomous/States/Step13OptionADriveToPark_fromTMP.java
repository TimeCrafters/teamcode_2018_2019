package org.timecrafters.PINKS_2018.Autonomous.States;


import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step13OptionADriveToPark_fromTMP extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private boolean PosDepot;
    private boolean FirstRun;
    private int DriveStep;
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
    private DcMotor ClipArm;



    public Step13OptionADriveToPark_fromTMP(Engine engine, ArchitectureControl control, boolean posDepot) {
        this.engine = engine;
        this.Control = control;
        this.PosDepot = posDepot;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;
        ClipArm = Control.PinksHardwareConfig.pClipArm;
        FirstRun = true;
        DriveStep = 1;
    }

    @Override
    public void exec() {
        if (Control.RunDriveToPark_fromTMP) {

            if (FirstRun) {
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                Log.i("distanceInRight", "First Run");
                FirstRun = false;
            }

            if (PosDepot) {

                if (DriveStep == 1) {
                    LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("PowerReverse");
                    RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("PowerReverse");
                    distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("InReverse");
                    distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("InReverse");

                    ClipArm.setTargetPosition(0);
                    ClipArm.setPower(-0.15);
                    if (ClipArm.getCurrentPosition() <= 0) {
                        ClipArm.setPower(0);
                    }

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);


                }

                if (DriveStep == 2) {

                    LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("LeftPowerTurn");
                    RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("RightPowerTurn");
                    distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("LeftInTurn");
                    distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("RightInTurn");

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);

                }

                if (DriveStep == 3) {

                    LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("ParkDrivePower");
                    RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("ParkDrivePower");
                    distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("ParkDriveIN");
                    distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("ParkDriveIN");

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                }

                if (DriveStep == 4) {

                    LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("LParkArcPower");
                    RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("RParkArcPower");
                    distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("LParkArcIN");
                    distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("RParkArcIN");

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                }

                if (DriveStep == 5) {
                    Log.i("distanceInRight", "Completed On Time");
                    Complete = true;
                }

            } else {

                Log.i("distanceInRight", "You're NOT supposed to be here!");

                if (DriveStep == 1) {
                    LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower");
                    RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower");
                    distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathIN");
                    distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathIN");

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                }

                if (DriveStep == 2) {
                    Complete = true;
                }

            }

            if (Complete) {
                engine.telemetry.addLine("Completed RunDriveToPark_fromTMP");
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
            LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveStep++;
            sleep(100);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addLine("Running Step13OptionADriveToPark_fromTMP");
        engine.telemetry.addData("Right Current Tick", RightCurrentTick);
        engine.telemetry.addData("Left Current Tick", LeftCurrentTick);
        engine.telemetry.addData("Drive Step", DriveStep);
        engine.telemetry.addData("distanceTicksRight", distanceTicksRight);
        engine.telemetry.addData("distanceTicksLeft", distanceTicksLeft);
    }
}
