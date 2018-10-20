package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathC extends SubEngine {
public GoldDetectExpLazers goldDetect;
public Engine engine;

    public GoldDetectPathC(Engine engine, GoldDetectExpLazers goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new TelemetryState(engine,"PathB"));
    }

    @Override
    public void evaluate() {
       if (goldDetect.ParticleCisGold && !goldDetect.ParticleBisGold && !goldDetect.ParticleAisGold) {setRunable(true);}
    }
}
