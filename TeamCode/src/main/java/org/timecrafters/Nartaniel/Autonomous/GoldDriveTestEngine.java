package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@Autonomous (name = "Gold Drive test")

public class GoldDriveTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new LazerArmToggle(this, true, 500));
        addState(new Drive(this, 0.5, 50, 4));
        addState(new LazerArmToggle(this, false, 0));

    }
}
