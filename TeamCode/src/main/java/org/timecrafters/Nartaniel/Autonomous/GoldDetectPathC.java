package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathC extends SubEngine {
public LazerScan goldDetect;
public Engine engine;

    public GoldDetectPathC(Engine engine, LazerScan goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new Drive(engine, -0.7, 3, 4));
    }

    @Override
    public void evaluate() {
       if (goldDetect.ParticleCisGold && !goldDetect.ParticleBisGold && !goldDetect.ParticleAisGold) {setRunable(true);}
    }
}
