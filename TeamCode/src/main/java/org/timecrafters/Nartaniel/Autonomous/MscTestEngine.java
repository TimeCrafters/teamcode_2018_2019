package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;

@Autonomous (name = "Msc Test")
public class MscTestEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new LazerArmSetPos(this, 0, 2000));
        addState(new LazerArmSetPos(this, 1.5, 2000));
        addState(new LazerArmSetPos(this, 0.5, 2000));
        addState(new LazerArmSetPos(this, 1.0, 2000));

    }
}
