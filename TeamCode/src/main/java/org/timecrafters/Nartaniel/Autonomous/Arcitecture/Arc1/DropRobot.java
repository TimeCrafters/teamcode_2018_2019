package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.temptingreality.states.servoController;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DropRobot extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private Servo servoRight;
    private Servo servoLeft;



    public DropRobot(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        servoLeft = engine.hardwareMap.servo.get("servoLeft");
        servoRight = engine.hardwareMap.servo.get("servoRight");
        servoLeft.setPosition(1);
        servoRight.setPosition(0);
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
                engine.telemetry.addLine("Completed DropRobot");
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
