package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathD extends SubEngine {
public LazerScan goldDetect;
public Engine engine;

    public GoldDetectPathD(Engine engine, LazerScan goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(engine, false, 0));
        //addState(new Drive(engine, -0.65, 50, 4));
    }


    //if something goes wrong with the scan and we end up with multiple or no gold particles deteted, we'll run path D and skip
    @Override
    public void evaluate() {
        int ThuthsNumber = 0;
        if (goldDetect.ParticleAisGold) {ThuthsNumber++;}
        if (goldDetect.ParticleBisGold) {ThuthsNumber++;}
        if (goldDetect.ParticleCisGold) {ThuthsNumber++;}

       if (ThuthsNumber == 0 || ThuthsNumber > 1) {setRunable(true);}
    }
}
