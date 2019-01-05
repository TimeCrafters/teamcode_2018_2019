package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.engine.Engine;
@Disabled
@Autonomous (name = "Gold PinksDrive test")

public class GoldDriveTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(this, true, 1000));
        LazerArmCalibrate Calibration = (new LazerArmCalibrate(this, 250) );
        addState(Calibration);
        Drive ScanDrive = (new Drive(this, 0.15, 50, 4));
        addState(ScanDrive);
        LazerScanv2_1 goldDetect = (new LazerScanv2_1(this, Calibration, ScanDrive,40, 20,10000));
        addThreadedState(goldDetect);


        addSubEngine(new GoldDetectPathA(this, goldDetect, ScanDrive));
        addSubEngine(new GoldDetectPathB(this, goldDetect, ScanDrive));
        addSubEngine(new GoldDetectPathC(this, goldDetect, ScanDrive));


    }
}
