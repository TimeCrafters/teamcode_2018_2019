package org.timecrafters.PINKS_2018.Autonomous.Engines;

/**********************************************************************************************
 * Name: AutoCraterDirect
 * Use: Autonomous Program for Crater Side
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/27/19 - added and reorganized States and edited power and distance variables on the phone file.
 * 1/10/19 - fixed issue of every states creating a new instance of PinksHardwareConfig
 * 1/3/19 - Created and assembled Crater States and SubEngines.
 * 12/29/18 - Restart From Scratch
 **********************************************************************************************/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.DeployPaddle;
import org.timecrafters.PINKS_2018.Autonomous.States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;

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

    addSubEngine(new CMineralPathCenter(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathLeft(this, MPosId, AppReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathRight(this, MPosId, AppReader, PinksHardwareConfig));


  }

}
