package org.cyberarm.engineV2Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.engine.V2.CyberarmEngineV2;
@Disabled
@Autonomous(name = "CyberarmEngineV2 TEST", group = "Testing")
public class TestEngineV2 extends CyberarmEngineV2 {
  @Override
  public void setup() {
    addState(new TopLevelState("One"));
    addState(new TopLevelState("Two"));
  }
}
