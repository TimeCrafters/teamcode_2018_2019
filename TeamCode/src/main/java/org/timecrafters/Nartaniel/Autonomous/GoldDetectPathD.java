package org.timecrafters.Nartaniel.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathD extends SubEngine {
public GoldDetectExpLazers goldDetect;
public Engine engine;

    public GoldDetectPathD(Engine engine, GoldDetectExpLazers goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new TelemetryState(engine,"PathB"));
    }

    @Override
    public void evaluate() {
        int ThuthsNumber = 0;
        if (goldDetect.ParticleAisGold) {ThuthsNumber++;}
        if (goldDetect.ParticleBisGold) {ThuthsNumber++;}
        if (goldDetect.ParticleCisGold) {ThuthsNumber++;}

       if (ThuthsNumber == 0 || ThuthsNumber > 1) {setRunable(true);}
    }
}
