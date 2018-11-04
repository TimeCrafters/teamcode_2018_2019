package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1.ArchitectureControl;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous Position: Depot")
public class AutoDepot1 extends Engine {

    @Override
    public void setProcesses() {

        ArchitectureControl control = (new ArchitectureControl(this));
        addState(control);

        addState(new DropRobot(this, control));
        addState(new PostDropUTurn(this, control));
        addState(new DriveToDetect(this, control));
        addState(new MineralDetect(this, control));
        addState(new MineralKick(this, control));
        addState(new TeamMarkerDrive(this, control));
        addState(new TeamMarkerPlace(this, control));
        addState(new DriveToPark_fromMK(this, control));
        addState(new DriveToPark_fromTMP( this, control));

    }
}
