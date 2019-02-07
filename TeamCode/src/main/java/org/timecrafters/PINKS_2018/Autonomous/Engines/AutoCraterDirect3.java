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
import org.timecrafters.PINKS_2018.Autonomous.States.Paddle;
import org.timecrafters.PINKS_2018.Autonomous.States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Crater 3")
public class AutoCraterDirect3 extends Engine {

  public PinksHardwareConfig PinksHardwareConfig;
  public StateConfiguration FileReader;

  //"setProcesses" Runs through each State in the order they are added

  @Override
  public void setProcesses() {

    PinksHardwareConfig = new PinksHardwareConfig(this);
    FileReader = new StateConfiguration();

    addState(new DropRobot(this, FileReader, PinksHardwareConfig));
    addState(new Paddle(this, FileReader, PinksHardwareConfig, true));

    MineralPosId MPosId = (new MineralPosId(this, FileReader, PinksHardwareConfig));
    addState(MPosId);

    //SubEnginges are like Engines that go inside Engines that can be turned on and off.
    addSubEngine(new CMineralPathCenter(this, MPosId, FileReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathLeft(this, MPosId, FileReader, PinksHardwareConfig));
    addSubEngine(new CMineralPathRight(this, MPosId, FileReader, PinksHardwareConfig));


  }

}
