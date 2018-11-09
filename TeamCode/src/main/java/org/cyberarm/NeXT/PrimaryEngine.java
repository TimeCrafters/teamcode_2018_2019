package org.cyberarm.NeXT;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.states.CyberarmDrop;
import org.cyberarm.NeXT.states.CyberarmMineralPositioning;
import org.cyberarm.NeXT.states.CyberarmPanic;
import org.cyberarm.NeXT.states.CyberarmPostDropUTurn;
import org.cyberarm.engine.V2.CyberarmEngineV2;

@TeleOp(name = "Testing->PrimaryEngine", group = "Testing")
public class PrimaryEngine extends CyberarmEngineV2 {
 StateConfiguration config;

  @Override
  public void init() {
    loadConfig();

    super.init();
  }

  private void loadConfig() {
    config = new StateConfiguration();
  }

  @Override
  public void setup() {
    if (config.wasLoadSuccessful()) {
      if (config.allow("RunDropRobot")) {
        addState(new CyberarmDrop());
      }
      if (config.allow("RunPostDropUTurn")) {
        addState(new CyberarmPostDropUTurn());
      }
      if (config.allow("RunDriveToDetect")) {
        addState(new CyberarmMineralPositioning());
      }
    } else {
      addState(new CyberarmPanic("JSON FAILED TO LOAD!"));
    }
  }
}
