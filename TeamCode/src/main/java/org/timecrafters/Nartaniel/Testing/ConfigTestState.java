package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;



import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class ConfigTestState extends State {

    private DcMotor Motor;
    private com.qualcomm.robotcore.hardware.ColorSensor ColorSensor;
    private Servo Servo;

    public ConfigTestState(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        Motor = engine.hardwareMap.dcMotor.get("Motor");
        ColorSensor = engine.hardwareMap.colorSensor.get("ColorSensor");
        Servo = engine.hardwareMap.servo.get("Servo");
    }

    @Override
    public void exec() throws InterruptedException {

        Motor.setPower(0.5);
        engine.telemetry.addData("Sensor: ", ColorSensor.green());
        Servo.setPosition(1.0);

    }
}
