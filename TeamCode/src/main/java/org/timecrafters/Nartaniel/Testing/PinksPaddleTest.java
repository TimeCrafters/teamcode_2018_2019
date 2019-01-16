package org.timecrafters.Nartaniel.Testing;

import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksPaddle;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class PinksPaddleTest extends State {
    private PinksHardwareConfig PinksHardwareConfig;
    private PinksPaddle Paddle;

    public PinksPaddleTest(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        PinksHardwareConfig = new PinksHardwareConfig(this.engine);
        Paddle = new PinksPaddle(PinksHardwareConfig, 1.0, 0.0, 0.4);
    }

    @Override
    public void exec() {
        engine.telemetry.addData("Servo CurrentPos", Paddle.getCurrentPos());
        engine.telemetry.addData("Servo Controler", Paddle.PaddleServo.getController());
        engine.telemetry.addData("Servo Number", Paddle.PaddleServo.getPortNumber());

        Paddle.PaddleServo.setPower(engine.gamepad1.right_stick_y);
        engine.telemetry.update();

    }
}
