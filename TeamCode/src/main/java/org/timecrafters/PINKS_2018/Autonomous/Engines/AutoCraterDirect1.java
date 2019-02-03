package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step07PostDropUTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step07MineralDetectV2;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step10MineralKick;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step11TeamMarkerDriveV2;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step12TeamMarkerPlace;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States.Step13OptionADriveToPark_fromTMPV2;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous: Crater")
public class AutoCraterDirect1 extends Engine {

  @Override
  public void setProcesses() {

    ArchitectureControl control = (new ArchitectureControl(this));
    addState(control);

    addState(new Step06DropRobot(this, control));
    Step07MineralDetectV2 Scan = new Step07MineralDetectV2(this, control);
    addState(Scan);
    addState(new Step07PostDropUTurn(this, control));
    addState(new Step10MineralKick(this, control, Scan));
    addState(new Step11TeamMarkerDriveV2(this, control, false));
    addState(new Step12TeamMarkerPlace(this, control));
    addState(new Step13OptionADriveToPark_fromTMPV2(this, control, false));


  }

}
