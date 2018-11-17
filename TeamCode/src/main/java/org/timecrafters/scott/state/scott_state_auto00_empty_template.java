package org.timecrafters.scott.state;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;

public class scott_state_auto00_empty_template extends State {
    public scott_state_auto00_empty_template(Engine engine, scott_state_hardware_config cHardwareConfig){
        this.engine = engine;
        this.cHardwareConfig = cHardwareConfig;
    }
    /**********************************************************************************************
     * CodeName:
     * Inputs:
     * Outputs:
     * Use:
     * History:
     *  11/13/18 DSB - original
     **********************************************************************************************/

    /* variables definitions */
    private scott_state_hardware_config cHardwareConfig;

    @Override
    public void exec(){

    }
}
