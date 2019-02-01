package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: Drive
 * Inputs: engine, appReader, pinksHardwareConfig, stepID
 * Outputs: none
 * Use: Drive the motors based on the drive variables from the phone
 * History:
 * 1/31/19 - Added four wheel drive.
 * 1/29/19 - Converted PinksDrive into a single State to replace the many repetitive drive States
 * 1/12/19 - Added PinksDrive to necessary States
 * 1/5/19 - Created PinksDrive to use in States
 **********************************************************************************************/

public class Drive extends State {
    //The StepID refers to a set of values in a file we can edit on the phones. In this case, the
    //StepID determines what set of distance and power values are used for a given state.
    private String StepID;
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private double LeftPower;
    private double RightPower;
    private int LeftMM;
    private int RightMM;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private DcMotor FrontRightDrive;
    private DcMotor FrontLeftDrive;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int FrontRightCurrentTick;
    private int FrontLeftCurrentTick;
    private int distanceTicksRight;
    private int distanceTicksLeft;

    public Drive(Engine engine, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig, String stepID) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.StepID = stepID;
    }

    public void init() {
        RightDrive = PinksHardwareConfig.pRightMotor;
        LeftDrive = PinksHardwareConfig.pLeftMotor;
        FrontRightDrive = PinksHardwareConfig.pFrontRightMotor;
        FrontLeftDrive = PinksHardwareConfig.pFrontLeftMotor;

        LeftPower = AppReader.get(StepID).variable("LeftPower");
        RightPower = AppReader.get(StepID).variable("RightPower");
        LeftMM = AppReader.get(StepID).variable("LeftMM");
        RightMM = AppReader.get(StepID).variable("RightMM");
    }

    @Override
    public void exec() {
        //The AppReader reads the file we edit on the phones, allowing us to skip steps and edit
        // variables from the phone. "AppReader.allow" returns true or false depending on if we have a step
        // toggled on or off.
        if (AppReader.allow(StepID)) {
            engine.telemetry.addLine("Running Step"+StepID);

            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();
            FrontRightCurrentTick = FrontRightDrive.getCurrentPosition();
            FrontLeftCurrentTick = FrontLeftDrive.getCurrentPosition();

            distanceTicksLeft = DistanceConverter(LeftMM, 98);
            distanceTicksRight = DistanceConverter(RightMM, 98);


            FrontLeftDrive.setPower(LeftPower);
            FrontRightDrive.setPower(RightPower);

            //run each motor until they reach their targets
            if (Math.abs(RightCurrentTick) < distanceTicksRight) {
                RightDrive.setPower(RightPower);
            } else {
                RightDrive.setPower(0);
            }

            if (Math.abs(LeftCurrentTick) < distanceTicksLeft) {
                LeftDrive.setPower(LeftPower);
            } else {
                LeftDrive.setPower(0);
            }

            if (Math.abs(FrontLeftCurrentTick) < distanceTicksLeft) {
                FrontLeftDrive.setPower(LeftPower);
            } else {
                FrontLeftDrive.setPower(0);
            }

            if (Math.abs(FrontRightCurrentTick) < distanceTicksRight) {
                FrontRightDrive.setPower(RightPower);
            } else {
                FrontRightDrive.setPower(0);
            }


            if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft && Math.abs(FrontLeftCurrentTick) >= distanceTicksLeft && Math.abs(FrontRightCurrentTick) >= distanceTicksRight) {
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                setFinished(true);
            }

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

    private int DistanceConverter(int distanceMM, int WhealDiamiter) {
        return (int) ((distanceMM * 288) / (WhealDiamiter * Math.PI));
    }

}