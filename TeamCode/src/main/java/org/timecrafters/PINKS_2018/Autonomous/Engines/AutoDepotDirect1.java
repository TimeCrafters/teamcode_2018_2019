package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step11TeamMarkerDriveV2;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step13OptionADriveToPark_fromTMPV2;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step08DriveToDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step09MineralDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step07PostDropUTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step12TeamMarkerPlace;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Depot")
public class AutoDepotDirect1 extends Engine {

  @Override
  public void setProcesses() {

    ArchitectureControl control = (new ArchitectureControl(this));
    addState(control);

    addState(new Step06DropRobot(this, control));
    addState(new Step07PostDropUTurn(this, control));
    addState(new Step08DriveToDetect(this, control));
    Step09MineralDetect Scan = new Step09MineralDetect(this, control);
    addState(Scan);

    addState(new Step11TeamMarkerDriveV2(this, control, true));
    addState(new Step12TeamMarkerPlace(this, control));
    addState(new Step13OptionADriveToPark_fromTMPV2(this, control, true));


  }

}
