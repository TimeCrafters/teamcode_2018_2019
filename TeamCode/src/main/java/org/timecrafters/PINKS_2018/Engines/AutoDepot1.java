package org.timecrafters.PINKS_2018.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.States.Step13OptionBDriveToPark_fromTMP;
import org.timecrafters.PINKS_2018.Support.ArchitectureControl;
import org.timecrafters.PINKS_2018.States.Step08DriveToDetect;
import org.timecrafters.PINKS_2018.States.Step13OptionADriveToPark_fromMK;
import org.timecrafters.PINKS_2018.States.Step06DropRobot;
import org.timecrafters.PINKS_2018.States.Step09MineralDetect;
import org.timecrafters.PINKS_2018.States.Step10MineralKick;
import org.timecrafters.PINKS_2018.States.Step07PostDropUTurn;
import org.timecrafters.PINKS_2018.States.Step11TeamMarkerDrive;
import org.timecrafters.PINKS_2018.States.Step12TeamMarkerPlace;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous Position: Depot")
public class AutoDepot1 extends Engine {

    @Override
    public void setProcesses() {

        ArchitectureControl control = (new ArchitectureControl(this));
        addState(control);

        addState(new Step06DropRobot(this, control));
        addState(new Step07PostDropUTurn(this, control));
        addState(new Step08DriveToDetect(this, control));
        addState(new Step09MineralDetect(this, control));
        addState(new Step10MineralKick(this, control));
        addState(new Step11TeamMarkerDrive(this, control));
        addState(new Step12TeamMarkerPlace(this, control));
        addState(new Step13OptionADriveToPark_fromMK(this, control));
        addState(new Step13OptionBDriveToPark_fromTMP( this, control));

    }

}
