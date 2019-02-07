package org.timecrafters.PINKS_2018.Autonomous.Engines;

/**********************************************************************************************
 * Name: AutoDepotDirect
 * Use: Autonomous Program for Depot Side
 * History:
 * 1/29/19 - replaced repetitive states with the "Drive" state
 * 1/26/19 - edited power and distance variables on the phone file
 * 1/24/19 - added and reorganized States to follow a more specific drive path.
 * 1/10/19 - fixed issue of every States creating a new instance of PinksHardwareConfig
 * 1/1/19 - Created and assembled States and SubEngines.
 * 12/29/18 - Restart From Scratch
 **********************************************************************************************/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.Paddle;
import org.timecrafters.PINKS_2018.Autonomous.States.DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2.DMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Depot 3")
public class AutoDepotDirect3 extends Engine {

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

    //SubEnginges are like Engines that go inside other Engines and can be turned on and off.
    addSubEngine(new DMineralPathCenter(this, MPosId, FileReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathLeft(this, MPosId, FileReader, PinksHardwareConfig));
    addSubEngine(new DMineralPathRight(this, MPosId, FileReader, PinksHardwareConfig));



  }

}
