package org.timecrafters.PINKS_2018.States;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step06DropRobot extends State {
    private boolean Complete = false;
    public Step05ArchitectureControl Control;
    private Servo servoRight;
    private Servo servoLeft;



    public Step06DropRobot(Engine engine, Step05ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        servoLeft = Control.PinksHardwareConfig.pDropLeft;
        servoRight = Control.PinksHardwareConfig.pDropRight;
    }

    @Override
    public void exec() {
        if (Control.RunDropRobot) {

            servoLeft.setPosition(.1);
            servoRight.setPosition(1);
            sleep(1500);
            servoLeft.setPosition(1);
            servoRight.setPosition(0);
            Complete = true;

            if (Complete) {
                engine.telemetry.addLine("Completed Step06DropRobot");
                engine.telemetry.update();
                sleep(1000);
                Complete = false;
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

}
