package org.timecrafters.cyberarm.states;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmStateV2;
import org.timecrafters.cyberarm.engines.CraneDriver;

public class CraneDriverState extends CyberarmStateV2 {
    public CRServo crRotate, crHorizontal, crVertical;
    String rotateID = "rotate", horizontalID = "hor", verticalID = "ver";

    @Override
    public void init() {
        crRotate     = cyberarmEngine.hardwareMap.crservo.get(rotateID);
        crHorizontal = cyberarmEngine.hardwareMap.crservo.get(horizontalID);
        crVertical   = cyberarmEngine.hardwareMap.crservo.get(verticalID);

        crRotate.setDirection(DcMotorSimple.Direction.FORWARD);
        crHorizontal.setDirection(DcMotorSimple.Direction.FORWARD);
        crVertical.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void exec() {
        crRotate.setPower(cyberarmEngine.gamepad1.left_stick_x);
        crHorizontal.setPower(cyberarmEngine.gamepad1.right_stick_x);
        crVertical.setPower(cyberarmEngine.gamepad1.right_stick_y);
    }

    @Override
    public void telemetry() {
        cyberarmEngine.telemetry.addData("Controls", "Orbit -> Left stick X axis");
        cyberarmEngine.telemetry.addData("Controls", "Horizontal function -> Right stick X axis");
        cyberarmEngine.telemetry.addData("Controls", "Vertical lift -> Right stick Y axis");

        cyberarmEngine.telemetry.addLine("");

        cyberarmEngine.telemetry.addData("Rotation Speed",   crRotate.getPower());
        cyberarmEngine.telemetry.addData("Horizontal Speed", crHorizontal.getPower());
        cyberarmEngine.telemetry.addData("Vertical Speed",   crVertical.getPower());
    }
}
