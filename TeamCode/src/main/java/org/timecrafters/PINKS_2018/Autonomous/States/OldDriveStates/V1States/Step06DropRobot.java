package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**********************************************************************************************
 * Name: DropRobot
 * Inputs: engine, ArchitectureControl
 * Outputs: none
 * Use: Opens drop latch to drop robot
 **********************************************************************************************/

public class Step06DropRobot extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private Servo servoRight;
    private Servo servoLeft;



    public Step06DropRobot(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        servoLeft = Control.PinksHardwareConfig.DropLeft;
        servoRight = Control.PinksHardwareConfig.DropRight;

        servoLeft.setPosition(1);
        servoRight.setPosition(0);
    }

    @Override
    public void exec() {
        if (Control.RunDropRobot) {
            //This is the actual code for the Drop step. It opens the drop latch servos before
            //closing them shortly after the robot lands.
            servoLeft.setPosition(.6);
            servoRight.setPosition(1);
            sleep(500);
            servoLeft.setPosition(1);
            servoRight.setPosition(0);
            Complete = true;

            if (Complete) {
                engine.telemetry.addLine("Completed DropRobot");
                engine.telemetry.update();
                Complete = false;
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

}
