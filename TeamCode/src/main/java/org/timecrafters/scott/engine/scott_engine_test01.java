package org.timecrafters.scott.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;
import org.timecrafters.scott.state.scott_state_laser_detect;

@TeleOp(name="scott_test_1")
public class scott_engine_test01 extends Engine {
    private scott_state_hardware_config cHardwareConfig;
    @Override
    public void setProcesses(){
        cHardwareConfig = new scott_state_hardware_config(this);
        addState(cHardwareConfig);
        addState(new scott_state_laser_detect(this, cHardwareConfig));
    }

}
