package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.greece.states.BlinkinLedDriverCyberarmState;

@TeleOp(name = "Blinkin", group ="Testing")
public class BlinkinLedDriver extends CyberarmEngine {
  @Override
  public void setup() {
    addState(new BlinkinLedDriverCyberarmState());
  }
}
