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

public class revRovingRobotGame1 extends State {
    private DcMotor motor1;
    private DcMotor motor2;
    
public boolean firstrun = true;
    public revRovingRobotGame1(Engine engine)


    {
        this.engine = engine;

    }

    @Override
    public void init() {
        super.init();
      motor1 = engine.hardwareMap.dcMotor.get("leftDrive");
      motor2 = engine.hardwareMap.dcMotor.get("rightDrive");
    }

    @Override
    public void exec() {
motor1.setPower(engine.gamepad1.left_stick_y*-1);
motor2.setPower(engine.gamepad1.right_stick_y);

    }
}