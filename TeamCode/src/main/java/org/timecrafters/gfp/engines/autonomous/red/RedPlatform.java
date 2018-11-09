package org.timecrafters.gfp.engines.autonomous.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.state.cam.ReadCam;

/**
 * Created by t420 on 11/2/2017.
 */


@Autonomous(name = "Red Front")
@Disabled
public class RedPlatform extends Engine {

    public void setProcesses(){

            ReadCam readCam = new ReadCam(this);


    }

}
