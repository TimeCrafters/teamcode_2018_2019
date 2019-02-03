package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class PinksDrive {
    public DcMotor RightDrive;
    public DcMotor LeftDrive;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceTicksRight;
    private int distanceTicksLeft;


    public PinksDrive(PinksHardwareConfig pinksHardwareConfig) {
        LeftDrive = pinksHardwareConfig.LeftMotor;
        RightDrive = pinksHardwareConfig.RightMotor;
        RightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    //A handy conversion from distance on the field to motor ticks using the circumference of the wheal
    //and the number of ticks in a rotation which is always 288
    private int DistanceConverter(int distanceMM, int WhealDiamiter) {
        return (int) ((distanceMM * 288) / (WhealDiamiter * Math.PI));
    }

    //The main PinksDrive method
    public void go(double leftPower, double rightPower, int leftMM, int rightMM) {

        RightCurrentTick = RightDrive.getCurrentPosition();
        LeftCurrentTick = LeftDrive.getCurrentPosition();

        distanceTicksLeft = DistanceConverter(leftMM, 98);
        distanceTicksRight = DistanceConverter(rightMM, 98);

        LeftDrive.setPower(leftPower);
        RightDrive.setPower(rightPower);

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
        if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
            reset();
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}
