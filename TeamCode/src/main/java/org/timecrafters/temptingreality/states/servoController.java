package org.timecrafters.temptingreality.states;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 10/23/2018.
 */

public class servoController extends State {
    private Servo servoRight;
    private Servo servoLeft;
    boolean servoRun = true;
    private Engine engine;
    private  ServoController sServoController;
    public servoController(Engine engine)
    {
        this.engine = engine;
        this.servoLeft = engine.hardwareMap.servo.get("servoLeft");
        this.servoRight = engine.hardwareMap.servo.get("servoRight");
    }

    @Override
    public void init() {
        servoLeft.setPosition(1);
        servoRight.setPosition(0);
        sServoController = servoLeft.getController();//engine.hardwareMap.servoController.get("servo controller");
    }

    @Override
    public void exec() throws InterruptedException {
        //if (servoRun == false){
//            sServoController.pwmDisable();
       // }
        if (engine.gamepad1.a){
            servoLeft.setPosition(.1);
            servoRight.setPosition(1);
            sleep(100);
            servoRun = false;
        }
    }
}
