package org.timecrafters.PINKS_2018.Autonomous.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: MineralKick
 * Inputs: engine, ArchitectureControl, scan
 * Outputs: none
 * Use: kicks the gold mineral and drives to the end of the mineral row if necessary
 **********************************************************************************************/


public class Step10MineralKick extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    public Step09MineralDetect Scan;
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



    public Step10MineralKick(Engine engine, ArchitectureControl control, Step09MineralDetect scan) {
        this.engine = engine;
        this.Control = control;
        this.Scan = scan;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;
        FirstRun = true;
    }

    @Override
    public void exec() {
        if (Control.RunMineralKick) {
            if (FirstRun) {
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                FirstRun = false;
            }

            if (Scan.isGold) {

                if (Scan.ScanNumber == 1) {


                }

                if (Scan.ScanNumber == 2) {

                }

                if (Scan.ScanNumber == 3) {

                }
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step10MineralKick");
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
        engine.telemetry.addData("Is Gold?", Scan.isGold);
        engine.telemetry.addData("Scan #", Scan.ScanNumber);
    }
}
