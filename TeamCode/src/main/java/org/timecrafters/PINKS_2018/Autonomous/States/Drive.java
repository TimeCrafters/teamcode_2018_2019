package org.timecrafters.PINKS_2018.Autonomous.States;

/**********************************************************************************************
 * Name: Drive
 * Inputs: engine, appReader, pinksHardwareConfig, stepID
 * Outputs: none
 * Use: Drive the motors based on the drive variables from the phone
 * History:
 * 2/1/19 - Added a deceleration when the drive approaches it's target
 * 1/31/19 - Added four wheel drive.
 * 1/29/19 - Converted PinksDrive into a single State to replace the many repetitive drive States
 * 1/12/19 - Added PinksDrive to necessary States
 * 1/5/19 - Created PinksDrive to use in States
 **********************************************************************************************/

import com.qualcomm.robotcore.hardware.DcMotor;
import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Drive extends State {
    //The StepID refers to a set of values in a file we can edit on the phones. In this case, the
    //StepID determines what set of distance and power values are used for a given state. Since
    //there are so may uses of the Drive state, their StepID's are named based on the path they
    //are used in: C and D for Crater and Depot and L, C, and R for Left, Center, and Right.
    private String StepID;
    public StateConfiguration FileReader;
    public org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig PinksHardwareConfig;
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

    public Drive(Engine engine, StateConfiguration fileReader, PinksHardwareConfig pinksHardwareConfig, String stepID) {
        this.engine = engine;
        this.FileReader = fileReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.StepID = stepID;
    }

    public void init() {
        RightDrive = PinksHardwareConfig.RightMotor;
        LeftDrive = PinksHardwareConfig.LeftMotor;
        FrontRightDrive = PinksHardwareConfig.FrontRightMotor;
        FrontLeftDrive = PinksHardwareConfig.FrontLeftMotor;

        LeftPower = FileReader.get(StepID).variable("LeftPower");
        RightPower = FileReader.get(StepID).variable("RightPower");
        LeftMM = FileReader.get(StepID).variable("LeftMM");
        RightMM = FileReader.get(StepID).variable("RightMM");
    }

    @Override
    public void exec() {
        //The FileReader reads the file we edit on the phones, allowing us to skip steps and edit
        //variables from the phone. "FileReader.allow" returns true or false depending on if we have
        //a step toggled on or off.
        if (FileReader.allow(StepID)) {


            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();
            FrontRightCurrentTick = FrontRightDrive.getCurrentPosition();
            FrontLeftCurrentTick = FrontLeftDrive.getCurrentPosition();

            distanceTicksLeft = DistanceConverter(LeftMM, 97);
            distanceTicksRight = DistanceConverter(RightMM, 97);


            //run each motor until they reach their targets. The power is reduced for the last
            //quarter of the journey to reduce the risk of being thrown off by inertia when the
            //robot stops.

            // Back Right
            if (Math.abs(RightCurrentTick) < distanceTicksRight - (0.25 * distanceTicksRight)) {
                RightDrive.setPower(RightPower);
            } else if (Math.abs(RightCurrentTick) < distanceTicksRight) {
                RightDrive.setPower(RightPower * 0.6);
            } else {
                RightDrive.setPower(0);
            }

            // Back Left
            if (Math.abs(LeftCurrentTick) < distanceTicksLeft - (0.25 * distanceTicksLeft)) {
                LeftDrive.setPower(LeftPower);
            } else if (Math.abs(LeftCurrentTick) < distanceTicksLeft) {
                LeftDrive.setPower(LeftPower * 0.6);
            } else {
                LeftDrive.setPower(0);

            }

            // Front Left
            if (Math.abs(FrontLeftCurrentTick) < distanceTicksLeft - (0.25 * distanceTicksLeft)) {
                FrontLeftDrive.setPower(LeftPower);
            } else if (Math.abs(FrontLeftCurrentTick) < distanceTicksLeft) {
                FrontLeftDrive.setPower(LeftPower * 0.6);
            } else {
                FrontLeftDrive.setPower(0);
            }

            // Back Right
            if (Math.abs(FrontRightCurrentTick) < distanceTicksRight - (0.25 * distanceTicksRight)) {
                FrontRightDrive.setPower(RightPower);
            } else if (Math.abs(FrontRightCurrentTick) < distanceTicksRight) {
                FrontRightDrive.setPower(RightPower * 0.6);
            } else {
                FrontRightDrive.setPower(0);
            }

            //When all motors reach their targets, reset encoders in prep for the next Drive and
            //finish the State
            if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft && Math.abs(FrontLeftCurrentTick) >= distanceTicksLeft && Math.abs(FrontRightCurrentTick) >= distanceTicksRight) {
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                setFinished(true);
            }

            engine.telemetry.addLine("Running Step"+StepID);
            engine.telemetry.addData("LeftPower", LeftPower);
            engine.telemetry.addData("RightPower", RightPower);
            engine.telemetry.addData("Left Distance Remaining", distanceTicksLeft - Math.abs(LeftCurrentTick));
            engine.telemetry.addData("Right Distance Remaining", distanceTicksRight - Math.abs(RightCurrentTick));

        } else {
            engine.telemetry.addLine("Skipping Step"+StepID);
            sleep(1000);
            setFinished(true);
        }
        engine.telemetry.update();
    }

    //A handy conversion from distance on the field to motor ticks using the circumference of the
    //wheal and the number of ticks in a rotation which is always 288
    private int DistanceConverter(int distanceMM, int WhealDiamiter) {
        return (int) ((distanceMM * 288) / (WhealDiamiter * Math.PI));
    }

}