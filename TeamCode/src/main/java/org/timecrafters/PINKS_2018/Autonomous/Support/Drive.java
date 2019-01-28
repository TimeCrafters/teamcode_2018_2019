package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: Drive
 * Inputs: engine, pinksHardwareConfig, stepID
 * Outputs: none
 * Use: Drive the motors based on the drive variables from the phone
 **********************************************************************************************/

public class Drive extends State {
    private String StepID;
    public StateConfiguration AppReader;
    public PinksHardwareConfig PinksHardwareConfig;
    private double LeftPower;
    private double RightPower;
    private int LeftInches;
    private int RightInches;
    public DcMotor RightDrive;
    public DcMotor LeftDrive;
    private int RightCurrentTick;
    private int LeftCurrentTick;
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

        LeftPower = AppReader.get(StepID).variable("LeftPower");
        RightPower = AppReader.get(StepID).variable("RightPower");
        LeftInches = AppReader.get(StepID).variable("LeftIN");
        RightInches = AppReader.get(StepID).variable("RightIN");
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

            distanceTicksLeft = DistanceConverter(LeftInches, 4);
            distanceTicksRight = DistanceConverter(RightInches, 4);

            LeftDrive.setPower(LeftPower);
            RightDrive.setPower(RightPower);

            //run the motor until it reaches it's target
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

                setFinished(true);
            }

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

    private int DistanceConverter(int distanceIN, int WhealDiamiter) {
        return (int) ((distanceIN * 288) / (WhealDiamiter * Math.PI));
    }

}