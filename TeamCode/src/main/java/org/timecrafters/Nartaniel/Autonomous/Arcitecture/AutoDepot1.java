package org.timecrafters.Nartaniel.Autonomous.Arcitecture;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

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

    public static class ArchitectureControl extends State {

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
}
