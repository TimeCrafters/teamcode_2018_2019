package org.timecrafters.scott.engine;

import org.timecrafters.engine.Engine;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;
import org.timecrafters.scott.state.scott_state_auto10_drop;

public class scott_engine_auto00_empty_template extends Engine {
    /**********************************************************************************************
     * CodeName:
     * Use:
     * History:
     *  11/13/18 DSB - add updates to top of list
     *  11/12/18 DSB - original
     **********************************************************************************************/

    /* define variables */
    private scott_state_hardware_config cHardwareConfig;

    /* run processes */
    @Override
    public void setProcesses(){
        cHardwareConfig = new scott_state_hardware_config(this);
        addState(cHardwareConfig);
        addState(new scott_state_auto10_drop(this, cHardwareConfig));
    }
}
