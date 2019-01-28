package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DeployPaddle;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.PointTowardGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Crater 3")
public class AutoCraterDirect3 extends Engine {

  public org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration AppReader;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    AppReader = new StateConfiguration();

    addState(new DropRobot(this, AppReader, PinksHardwareConfig));
    addState(new DeployPaddle(this, AppReader, PinksHardwareConfig));

    MineralPosId MPosId = (new MineralPosId(this, AppReader, PinksHardwareConfig));
    addState(MPosId);

    addState(new PointTowardGold(this, MPosId, AppReader, PinksHardwareConfig));

    addSubEngine(new CMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));

    addState(new CTurnToDepot(this, AppReader, PinksHardwareConfig));
    addState(new CDriveToDepot(this, AppReader, PinksHardwareConfig));
    addState(new StepExtendArm(this, AppReader, PinksHardwareConfig));
    addState(new StepPlaceMarker(this, AppReader, PinksHardwareConfig));
    addState(new CDriveToCrater(this, AppReader, PinksHardwareConfig));


  }

}
