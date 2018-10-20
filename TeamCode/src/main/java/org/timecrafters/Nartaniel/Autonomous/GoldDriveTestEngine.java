package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

@Autonomous (name = "Gold Drive test")

public class GoldDriveTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(this, true, 500));
        LazerArmCalibrate Calibration = (new LazerArmCalibrate(this, 250) );
        addState(Calibration);
        addState(new Drive(this, 0.5, 50, 4));
        GoldDetectExpLazers goldDetect = (new GoldDetectExpLazers(this, Calibration, 57.5, 27.5));
        addThreadedState(goldDetect);
        addState(new LazerArmToggle(this, false, 0));

        addSubEngine(new GoldDetectPathA(this, goldDetect));
        addSubEngine(new GoldDetectPathB(this, goldDetect));
        addSubEngine(new GoldDetectPathC(this, goldDetect));
        addSubEngine(new GoldDetectPathD(this, goldDetect));

    }
}
