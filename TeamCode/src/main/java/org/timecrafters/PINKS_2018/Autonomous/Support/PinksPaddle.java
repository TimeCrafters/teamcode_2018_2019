package org.timecrafters.PINKS_2018.Autonomous.Support;

import com.qualcomm.robotcore.hardware.CRServo;

public class PinksPaddle {
    public CRServo PaddleServo;
    private double CurrentPos;
    private double DeployPos;
    private double StorePos;
    private double Power;
    public boolean HasMovedToTarget;

    public PinksPaddle(PinksHardwareConfig pinksHardwareConfig, double deployPos, double storePos, double paddlePower ) {
        DeployPos = deployPos;
        StorePos = storePos;
        PaddleServo = pinksHardwareConfig.Paddle;
        Power = paddlePower;
    }

    public double getCurrentPos() {
        return PaddleServo.getController().getServoPosition(PaddleServo.getPortNumber());
    }

    //Puts the paddle into the deploy position. Must Be ran in Exec loop.
    public void Deploy() {
        if (getCurrentPos() < DeployPos) {
            PaddleServo.setPower(Power);
            HasMovedToTarget = false;
        } else {
            PaddleServo.setPower(0);
        }
    }

    public void Store() {
        if (getCurrentPos() > StorePos) {
            PaddleServo.setPower(Power);
            HasMovedToTarget = false;
        } else {
            PaddleServo.setPower(0);
        }
    }

    public boolean hasStopped() {
        return (PaddleServo.getPower() == 0);
    }
}
