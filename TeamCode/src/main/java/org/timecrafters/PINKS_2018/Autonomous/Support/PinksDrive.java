package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class PinksDrive {
    public int LeftInches;
    public int RightInches;
    public double LeftPower;
    public double RightPower;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceTicksRight;
    private int distanceTicksLeft;


    public void initialize(PinksHardwareConfig pinksHardwareConfig) {
        LeftDrive = pinksHardwareConfig.pLeftMotor;
        RightDrive = pinksHardwareConfig.pRightMotor;
        RightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    //A handy conversion from distance on the field to motor ticks using the circumference of the wheal
    //and the number of ticks in a rotation which is always 288
    private int DistanceConverter(int distanceIN, int WhealDiamiter) {
        return (int) ((distanceIN * 288) / (WhealDiamiter * Math.PI));
    }

    //The main PinksDrive method
    public void go() {

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
    }

    public boolean HasReachedTarget() {
        //when both motors reach their target, return true
        return (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft);
    }

    public void reset() {
        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
