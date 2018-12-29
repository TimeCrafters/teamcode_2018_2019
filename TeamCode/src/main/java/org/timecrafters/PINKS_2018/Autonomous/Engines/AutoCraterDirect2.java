package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step08PointTowardGold;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Crater")
public class AutoCraterDirect2 extends Engine {

  @Override
  public void setProcesses() {

    addState(new Step06DropRobot(this));
    Step07MineralPosId MPosId = (new Step07MineralPosId(this));

    addState(new Step08PointTowardGold(this));




  }

}
