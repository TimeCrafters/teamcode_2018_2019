package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ArchitectureControl extends State {
    private StateConfiguration AppReader;
    public boolean RunDropRobot = false;
    public boolean RunPostDropUTurn = false;
    public boolean RunDriveToDetect = false;
    public boolean RunMineralDetect = false;
    public boolean RunMineralKick = false;
    public boolean RunTeamMarkerDrive = false;
    public boolean RunTeamMarkerPlace = false;
    public boolean RunDriveToPark_fromTMP = false;
    public boolean RunDriveToPark_fromMK = false;

    public ArchitectureControl(Engine engine) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
    }

    public void init() {
        RunDropRobot = AppReader.allow("RunDropRobot");
        RunPostDropUTurn = AppReader.allow("RunPostDropUTurn");
        RunDriveToDetect = AppReader.allow("RunDriveToDetect");
        RunMineralDetect = AppReader.allow("RunMineralDetect");
        RunMineralKick = AppReader.allow("RunMineralKick");
        RunTeamMarkerDrive = AppReader.allow("RunTeamMarkerDrive");
        RunTeamMarkerPlace = AppReader.allow("RunTeamMarkerPlace");
        RunDriveToPark_fromTMP = AppReader.allow("RunDriveToPark_fromTMP");
        RunDriveToPark_fromMK = AppReader.allow("RunDriveToPark_fromMK");
    }

    @Override
    public void exec() throws InterruptedException {


    }

}
