package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;

@Autonomous (name = "Gold Drive test")

public class GoldDriveTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(this, true, 1000));
        LazerArmCalibrate Calibration = (new LazerArmCalibrate(this, 250) );
        addState(Calibration);
        //addState(new Drive(this, 0.25, 50, 4));
        LazerScan goldDetect = (new LazerScan(this, Calibration, 57.5, 15));
        //addThreadedState(goldDetect);
        addState(goldDetect);

        addSubEngine(new GoldDetectPathA(this, goldDetect));
        addSubEngine(new GoldDetectPathB(this, goldDetect));
        addSubEngine(new GoldDetectPathC(this, goldDetect));
        addSubEngine(new GoldDetectPathD(this, goldDetect));

    }
}
