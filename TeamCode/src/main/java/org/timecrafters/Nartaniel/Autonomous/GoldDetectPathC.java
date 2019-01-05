package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathC extends SubEngine {
    public LazerScanv2_1 goldDetect;
//    public PinksDrive ScanDrive;
    public Engine engine;

    public GoldDetectPathC(Engine engine, LazerScanv2_1 goldDetect, Drive scanDrive) {
        this.goldDetect = goldDetect;
        this.engine = engine;
//        this.ScanDrive = scanDrive;

    }

    @Override
    public void setProcesses() {

        //addState(new PinksDrive(engine, -0.7, 27, 4));
    }

    @Override
    public void evaluate() {
       if (goldDetect.isGold && goldDetect.ScanNumber == 2) {
           setRunable(true);
//           ScanDrive.stop();
       }
    }
}
