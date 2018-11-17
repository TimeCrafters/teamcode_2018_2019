package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.revRovingRobotGame1;
import org.timecrafters.temptingreality.states.revRovingRobotGame2;

/**
 * Created by t420-1 on 10/2/2018.
 */
@TeleOp(name = "rev roving robot", group = "Testing")
public class revRovingRobotEngine extends Engine {
    @Override
    public void setProcesses() {
        addThreadedState(new revRovingRobotGame1(this));
        addThreadedState(new revRovingRobotGame2(this));
    }

}
