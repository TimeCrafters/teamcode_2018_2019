package org.timecrafters.temptingreality.states;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 7/11/2018.
 */

public class revRovingRobot extends State {
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private CRServo servoElbow;
    
public boolean firstrun = true;
    public revRovingRobot(Engine engine)


    {
        this.engine = engine;

    }

    @Override
    public void init() {
        super.init();
      motor1 = engine.hardwareMap.dcMotor.get("leftDrive");
      motor2 = engine.hardwareMap.dcMotor.get("righDrive");
      motor3 = engine.hardwareMap.dcMotor.get("collectionArm");
      servoElbow = engine.hardwareMap.crservo.get("collectionElbow");
    }

    @Override
    public void exec() {
motor1.setPower(engine.gamepad1.left_stick_y*-1);
motor2.setPower(engine.gamepad1.right_stick_y);
motor3.setPower(engine.gamepad2.left_stick_y);
servoElbow.setPower(engine.gamepad2.right_stick_x);
    }
}