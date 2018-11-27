package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.Autonomous.States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.Step07PostDropUTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.Step08DriveToDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.Step09MineralDetect;
import org.timecrafters.PINKS_2018.Autonomous.States.Step10MineralKick;
import org.timecrafters.PINKS_2018.Autonomous.States.Step11TeamMarkerDrive;
import org.timecrafters.PINKS_2018.Autonomous.States.Step12TeamMarkerPlace;
import org.timecrafters.PINKS_2018.Autonomous.States.Step13OptionADriveToPark_fromTMP;
import org.timecrafters.PINKS_2018.Autonomous.States.Step13OptionBDriveToPark_fromMK;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Crater")
public class AutoCrater1 extends Engine {

    @Override
    public void setProcesses() {

        ArchitectureControl control = (new ArchitectureControl(this));
        addState(control);

        addState(new Step06DropRobot(this, control));
        addState(new Step07PostDropUTurn(this, control));
        addState(new Step08DriveToDetect(this, control));
        Step09MineralDetect Scan = new Step09MineralDetect(this, control);
        addState(Scan);
        addState(new Step10MineralKick(this, control, Scan));
        addState(new Step11TeamMarkerDrive(this, control, false));
        addState(new Step12TeamMarkerPlace(this, control));
        addState(new Step13OptionADriveToPark_fromTMP(this, control, false));
        addState(new Step13OptionBDriveToPark_fromMK( this, control));

    }

}
