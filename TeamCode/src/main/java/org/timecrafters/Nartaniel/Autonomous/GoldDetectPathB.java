package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathB extends SubEngine {
public LazerScanv2_1 goldDetect;
//public PinksDrive ScanDrive;
public Engine engine;

    public GoldDetectPathB(Engine engine, LazerScanv2_1 goldDetect, Drive scanDrive) {
        this.goldDetect = goldDetect;
        this.engine = engine;
//        this.ScanDrive = scanDrive;

    }

    @Override
    public void setProcesses() {

    }

    @Override
    public void evaluate() {
       if (goldDetect.isGold && goldDetect.ScanNumber == 1) {
           setRunable(true);
//           ScanDrive.stop();
       }
    }
}
