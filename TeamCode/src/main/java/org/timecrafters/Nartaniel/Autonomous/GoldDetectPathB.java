package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathB extends SubEngine {
public LazerScanv3 goldDetect;
//public Drive ScanDrive;
public Engine engine;

    public GoldDetectPathB(Engine engine, LazerScanv3 goldDetect, Drive scanDrive) {
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
