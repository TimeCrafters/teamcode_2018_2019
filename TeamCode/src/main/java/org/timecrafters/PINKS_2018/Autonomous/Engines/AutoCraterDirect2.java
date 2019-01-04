package org.timecrafters.PINKS_2018.Autonomous.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step06DropRobot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step08PointTowardGold;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step12CTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step13CDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step14CDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step14DPointToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step15DDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.CMineralPathRight;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathCenter;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathLeft;
import org.timecrafters.PINKS_2018.Autonomous.SubEngines.DMineralPathRight;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Autonomous: Depot")
public class AutoCraterDirect2 extends Engine {

  @Override
  public void setProcesses() {

    addState(new Step06DropRobot(this));

    Step07MineralPosId MPosId = (new Step07MineralPosId(this));
    addState(MPosId);

    addState(new Step08PointTowardGold(this, MPosId));

    addSubEngine(new CMineralPathCenter(this, MPosId));
    addSubEngine(new CMineralPathLeft(this, MPosId));
    addSubEngine(new CMineralPathRight(this, MPosId));

    addState(new Step12CTurnToDepot(this));
    addState(new Step13CDriveToDepot(this));
    addState(new StepPlaceMarker(this));
    addState(new Step14CDriveToCrater(this));


  }

}
