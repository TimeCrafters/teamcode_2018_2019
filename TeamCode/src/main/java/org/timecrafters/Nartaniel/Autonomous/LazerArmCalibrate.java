package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class LazerArmCalibrate extends State {
    private DistanceSensor distanceSensor0;
    private DistanceSensor distanceSensor1;
    private DistanceSensor distanceSensor2;
    private DistanceSensor distanceSensor3;
    public double BuildHeightMM;
    private ArrayList<Double> HeightValues;
    private double CurrentSum;
    private long StartTime;
    private long ScanTime;
    private boolean ForcedRun = true;

    public LazerArmCalibrate(Engine engine, long scanTimeMillis) {
        this.engine = engine;
        this.ScanTime = scanTimeMillis;
        this.HeightValues = new ArrayList<>();
    }

    public void init() {
        distanceSensor0 = engine.hardwareMap.get(DistanceSensor.class,"distance0");
        distanceSensor1 = engine.hardwareMap.get(DistanceSensor.class,"distance1");
        distanceSensor2 = engine.hardwareMap.get(DistanceSensor.class,"distance2");
        distanceSensor3 = engine.hardwareMap.get(DistanceSensor.class,"distance3");


    }

    @Override
    public void exec() throws InterruptedException {

        if(ForcedRun) {StartTime = System.currentTimeMillis();
        ForcedRun = false;
        }

        if (System.currentTimeMillis() - StartTime < ScanTime) {
                HeightValues.add(distanceSensor0.getDistance(DistanceUnit.MM));
                HeightValues.add(distanceSensor1.getDistance(DistanceUnit.MM));
                HeightValues.add(distanceSensor2.getDistance(DistanceUnit.MM));
                HeightValues.add(distanceSensor3.getDistance(DistanceUnit.MM));
        } else {

            CurrentSum = 0;
            for (int value = 0; value < HeightValues.size(); value++) {
                CurrentSum += HeightValues.get(value);
            }
            BuildHeightMM = CurrentSum / HeightValues.size();
            setFinished(true);
        }


    }

    public void telemetry() {
        engine.telemetry.addData("Build Hight", BuildHeightMM);
        engine.telemetry.addData("Current Sum", CurrentSum);
        engine.telemetry.addData("Array Size", HeightValues.size());

    }
}

