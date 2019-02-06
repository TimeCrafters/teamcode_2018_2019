package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;


import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: DriveToPark
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: drives the robot to the crater and park
 **********************************************************************************************/


public class Step13OptionADriveToPark_fromTMPV2 extends State {
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
    private ModernRoboticsI2cRangeSensor LeftUSSensor;
    private double USDistance;
    private double TargetWallDistance;
    private long ReadTime;
    private long PreviousTriggerTime;
    private double CorrectionAmount;



    public Step13OptionADriveToPark_fromTMPV2(Engine engine, ArchitectureControl control, boolean posDepot) {
        this.engine = engine;
        this.Control = control;
        this.PosDepot = posDepot;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.LeftMotor;
        RightDrive = Control.PinksHardwareConfig.RightMotor;
        ClipArm = Control.PinksHardwareConfig.ClipArm;
        //LeftUSSensor = Control.PinksHardwareConfig.pLeftUSSensor;
        ReadTime = Control.AppReader.get("RunTeamMarkerDrive").variable("CorrectionTime");
        TargetWallDistance = Control.AppReader.get("RunTeamMarkerDrive").variable("TargetWallDistance");
        CorrectionAmount = Control.AppReader.get("RunTeamMarkerDrive").variable("CAmount");
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
                PreviousTriggerTime = System.currentTimeMillis();
                FirstRun = false;
            }
            
            //the PosDepot Variable enables the different drive patterns for different starting positions
            if (PosDepot) {

                //the robot drives strait backwards onto the opponent's crater while simultaneously
                // returning the marker placement arm to it's original position.
                if (DriveStep == 1) {
                    setDriveValues("PowerReverse", "PowerReverse", "InReverse", "InReverse");

                    ClipArm.setTargetPosition(0);
                    ClipArm.setPower(-0.5);
                    if (ClipArm.getCurrentPosition() <= 0) {
                        ClipArm.setPower(0);
                    }

                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);


                }

                //these other steps currently have their variables on the phone set to zero
                //originally they were used in a drive path that retraced our steps around the minerals
                //and back toward the crater.
                if (DriveStep == 2) {
                    setDriveValues("LeftPowerTurn", "RightPowerTurn", "LeftInTurn", "RightInTurn");
                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);

                }

                if (DriveStep == 3) {
                    setDriveValues("ParkDrivePower", "ParkDrivePower", "ParkDriveIN", "ParkDriveIN");
                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                }

                if (DriveStep == 4) {
                    Complete = true;
                }

            } else {

                //the robot drives strait backwards onto the alliance's crater while simultaneously
                // returning the marker placement arm to it's original position.
                if (DriveStep == 1) {

                    //Ultrasonic sensor guidance
                    USDistance = LeftUSSensor.cmUltrasonic();

                    if (System.currentTimeMillis() - PreviousTriggerTime > ReadTime && USDistance < 100) {

                        PreviousTriggerTime = System.currentTimeMillis();

                        distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathIN");
                        distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathIN");

                        if (USDistance > TargetWallDistance + 1) {
                            LeftPower = (double) (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower")) + CorrectionAmount;
                            RightPower = (double) (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower")) - CorrectionAmount;
                        } else if (USDistance < TargetWallDistance - 1) {
                            LeftPower = (double) (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower")) - CorrectionAmount;
                            RightPower = (double) (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower")) + CorrectionAmount;
                        } else {
                            LeftPower = (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower"));
                            RightPower = (Control.AppReader.get("RunDriveToPark_fromTMP").variable("AltPathPower"));
                        }

                        Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                    }

                    //The part that returns the marker arm to storage.
                    ClipArm.setTargetPosition(0);
                    ClipArm.setPower(-1);
                    if (ClipArm.getCurrentPosition() <= 10) {
                        ClipArm.setPower(0);
                    }

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

    //A handy conversion from distance on the field to motor ticks using the circumference of the wheal
    private int DistanceConverter(int distanceIN, int whealCircumference) {
        return (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }

    //these step specific definitions used to be individually done before every drive
    //we packaged it all into this method in response to a recommendation from a previous torment.
    private void setDriveValues(String leftPower, String rightPower, String INLeft, String INRight) {
        LeftPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable(leftPower);
        RightPower = Control.AppReader.get("RunDriveToPark_fromTMP").variable(rightPower);
        distanceINLeft = Control.AppReader.get("RunDriveToPark_fromTMP").variable(INLeft);
        distanceINRight = Control.AppReader.get("RunDriveToPark_fromTMP").variable(INRight);
    }

    private void Drive(double LeftPower, double RightPower, int distanceINLeft, int distanceINRight) {

        RightCurrentTick = RightDrive.getCurrentPosition();
        LeftCurrentTick = LeftDrive.getCurrentPosition();

        distanceTicksLeft = DistanceConverter(distanceINLeft,4);
        distanceTicksRight = DistanceConverter(distanceINRight,4);

        //run the motor until it reaches it's target
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

        //when both motors reach their target, it moves to the next step before resetting the encoders
        if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
            DriveStep++;


            LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }


    public void telemetry() {
        engine.telemetry.addLine("Running Step13OptionADriveToPark_fromTMP");
        engine.telemetry.addData("RightDrive Current Tick", RightCurrentTick);
        engine.telemetry.addData("LeftDrive Current Tick", LeftCurrentTick);
        engine.telemetry.addData("PinksDrive Step", DriveStep);
        engine.telemetry.addData("distanceTicksRight", distanceTicksRight);
        engine.telemetry.addData("distanceTicksLeft", distanceTicksLeft);
    }
}
