package org.timecrafters.cyberarm.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.timecrafters.cyberarm.states.CraneDriverState;

@TeleOp(name = "CraneDriver")
public class CraneDriver extends CyberarmEngineV2 {
    @Override
    public void setup() {
        addState(new CraneDriverState());
    }
}
