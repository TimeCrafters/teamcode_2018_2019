package org.timecrafters.scott.state;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.scott.hardwareConfig.scott_state_hardware_config;

import java.util.Date;

public class scott_state_laser_detect extends State {

    public scott_state_laser_detect(Engine engine,scott_state_hardware_config cHardwareConfig){
        this.engine = engine;
        this.cHardwareConfig = cHardwareConfig;
    }
    /* variables definitions */
    public boolean bDistanceCalibrated = false;
    private scott_state_hardware_config cHardwareConfig;
    private double dDistanceTofloor;
    private double dMaxObjectHeight;
    private double dSingleScanMaxObjectHeight;
    private double d0;
    private double d1;
    private double d2;
    private double d3;
    private double dWiggleMin=0.3;
    private double dWiggleMax=0.7;
    private long lWiggleCycleTime = 10000;
    private long lWiggleStartTime;
    private long lCurrentTime;
    private boolean bWiggleDirection=false;
    private double dWigglePosition;
    private double dWiggleStepSize=0.1;

    @Override
    public void init() {

    }

    @Override
    public void exec() {
        if(!bDistanceCalibrated) {
            calibrate_to_floor();
            engine.telemetry.addData("distance to floor", dDistanceTofloor);
            engine.telemetry.update();
            bDistanceCalibrated=true;
            dMaxObjectHeight = 0.0;
            lWiggleStartTime = System.currentTimeMillis();
        }else
        {
            find_mineral_height();
            wiggle_laser_arm();
            engine.telemetry.addData("distance to floor", dDistanceTofloor);
            engine.telemetry.addData("single scan object height", dSingleScanMaxObjectHeight);
            engine.telemetry.addData("object height", dMaxObjectHeight);
            engine.telemetry.addData("wiggle position", dWigglePosition);
            engine.telemetry.update();
        }
    }
    private void calibrate_to_floor(){
        cHardwareConfig.pLaserArmServo.setPosition(0.5);
        sleep(1000);
        d0 = cHardwareConfig.pMineralDetect0.getDistance(DistanceUnit.MM);
        d1 = cHardwareConfig.pMineralDetect1.getDistance(DistanceUnit.MM);
        d2 = cHardwareConfig.pMineralDetect2.getDistance(DistanceUnit.MM);
        d3 = cHardwareConfig.pMineralDetect3.getDistance(DistanceUnit.MM);
        dDistanceTofloor = d0;
        if (d1 > dDistanceTofloor) {
            dDistanceTofloor = d1;
        }
        if (d2 > dDistanceTofloor) {
            dDistanceTofloor = d2;
        }
        if (d3 > dDistanceTofloor) {
            dDistanceTofloor = d3;
        }
    }
    private void find_mineral_height() {
        cHardwareConfig.pLaserArmServo.setPosition(0.5);
        d0 = cHardwareConfig.pMineralDetect0.getDistance(DistanceUnit.MM);
        d1 = cHardwareConfig.pMineralDetect1.getDistance(DistanceUnit.MM);
        d2 = cHardwareConfig.pMineralDetect2.getDistance(DistanceUnit.MM);
        d3 = cHardwareConfig.pMineralDetect3.getDistance(DistanceUnit.MM);
        dSingleScanMaxObjectHeight = dDistanceTofloor - d0;
        if (dDistanceTofloor - d1 > dSingleScanMaxObjectHeight) {
            dSingleScanMaxObjectHeight = dDistanceTofloor - d1;
        }
        if (dDistanceTofloor - d2 > dSingleScanMaxObjectHeight) {
            dSingleScanMaxObjectHeight = dDistanceTofloor - d2;
        }
        if (dDistanceTofloor - d3 > dSingleScanMaxObjectHeight) {
            dSingleScanMaxObjectHeight = dDistanceTofloor - d3;
        }
        if (dSingleScanMaxObjectHeight > dMaxObjectHeight) {
            dMaxObjectHeight = dSingleScanMaxObjectHeight;
        }
    }
    private void wiggle_laser_arm (){
        lCurrentTime = System.currentTimeMillis();
        dWigglePosition = Math.sin(Math.toRadians((double)(360*(lCurrentTime-lWiggleStartTime))/lWiggleCycleTime));
        //cHardwareConfig.pLaserArmServo.setPosition(dWigglePosition);


    }
}
