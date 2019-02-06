package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: TeamMarkerDrive
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: drive over to the depot
 * Version 1.0
 **********************************************************************************************/

public class Step11TeamMarkerDriveV2 extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private long PreviousTriggerTime;
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
    private ModernRoboticsI2cRangeSensor LeftUSSensor;
    private double USDistance;
    private double TargetWallDistance;
    private long ReadTime;
    private double CorrectionAmount;





    public Step11TeamMarkerDriveV2(Engine engine, ArchitectureControl control, boolean posDepot) {
        this.engine = engine;
        this.Control = control;
        this.PosDepot = posDepot;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.LeftMotor;
        RightDrive = Control.PinksHardwareConfig.RightMotor;
        //LeftUSSensor = Control.PinksHardwareConfig.pLeftUSSensor;
        ReadTime = Control.AppReader.get("RunTeamMarkerDrive").variable("CorrectionTime");
        TargetWallDistance = Control.AppReader.get("RunTeamMarkerDrive").variable("TargetWallDistance");
        CorrectionAmount = Control.AppReader.get("RunTeamMarkerDrive").variable("CAmount");
        DriveStep = 1;
        FirstRun = true;
    }

    @Override
    public void exec() {

        if (Control.RunTeamMarkerDrive) {
            if (FirstRun) {
                //These used to be in init which left things very broken
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                PreviousTriggerTime = System.currentTimeMillis();
                FirstRun = false;
            }

            //Turns the robot to face the depot
            //the PosDepot Variable enables the different drive patterns for different starting positions
            if (DriveStep == 1 && PosDepot) {
                //The strings below correspond to values we can edit on the phone
                setDriveValues("LeftPowerArc", "RightPowerArc", "LeftInArc", "RightInArc");
                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            //This dose the same thing as the step above, just adjusted for a different starting location
            if (DriveStep == 1 && !PosDepot) {
                setDriveValues("CLeftPowerArc", "CRightPowerArc", "CLeftInArc", "CRightInArc");
                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            // These drive strait into the depot for a distance that depends on the starting position
            if (DriveStep == 2 && PosDepot) {

                //Ultrasonic sensor guidance
                USDistance = LeftUSSensor.cmUltrasonic();

                if (System.currentTimeMillis() - PreviousTriggerTime > ReadTime && USDistance < 100) {

                    PreviousTriggerTime = System.currentTimeMillis();

                    if (USDistance > TargetWallDistance + 1) {
                        LeftPower = (double) (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower")) - CorrectionAmount;
                        RightPower = (double) (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower")) + CorrectionAmount;
                    } else if (USDistance < TargetWallDistance - 1) {
                            LeftPower = (double) (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower")) + CorrectionAmount;
                            RightPower = (double) (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower")) - CorrectionAmount;
                    } else {
                        LeftPower = (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower"));
                        RightPower = (Control.AppReader.get("RunTeamMarkerDrive").variable("StraitPower"));
                    }

                    distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInReverse");
                    distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInReverse");


                    Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
                }
            }

            if (DriveStep == 2 && !PosDepot) {
                setDriveValues("StraitPower", "StraitPower", "CLeftIN", "CRightIN");
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

    //A handy conversion from distance on the field to motor ticks using the circumference of the wheal
    private int DistanceConverter(int distanceIN, int whealCircumference) {
        return (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }

    //these step specific definitions used to be individually done before every drive
    //we packaged it all into this method in response to a recommendation from a previous torment.
    private void setDriveValues(String leftPower, String rightPower, String INLeft, String INRight) {
        LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable(leftPower);
        RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable(rightPower);
        distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable(INLeft);
        distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable(INRight);
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

    //the telemetry method is called with the main op-mode loop to avoid weirdness in perpetually displaying telemetry
    @Override
    public void telemetry() {
        engine.telemetry.addLine("Running RunPark");
        engine.telemetry.addData("PinksDrive Step", DriveStep);
        engine.telemetry.addData("LeftDrive Power", LeftPower);
//        engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
//        engine.telemetry.addData("RightDrive Target IN", distanceINRight);
//        engine.telemetry.addData("LeftDrive Target IN", distanceINLeft);
//        engine.telemetry.addData("RightDrive Target Tick", distanceTicksRight);
//        engine.telemetry.addData("LeftDrive Target Tick", distanceTicksLeft);


        engine.telemetry.addData("LeftDrive Ultrasonic", LeftUSSensor.cmUltrasonic());
        //engine.telemetry.addData("RightDrive Ultrasonic", RightUSSensor.cmUltrasonic());
    }

}
