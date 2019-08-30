package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "Testing Ground")
public class TestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new ConfigTestState(this));
    }
}
