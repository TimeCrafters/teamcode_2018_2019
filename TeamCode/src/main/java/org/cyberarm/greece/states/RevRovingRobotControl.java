package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.container.InputChecker;
import org.cyberarm.engine.CyberarmState;
import org.cyberarm.greece.statues.LaserObjectDetector;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class RevRovingRobotControl extends CyberarmState {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private final InputChecker inputChecker;

  private double speedKP = 1.0;
  private double steeringKP = 0.5;
  private double drivePower;

  private DistanceSensor laserDistanceSensor0,
                         laserDistanceSensor1,
                         laserDistanceSensor2,
                         laserDistanceSensor3;
  private ArrayList<DistanceSensor> distanceSensors;

  private LaserObjectDetector laserObjectDetector;

  private int block = 22, // MM // 45 for Flat face, ~22 for waffle
              sphere= 65, // MM
              distanceKP=5;//MM
  private long lastUpdateMS = System.currentTimeMillis();

  public RevRovingRobotControl() {
    leftDrive  = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = cyberarmEngine.hardwareMap.servo.get("svSteering");
    inputChecker = new InputChecker(cyberarmEngine.gamepad1);

    laserObjectDetector = new LaserObjectDetector(cyberarmEngine);
  }

  @Override
  public void exec() {
    inputChecker.update();
    drivePower = cyberarmEngine.gamepad1.left_stick_y * speedKP;

    laserObjectDetector.update();

    leftDrive.setPower(drivePower);
    rightDrive.setPower(drivePower);

    steering.setPosition((cyberarmEngine.gamepad1.right_stick_x + steeringKP) * -1);

    if (inputChecker.check("a")) {
      speedKP += 0.1;
    } else if (inputChecker.check("b")) {
      speedKP -=0.1;
    }

    speedKP = Range.clip(speedKP, 0.1, 1.0);

    lastUpdateMS = System.currentTimeMillis();
  }


  @Override
  public void telemetry() {
    boolean foundBlock = false;
    boolean foundSphere = false;
    double highestPoint = 0;

    cyberarmEngine.telemetry.addLine();

    cyberarmEngine.telemetry.addLine("Rev Roving Robot");
    cyberarmEngine.telemetry.addData("LeftDrive PinksDrive Position", leftDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("RightDrive PinksDrive Position", rightDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Steering Position", steering.getPosition());

    cyberarmEngine.telemetry.addLine();
    cyberarmEngine.telemetry.addData("Last loop took", System.currentTimeMillis()-lastUpdateMS+"ms");
    cyberarmEngine.telemetry.addLine();

    laserObjectDetector.telemetry(cyberarmEngine);

//    cyberarmEngine.telemetry.addLine();
//    for (int i = 0; i < distanceSensors.size(); i++) {
//      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, mmDistance(distanceSensors.get(i)));
//    }
//
//    cyberarmEngine.telemetry.addLine();

//    for (int i = 0; i < distanceSensors.size(); i++) {
//      double d = (mmDistance(distanceSensors.get(i))-average)*-1;
//      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, d);
//      if (d > highestPoint) { highestPoint = d; }
//    }

//    cyberarmEngine.telemetry.addLine();

//    for (int i = 0; i < distanceSensors.size(); i++) {
//      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, (mmDistance(distanceSensors.get(i))-averageDistance())*-1);
//    }
//    cyberarmEngine.telemetry.addData("Average Distance (1 sample)", average);
//
//    cyberarmEngine.telemetry.addLine();
//    cyberarmEngine.telemetry.addData("Highest Point", highestPoint);
//
//    if (sphere >= (highestPoint - distanceKP) && sphere <= (highestPoint + distanceKP)) {
//      foundSphere = true;
//    } else if (block >= (highestPoint - distanceKP) && block <= (highestPoint + distanceKP)) {
//      foundBlock = true;
//    }
//    cyberarmEngine.telemetry.addData("Block Found", foundBlock);
//    cyberarmEngine.telemetry.addData("Sphere Found", foundSphere);
  }

  private int distanceInMMToTicks(double distanceMM) {
    int oneRotation = 288; // ticks per full rotation on Rev Core Hex Motor
    double wheelRadius = 50.8; // mm
    double wheelCircumference = 319.2; // mm
    double oneMM = wheelCircumference / 288;

    return (0);
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
