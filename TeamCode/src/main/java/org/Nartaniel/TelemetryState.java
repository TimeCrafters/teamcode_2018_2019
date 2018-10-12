package org.Nartaniel;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TelemetryState extends State {

    private String Text;

    public TelemetryState(Engine engine, String text) {
        this.engine = engine;
        this.Text = text;
    }

    @Override
    public void exec() {
        engine.telemetry.addLine(Text);
    }
}
