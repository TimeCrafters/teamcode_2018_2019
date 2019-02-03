package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: PostDropUTurn
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: drives the robot in an arc that leaves it parallel with the minerals
 **********************************************************************************************/

public class Step07PostDropUTurn extends State {
  private boolean Complete = false;
  private boolean FirstRun;
  public ArchitectureControl Control;
  private DcMotor RightDrive;
  private DcMotor LeftDrive;
  private int RightCurrentTick;
  private int LeftCurrentTick;
  private int distanceINRight;
  private int distanceINLeft;
  private int distanceTicksRight;
  private int distanceTicksLeft;
  private double RightPower;
  private double LeftPower;



  public Step07PostDropUTurn(Engine engine, ArchitectureControl control) {
    this.engine = engine;
    this.Control = control;

  }

  public void init() {
    LeftDrive = Control.PinksHardwareConfig.LeftMotor;
    RightDrive = Control.PinksHardwareConfig.RightMotor;
    RightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    //these are were we use the variables we edit on the phone
    distanceINLeft = Control.AppReader.get("RunPostDropUTurn").variable("LeftIN");
    distanceINRight = Control.AppReader.get("RunPostDropUTurn").variable("RightIN");
    LeftPower = Control.AppReader.get("RunPostDropUTurn").variable("LeftPower");
    RightPower = Control.AppReader.get("RunPostDropUTurn").variable("RightPower");

    FirstRun = true;

  }

  @Override
  public void exec() {
    if (Control.RunPostDropUTurn) {

      if (FirstRun) {
        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FirstRun = false;
      }


      Drive(-LeftPower, -RightPower, distanceINLeft, distanceINRight);

      if (Complete) {
        engine.telemetry.addLine("Completed Step07PostDropUTurn");
        engine.telemetry.update();
        setFinished(true);
      }
    } else {
      setFinished(true);
    }
  }

  //A handy conversion from distance on the field to motor ticks using the circumference of the wheal
  private int DistanceConverter(int distanceIN, int WhealDiamiter) {
    return (int) ((distanceIN * 288) / (WhealDiamiter * Math.PI));
  }


  private void Drive(double LeftPower, double RightPower, int distanceINLeft, int distanceINRight) {

    RightCurrentTick = RightDrive.getCurrentPosition();
    LeftCurrentTick = LeftDrive.getCurrentPosition();

    distanceTicksLeft = DistanceConverter(distanceINLeft, 4);
    distanceTicksRight = DistanceConverter(distanceINRight, 4);

    LeftDrive.setPower(LeftPower);
    RightDrive.setPower(RightPower);
    //run the motor until it reaches it's target
    if (Math.abs(RightCurrentTick) >= distanceTicksRight) {
      RightDrive.setPower(0);
    }

    if (Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
      LeftDrive.setPower(0);
    }
    //when both motors reach their target, it moves to the next step before resetting the encoders
    if (Math.abs(RightCurrentTick) >= distanceTicksRight && Math.abs(LeftCurrentTick) >= distanceTicksLeft) {
      Complete = true;


      LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
  }

  //the telemetry method is called with the main op-mode loop to avoid weirdness in perpetually displaying telemetry
  @Override
  public void telemetry() {
    engine.telemetry.addData("LeftCurrentTick", LeftCurrentTick);
    engine.telemetry.addData("RightCurrentTick", RightCurrentTick);
    engine.telemetry.addData("distanceTicksRight", distanceTicksRight);
    engine.telemetry.addData("distanceTicksLeft", distanceTicksLeft);
    engine.telemetry.addData("RightPower", RightDrive.getPower());
    engine.telemetry.addData("LeftPower", LeftDrive.getPower());
    engine.telemetry.addData("RunMode", RightDrive.getMode());
  }
}