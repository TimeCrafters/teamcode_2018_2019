package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step08PointTowardGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step14DPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step15DDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Depot")
public class AutoDepotDirect2 extends Engine {

  public PinksHardwareConfig PinksHardwareConfig;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);

    addState(new Step06DropRobot(this));

    Step07MineralPosId MPosId = (new Step07MineralPosId(this));
    addState(MPosId);

    addState(new Step08PointTowardGold(this, MPosId));

    addSubEngine(new DMineralPathCenter(this, MPosId));
    addSubEngine(new DMineralPathLeft(this, MPosId));
    addSubEngine(new DMineralPathRight(this, MPosId));

    addState(new Step14DPointToCrater(this));
    addState(new Step15DDriveToCrater(this));




  }

}
