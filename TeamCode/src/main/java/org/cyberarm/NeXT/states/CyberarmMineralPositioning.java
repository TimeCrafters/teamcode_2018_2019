package org.cyberarm.NeXT.states;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmMineralPositioning extends CyberarmStateV2 {
  @Override
  public void exec() {
    sleep(2500);
    setHasFinished(true);
  }
}
