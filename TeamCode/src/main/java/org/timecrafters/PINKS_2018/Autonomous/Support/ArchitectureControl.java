package org.timecrafters.PINKS_2018.Autonomous.Support;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ArchitectureControl extends State {

    public PinksHardwareConfig PinksHardwareConfig;

    public StateConfiguration AppReader;
    public boolean RunDropRobot;
    public boolean RunPostDropUTurn;
    public boolean RunDriveToDetect;
    public boolean RunMineralDetect;
    public boolean RunMineralKick;
    public boolean RunTeamMarkerDrive;
    public boolean RunTeamMarkerPlace;
    public boolean RunDriveToPark_fromTMP;
    public boolean RunPark;

    public ArchitectureControl(Engine engine) {
        this.engine = engine;
        this.AppReader = new StateConfiguration();
        this.PinksHardwareConfig = new PinksHardwareConfig(engine);
    }

    @Override
    public void exec() {

        RunDropRobot = AppReader.allow("RunDropRobot");
        RunPostDropUTurn = AppReader.allow("RunPostDropUTurn");
        RunDriveToDetect = AppReader.allow("RunDriveToDetect");
        RunMineralDetect = AppReader.allow("RunMineralDetect");
        RunMineralKick = AppReader.allow("RunMineralKick");
        RunTeamMarkerDrive = AppReader.allow("RunTeamMarkerDrive");
        RunTeamMarkerPlace = AppReader.allow("RunTeamMarkerPlace");
        RunDriveToPark_fromTMP = AppReader.allow("RunDriveToPark_fromTMP");
        RunPark = AppReader.allow("RunPark");
        setFinished(true);

    }

}
