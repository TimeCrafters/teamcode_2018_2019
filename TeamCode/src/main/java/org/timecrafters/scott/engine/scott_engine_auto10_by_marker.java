package org.timecrafters.scott.engine;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;
import org.timecrafters.scott.state.scott_state_auto10_drop;

@Autonomous(name="scott_10_by_marker")
public class scott_engine_auto10_by_marker extends Engine {
    /**********************************************************************************************
     * CodeName: scott_engine_auto10_by_marker
     * Use:
     *  1. drop robot
     *  2. turn robot towards minerals
     *  3. align using Vuforia
     *  4. detect the gold mineral
     *  5. drive to team marker drop
     *  6. drop team marker
     *  7. drive to crater
     * History:
     *  11/13/18 DSB - original
     **********************************************************************************************/

    /* define variables */
    private scott_state_hardware_config cHardwareConfig;

    @Override
    public void setProcesses(){
        cHardwareConfig = new scott_state_hardware_config(this);
        addState(cHardwareConfig);
    }
}
