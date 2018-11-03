package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class GoldDetectExpColorSensor extends State {

    private ColorSensor colorSensor;

    public GoldDetectExpColorSensor(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        colorSensor = engine.hardwareMap.colorSensor.get("colorSensor");
    }

    @Override
    public void exec() {
        engine.telemetry.addData("Red", colorSensor.red());
        engine.telemetry.addData("Blue", colorSensor.blue());
        engine.telemetry.addData("Green", colorSensor.green());
        engine.telemetry.update();
    }
}
