package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Autonomous Position: Depot - Alt")
public class Auto_Depot_1 extends Engine {

    @Override
    public void setProcesses() {

        ArchitectureControl architectureControl = (new ArchitectureControl(this));
        addState(architectureControl);

        addState(new CompleteStepIndicator_(this, "TEST", 1 ));

        addSubEngine(new DropRobot_(this, architectureControl));
        addSubEngine(new PostDropUTurn_(this, architectureControl));
        addSubEngine(new DriveToDetect_(this, architectureControl));
        addSubEngine(new MineralDetect_(this, architectureControl));
        addSubEngine(new MineralKick_(this, architectureControl));
        addSubEngine(new TeamMarkerDrive_(this, architectureControl));
        addSubEngine(new TeamMarkerPlace_(this, architectureControl));
        addSubEngine(new DriveToPark_fromTMP_(this, architectureControl));
        addSubEngine(new DriveToPark_fromMK_(this, architectureControl));
    }
}
