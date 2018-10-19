package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.timecrafters.engine.State;

import java.util.ArrayList;

public class LazerArmCalibrate extends State {
    private ArrayList<DistanceSensor> distanceSensors;
    public double CalibrateBuildHeight;
    private ArrayList<Double> Heights;
    private double CurrentSum;
    private long StartTime;
    private long ScanTime;

    public LazerArmCalibrate(ArrayList<DistanceSensor> distanceSensors, long scanTime) {
        this.distanceSensors = distanceSensors;
        this.StartTime = System.currentTimeMillis();
        this.ScanTime = scanTime;
    }

    public void init() {
        distanceSensors.add( engine.hardwareMap.get(DistanceSensor.class, "distance0") );
        distanceSensors.add( engine.hardwareMap.get(DistanceSensor.class, "distance1") );
        distanceSensors.add( engine.hardwareMap.get(DistanceSensor.class, "distance2") );
        distanceSensors.add( engine.hardwareMap.get(DistanceSensor.class, "distance3") );
        distanceSensors.add( engine.hardwareMap.get(DistanceSensor.class, "distance4") );

    }

    @Override
    public void exec() throws InterruptedException {

        if (StartTime + ScanTime > System.currentTimeMillis()) {
            for (int value = 0; value < Heights.size(),)
        }

        for (int value = 0; value < Heights.size(); value++) {
            CurrentSum += Heights.get(value);
        }
        CalibrateBuildHeight = CurrentSum/Heights.size();
    }
}
