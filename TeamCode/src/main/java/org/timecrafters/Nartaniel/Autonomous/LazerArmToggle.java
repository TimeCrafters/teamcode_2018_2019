package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LazerArmToggle extends State {
    private Servo LazerArmServo;
    private boolean GoOut;
    private int SleepTime;

    public LazerArmToggle(Engine engine, boolean goOut, int sleepTime) {
        this.engine = engine;
        this.GoOut = goOut;
        this.SleepTime = sleepTime;
    }

    public void init() {
        LazerArmServo = engine.hardwareMap.servo.get("lazerArmServo");

    }

    @Override
    public void exec() throws InterruptedException {
        if (GoOut) {
            LazerArmServo.setPosition(0.7);
        } else {
            LazerArmServo.setPosition(0);

        }
        sleep(SleepTime);
        setFinished(true);
    }
}
