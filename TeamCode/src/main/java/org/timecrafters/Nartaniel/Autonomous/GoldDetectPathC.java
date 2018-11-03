package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathC extends SubEngine {
    public LazerScanv3 goldDetect;
//    public Drive ScanDrive;
    public Engine engine;

    public GoldDetectPathC(Engine engine, LazerScanv3 goldDetect, Drive scanDrive) {
        this.goldDetect = goldDetect;
        this.engine = engine;
//        this.ScanDrive = scanDrive;

    }

    @Override
    public void setProcesses() {

        //addState(new Drive(engine, -0.7, 27, 4));
    }

    @Override
    public void evaluate() {
       if (goldDetect.isGold && goldDetect.ScanNumber == 2) {
           setRunable(true);
//           ScanDrive.stop();
       }
    }
}
