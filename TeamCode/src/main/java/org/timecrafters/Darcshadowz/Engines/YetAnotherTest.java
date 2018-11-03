package org.timecrafters.Darcshadowz.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.Darcshadowz.State.ReadingColor;
import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by Dylan on 1/6/2018.
 */
@Autonomous(name = "Yet Another Test")
@Disabled
public class YetAnotherTest extends Engine{
    public YetAnotherTest(Engine engine) {
    }

    @Override
    public void setProcesses() {
        addState(new ReadingColor(this, "blue"));
    }


}
