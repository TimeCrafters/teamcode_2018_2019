package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathA extends SubEngine {
public LazerScan goldDetect;
public Engine engine;

    public GoldDetectPathA(Engine engine, LazerScan goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new Drive(engine, -0.7, 27, 4));
    }

    @Override
    public void evaluate() {
       if (goldDetect.ParticleAisGold && !goldDetect.ParticleBisGold && !goldDetect.ParticleBisGold) {setRunable(true);}
    }
}
