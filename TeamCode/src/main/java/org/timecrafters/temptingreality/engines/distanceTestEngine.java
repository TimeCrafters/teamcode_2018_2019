package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.revDistancesensorTest;

/**
 * Created by t420-1 on 10/2/2018.
 */
@TeleOp(name = "rev roving robot", group = "Testing")
public class distanceTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new revDistancesensorTest(this));
    }
}
