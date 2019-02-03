package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step14Park extends State {
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
    private boolean DriveAround;




    public Step14Park(Engine engine, ArchitectureControl control, boolean posDepot, boolean driveAround) {
        this.engine = engine;
        this.Control = control;
        this.PosDepot = posDepot;
        this.DriveAround = driveAround;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.LeftMotor;
        RightDrive = Control.PinksHardwareConfig.RightMotor;
        DriveStep = 1;
        FirstRun = true;
    }

    @Override
    public void exec() {

        if (Control.RunPark) {
            if (FirstRun) {
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                FirstRun = false;
            }

            //the PosDepot Variable enables the different drive patterns for
            if (PosDepot) {
                if (DriveAround) {
                    //Position: Depot, PinksDrive around robot
                    if (DriveStep == 1) {

                        LeftPower = Control.AppReader.get("RunPark").variable("Arc1LPowerD");
                        RightPower = Control.AppReader.get("RunPark").variable("Arc1RPowerD");
                        distanceINLeft = Control.AppReader.get("RunPark").variable("Arc1LIND");
                        distanceINRight = Control.AppReader.get("RunPark").variable("Arc1RIND");

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 2) {

                        LeftPower = Control.AppReader.get("RunPark").variable("Arc2LPowerD");
                        RightPower = Control.AppReader.get("RunPark").variable("Arc2RPowerD");
                        distanceINLeft = Control.AppReader.get("RunPark").variable("Arc2LIND");
                        distanceINRight = Control.AppReader.get("RunPark").variable("Arc2RIND");

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 3) {
                        Complete = true;
                    }

                } else {
                    //Position: Depot, Don't drive around
                    if (DriveStep == 1) {

                        LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectLPowerD");
                        RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectRPowerD");
                        distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectLIND");
                        distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectRIND");

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 2) {
                        Complete = true;
                    }

                }
            } else {
                if (DriveAround) {
                    //Position: Crater, drive around Robot
                    if (DriveStep == 1) {

                        LeftPower = Control.AppReader.get("RunPark").variable("Arc1LPowerC");
                        RightPower = Control.AppReader.get("RunPark").variable("Arc1RPowerC");
                        distanceINLeft = Control.AppReader.get("RunPark").variable("Arc1LINC");
                        distanceINRight = Control.AppReader.get("RunPark").variable("Arc1RINC");

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 2) {

                        LeftPower = Control.AppReader.get("RunPark").variable("Arc2LPowerC");
                        RightPower = Control.AppReader.get("RunPark").variable("Arc2RPowerC");
                        distanceINLeft = Control.AppReader.get("RunPark").variable("Arc2LINC");
                        distanceINRight = Control.AppReader.get("RunPark").variable("Arc2RINC");

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 3) {
                        Complete = true;
                    }
                } else {
                    //Position: Crater, don't drive around
                    if (DriveStep == 1) {

                        LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectLPowerC");
                        RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectRPowerC");
                        distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectLINC");
                        distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("DirectRINC");


                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    if (DriveStep == 2) {
                        Complete = true;
                    }
                }
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
        engine.telemetry.addData("PinksDrive Step", DriveStep);
        engine.telemetry.addData("RightPower", RightDrive.getPower());
        engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
        engine.telemetry.addData("RightDrive Target IN", distanceINRight);
        engine.telemetry.addData("LeftDrive Target IN", distanceINLeft);
        engine.telemetry.addData("RightDrive Target Tick", distanceTicksRight);
        engine.telemetry.addData("LeftDrive Target Tick", distanceTicksLeft);


    }

}
