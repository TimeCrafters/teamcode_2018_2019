package org.cyberarm.NeXT.states;

import org.cyberarm.NeXT.StateConfiguration;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmDrop extends CyberarmStateV2 {
  StateConfiguration config;

  @Override
  public void init() {
    config = new StateConfiguration();
//    Servo leftServo  = cyberarmEngine.hardwareMap.servo.get("leftServo");
//    Servo rightServo = cyberarmEngine.hardwareMap.servo.get("rightServo");
  }

  @Override
  public void exec() {
    sleep(2500);
    setHasFinished(true);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("Drop", "Dropping...");
    try {
      cyberarmEngine.telemetry.addData("Drop", "" + config.get("RunDropRobot").variable("string"));
    } catch (NullPointerException e) {
      cyberarmEngine.telemetry.addData("Drop", "string is MISSING!");
    }
  }
}
