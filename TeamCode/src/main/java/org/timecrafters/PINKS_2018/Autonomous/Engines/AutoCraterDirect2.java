package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step08PointTowardGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step12CTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step13CDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step14CDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step14DPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step15DDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Crater")
public class AutoCraterDirect2 extends Engine {

  public org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration AppReader;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    AppReader = new StateConfiguration();

    addState(new Step06DropRobot(this, AppReader, PinksHardwareConfig));

    Step07MineralPosId MPosId = (new Step07MineralPosId(this, AppReader, PinksHardwareConfig));
    addState(MPosId);

    addState(new Step08PointTowardGold(this, MPosId, AppReader, PinksHardwareConfig));

    addSubEngine(new CMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));

    addState(new Step12CTurnToDepot(this, AppReader, PinksHardwareConfig));
    addState(new Step13CDriveToDepot(this, AppReader, PinksHardwareConfig));
    addState(new StepPlaceMarker(this, AppReader, PinksHardwareConfig));
    addState(new Step14CDriveToCrater(this, AppReader, PinksHardwareConfig));


  }

}
