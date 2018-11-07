package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.AutoDepot1;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous Position: Depot")
public class Auto_Depot_1 extends Engine {

    @Override
    public void setProcesses() {

        AutoDepot1.ArchitectureControl architectureControl = (new AutoDepot1.ArchitectureControl(this));
        addState(architectureControl);

        addSubEngine(new DropRobot(this, architectureControl));
        addSubEngine(new PostDropUTurn(this, architectureControl));
        addSubEngine(new DriveToDetect_(this, architectureControl));
        addSubEngine(new MineralDetect(this, architectureControl));
        addSubEngine(new MineralKick(this, architectureControl));
        addSubEngine(new TeamMarkerDrive(this, architectureControl));
        addSubEngine(new TeamMarkerPlace(this, architectureControl));
        addSubEngine(new DriveToPark_fromTMP(this, architectureControl));
        addSubEngine(new DriveToPark_fromMK(this, architectureControl));
    }
}
