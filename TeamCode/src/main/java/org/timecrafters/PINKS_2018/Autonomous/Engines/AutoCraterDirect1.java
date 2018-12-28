package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.Autonomous.States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.Step07PostDropUTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.Step08DriveToDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.Step09MineralDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.Step09MineralDetectV2;
import org.timecrafters.PINKS_2018.Autonomous.States.Step10MineralKick;
import org.timecrafters.PINKS_2018.Autonomous.States.Step11TeamMarkerDrive;
import org.timecrafters.PINKS_2018.Autonomous.States.Step11TeamMarkerDriveV2;
import org.timecrafters.PINKS_2018.Autonomous.States.Step12TeamMarkerPlace;
import org.timecrafters.PINKS_2018.Autonomous.States.Step13OptionADriveToPark_fromTMP;
import org.timecrafters.PINKS_2018.Autonomous.States.Step13OptionADriveToPark_fromTMPV2;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Crater")
public class AutoCraterDirect1 extends Engine {

  @Override
  public void setProcesses() {

    ArchitectureControl control = (new ArchitectureControl(this));
    addState(control);

    addState(new Step06DropRobot(this, control));
    Step09MineralDetectV2 Scan = new Step09MineralDetectV2(this, control);
    addState(Scan);
    addState(new Step07PostDropUTurn(this, control));
    addState(new Step10MineralKick(this, control));
    addState(new Step11TeamMarkerDriveV2(this, control, false));
    addState(new Step12TeamMarkerPlace(this, control));
    addState(new Step13OptionADriveToPark_fromTMPV2(this, control, false));


  }

}
