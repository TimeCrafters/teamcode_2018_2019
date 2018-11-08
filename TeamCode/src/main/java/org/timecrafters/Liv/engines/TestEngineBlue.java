package org.timecrafters.Liv.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.state.drive.DriveStraightBackward;

/**
 * Created by Liv on 11/14/2017.
 */
@Autonomous(name="IDK")
@Disabled
public class TestEngineBlue extends Engine {

    @Override
    public void setProcesses() {
        addState(new DriveStraightBackward(this, 0.2, 100));

    }


}

