package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LazerArmToggle extends State {
    private Servo LazerArmServo;
    private boolean GoIn;

    public LazerArmToggle(Engine engine, boolean goIn) {
        this.engine = engine;
        this.GoIn = goIn;
    }

    public void init() {
        LazerArmServo = engine.hardwareMap.servo.get("lazerArmServo");

    }

    @Override
    public void exec() throws InterruptedException {
        if (GoIn) {
            LazerArmServo.setPosition(0);
        } else {
            LazerArmServo.setPosition(0.5);

        }
        sleep(100);
        setFinished(true);
    }
}
