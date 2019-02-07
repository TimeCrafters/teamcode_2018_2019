package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.Paddle;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Crater")
public class AutoCraterDirect2 extends Engine {

  public org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration AppReader;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    AppReader = new StateConfiguration();

    addState(new DropRobot(this, AppReader, PinksHardwareConfig));
    addState(new Paddle(this, AppReader, PinksHardwareConfig, true));

    MineralPosId MPosId = (new MineralPosId(this, AppReader, PinksHardwareConfig));
    addState(MPosId);

//    addState(new PointTowardGold(this, MPosId, FileReader, PinksHardwareConfig));

    addSubEngine(new CMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));

//    addState(new CTurnToDepot(this, FileReader, PinksHardwareConfig));
//    addState(new CDriveToDepot(this, FileReader, PinksHardwareConfig));
//    addState(new ExtendArm(this, FileReader, PinksHardwareConfig));
//    addState(new PlaceMarker(this, FileReader, PinksHardwareConfig));
//    addState(new CDriveToCrater(this, FileReader, PinksHardwareConfig));


  }

}
