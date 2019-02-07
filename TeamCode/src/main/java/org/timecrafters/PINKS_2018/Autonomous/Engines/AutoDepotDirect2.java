package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.Paddle;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Depot")
public class AutoDepotDirect2 extends Engine {

  public PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration AppReader;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    AppReader = new StateConfiguration();

    addState(new DropRobot(this, AppReader, PinksHardwareConfig));
    addState(new Paddle(this, AppReader, PinksHardwareConfig, true));

    MineralPosId MPosId = (new MineralPosId(this, AppReader, PinksHardwareConfig));
    addState(MPosId);

    //addState(new PointTowardGold(this, MPosId, FileReader, PinksHardwareConfig));

    addSubEngine(new DMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));

//    addState(new DPointToCrater(this, FileReader, PinksHardwareConfig));
//    addState(new DDriveToCrater(this, FileReader, PinksHardwareConfig));




  }

}
