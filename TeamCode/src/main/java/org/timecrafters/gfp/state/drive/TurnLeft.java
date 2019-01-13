package org.timecrafters.gfp.state.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;

/**
 * Created by t420 on 11/2/2017.
 */

public class TurnLeft extends Drive {

    public TurnLeft(Engine engine, double power, int distance){
        super(engine);
        this.power = power;
        this.distance = distance;
    }
    public TurnLeft(Engine engine, double power, int distance, boolean haltOnComplete){
        super(engine);
        this.power = power;
        this.distance = distance;
        this.haltOnComplete = haltOnComplete;
    }

    public void init(){
        super.init();
        //Front LeftDrive, Back LeftDrive, Front RightDrive, Back RightDrive
        setMotors(1,1,-1,-1);
        DcMotor[] motors = {dcFrontLeft, dcBackLeft};
        setReadMotors(motors);
    }
}
