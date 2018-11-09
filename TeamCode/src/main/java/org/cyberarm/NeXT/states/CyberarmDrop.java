package org.cyberarm.NeXT.states;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmDrop extends CyberarmStateV2 {
  @Override
  public void exec() {
    sleep(2500);
    setHasFinished(true);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("Drop", "Dropping...");
  }
}
