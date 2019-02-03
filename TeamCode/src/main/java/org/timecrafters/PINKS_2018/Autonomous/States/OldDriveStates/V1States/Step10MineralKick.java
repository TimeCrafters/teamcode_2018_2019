package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

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
    public Step07MineralDetectV2 Scan;
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



    public Step10MineralKick(Engine engine, ArchitectureControl control, Step07MineralDetectV2 scan) {
        this.engine = engine;
        this.Control = control;
        this.Scan = scan;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.LeftMotor;
        RightDrive = Control.PinksHardwareConfig.RightMotor;
        FirstRun = true;

        LeftPower = Control.AppReader.get("MineralKick").variable("Power");
        RightPower = Control.AppReader.get("MineralKick").variable("Power");
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

            if (Scan.GoldPosition == 1) {
                distanceINLeft = Control.AppReader.get("MineralKick").variable("Pos1Distance");
                distanceINRight = Control.AppReader.get("MineralKick").variable("Pos1Distance");
                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (Scan.GoldPosition == 2) {
                distanceINLeft = Control.AppReader.get("MineralKick").variable("Pos2Distance");
                distanceINRight = Control.AppReader.get("MineralKick").variable("Pos2Distance");
                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
            }

            if (Scan.GoldPosition == 3) {
                distanceINLeft = Control.AppReader.get("MineralKick").variable("Pos3Distance");
                distanceINRight = Control.AppReader.get("MineralKick").variable("Pos3Distance");
                Drive(LeftPower, RightPower, distanceINLeft, distanceINRight);
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

    }
}
