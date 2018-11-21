package org.timecrafters.PINKS_2018.States;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step13OptionADriveToPark_fromMK extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
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


    public Step13OptionADriveToPark_fromMK(Engine engine, ArchitectureControl control) {
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
        if (Control.RunDriveToPark_fromMK) {

            if (DriveStep == 1) {

                LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftPowerTurn");
                RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable("RightPowerTurn");
                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInTurn");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInTurn");

                Drive(LeftPower, RightPower, distanceINLeft, distanceTicksRight);

            }

            if (DriveStep == 2) {

                LeftPower = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftPowerDrive");
                RightPower = Control.AppReader.get("RunTeamMarkerDrive").variable("RightPowerDrive");
                distanceINLeft = Control.AppReader.get("RunTeamMarkerDrive").variable("LeftInDrive");
                distanceINRight = Control.AppReader.get("RunTeamMarkerDrive").variable("RightInDrive");

                Drive(LeftPower, RightPower, distanceINLeft, distanceTicksRight);

            }

            if (DriveStep == 3) {
                Complete = true;
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step13OptionADriveToPark_fromMK");
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
            LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveStep++;

        }
    }


}
