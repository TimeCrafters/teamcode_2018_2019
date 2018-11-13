package org.timecrafters.scott.state;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;

public class scott_state_auto10_drop extends State {
    public scott_state_auto10_drop(Engine engine, scott_state_hardware_config cHardwareConfig){
        this.engine = engine;
        this.cHardwareConfig = cHardwareConfig;
    }
    /**********************************************************************************************
     * CodeName: scott_state_auto10_drop
     * Inputs: engine, hardware pointers
     * Outputs: none
     * Use: drops the robot off of the lander
     * History:
     *  11/13/18 DSB - original
    **********************************************************************************************/

    /* variables definitions */
    private scott_state_hardware_config cHardwareConfig;

    @Override
    public void exec(){

    }

}
