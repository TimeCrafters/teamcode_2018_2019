package org.cyberarm.NeXT.states;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmPostDropUTurn extends CyberarmStateV2 {
  @Override
  public void exec() {
    sleep(4000);
    setHasFinished(true);
  }
}
