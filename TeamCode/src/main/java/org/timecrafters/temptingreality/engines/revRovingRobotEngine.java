package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.revRovingRobot;

/**
 * Created by t420-1 on 10/2/2018.
 */
@TeleOp(name = "rev roving robot", group = "Testing")
public class revRovingRobotEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new revRovingRobot(this));
    }
}
