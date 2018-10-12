package org.Nartaniel;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.engine.SubEngine;

public class GoldDetectPathA extends SubEngine {
public GoldDetectExpLazers goldDetect;
public Engine engine;

    public GoldDetectPathA(Engine engine, GoldDetectExpLazers goldDetect) {
        this.goldDetect = goldDetect;
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        addState(new TelemetryState(engine,"Gold"));
    }

    @Override
    public void evaluate() {
       if (goldDetect.isGold) {setRunable(true);}
    }
}
