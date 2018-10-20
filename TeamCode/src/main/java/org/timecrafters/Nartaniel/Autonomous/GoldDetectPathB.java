package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathB extends SubEngine {
public LazerScan goldDetect;
public Engine engine;

    public GoldDetectPathB(Engine engine, LazerScan goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new Drive(engine, -0.7, 15, 4));
    }

    @Override
    public void evaluate() {
       if (goldDetect.ParticleBisGold && !goldDetect.ParticleAisGold && !goldDetect.ParticleCisGold) {setRunable(true);}
    }
}
