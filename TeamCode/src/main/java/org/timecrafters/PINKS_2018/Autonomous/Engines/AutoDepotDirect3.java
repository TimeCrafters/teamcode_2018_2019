package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DeployPaddle;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.PointTowardGold;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.Drive;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Depot 3")
public class AutoDepotDirect3 extends Engine {

  public PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration AppReader;

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    AppReader = new StateConfiguration();

    addState(new DropRobot(this, AppReader, PinksHardwareConfig));
    addState(new DeployPaddle(this, AppReader, PinksHardwareConfig));

    MineralPosId MPosId = (new MineralPosId(this, AppReader, PinksHardwareConfig));
    addState(MPosId);

    addState(new Drive(this, AppReader, PinksHardwareConfig, "TurnToGold"));

    addSubEngine(new DMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));

    addState(new DPointToCrater(this, AppReader, PinksHardwareConfig));
    addState(new DDriveToCrater(this, AppReader, PinksHardwareConfig));




  }

}
