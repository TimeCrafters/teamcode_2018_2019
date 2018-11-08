package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.servoController;

/**
 * Created by t420-1 on 10/23/2018.
 */
@TeleOp (name = "name", group = "Testing")
public class servoControllerEngine extends Engine {
    @Override
    public void setProcesses() { addState(new servoController(this));
    }
}
