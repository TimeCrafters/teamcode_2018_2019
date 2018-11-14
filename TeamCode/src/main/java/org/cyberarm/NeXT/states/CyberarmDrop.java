package org.cyberarm.NeXT.states;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmDrop extends CyberarmStateV2 {
    StateConfiguration config;

  @Override
  public void init() {
    config = new StateConfiguration();
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
      cyberarmEngine.telemetry.addData("Drop", "" + config.get_variable("RunDropRobot", "string"));
    } catch (NullPointerException e) {
      cyberarmEngine.telemetry.addData("Drop", "string is MISSING!");
    }
  }
}
