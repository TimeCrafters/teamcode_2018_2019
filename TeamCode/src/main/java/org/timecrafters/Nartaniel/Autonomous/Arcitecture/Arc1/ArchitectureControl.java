package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ArchitectureControl extends State {

    public boolean RunDropRobot = true;
    public boolean RunPostDropUTurn = true;
    public boolean RunDriveToDetect = true;
    public boolean RunMineralDetect = true;
    public boolean RunMineralKick = true;
    public boolean RunTeamMarkerDrive = true;
    public boolean RunTeamMarkerPlace = true;
    public boolean RunDriveToPark_fromTMP = true;
    public boolean RunDriveToPark_fromMK = true;

    public ArchitectureControl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {


    }
}
