package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;

@Autonomous (name = "Msc Test")
public class MscTestEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(this, true, 1000));
        LazerArmCalibrate Calibration = (new LazerArmCalibrate(this, 250) );
        addState(Calibration);
        //addState(new LazerScanv2(this, Calibration, , 40, 20, 25000));
    }
}
