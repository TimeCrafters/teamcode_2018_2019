package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LazerArmSetPos extends State {
    private Servo LazerArmServo;
    private double Position;
    private int SleepTime;

    public LazerArmSetPos(Engine engine, double position, int sleepTime) {
        this.engine = engine;
        this.Position = position;
        this.SleepTime = sleepTime;
    }

    public void init() {
        LazerArmServo = engine.hardwareMap.servo.get("lazerArmServo");

    }

    @Override
    public void exec() throws InterruptedException {
        LazerArmServo.setPosition(Position);
        sleep(SleepTime);
        setFinished(true);
    }
}
