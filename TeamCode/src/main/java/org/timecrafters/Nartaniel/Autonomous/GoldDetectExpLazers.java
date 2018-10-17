package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class GoldDetectExpLazers extends State {
    private DistanceSensor distanceSensor0;
    private DistanceSensor distanceSensor1;
    private DistanceSensor distanceSensor2;
    private DistanceSensor distanceSensor3;
    public double BuildHeightMM;
    private double Hight0;
    private double Hight1;
    private double Hight2;
    private double Hight3;
    private double GoldDetectThreshold;
    private double ObjectDetectThreshhold;
    public boolean isGold;
    public boolean isSilver;
    private boolean ScanCommence;
    private ArrayList<Double> HightValues;
    private double HighestValue;



    public GoldDetectExpLazers(Engine engine, double buildHeightMM, double goldDetectThreshold, double objectDetectThreshhold) {
        this.engine = engine;
        BuildHeightMM = buildHeightMM;
        GoldDetectThreshold = goldDetectThreshold;
        ObjectDetectThreshhold = objectDetectThreshhold;
        HightValues = new ArrayList<>();
    }

    public void  init() {
        distanceSensor0 = engine.hardwareMap.get(DistanceSensor.class,"distance0");
        distanceSensor1 = engine.hardwareMap.get(DistanceSensor.class,"distance1");
        distanceSensor2 = engine.hardwareMap.get(DistanceSensor.class,"distance2");
        distanceSensor3 = engine.hardwareMap.get(DistanceSensor.class,"distance3");

        isGold = false;

    }

    @Override
    public void exec() {
        Hight0 = BuildHeightMM - distanceSensor0.getDistance(DistanceUnit.MM);
        Hight1 = BuildHeightMM - distanceSensor1.getDistance(DistanceUnit.MM);
        Hight2 = BuildHeightMM - distanceSensor2.getDistance(DistanceUnit.MM);
        Hight3 = BuildHeightMM - distanceSensor3.getDistance(DistanceUnit.MM);
        isGold = false;
        isSilver = false;

        if (Hight0 >= ObjectDetectThreshhold || Hight1 >= ObjectDetectThreshhold || Hight2 >= ObjectDetectThreshhold || Hight3 >= ObjectDetectThreshhold) {
            ScanCommence = true;
        }

        if (ScanCommence) {
            HightValues.add(Hight0);
            HightValues.add(Hight1);
            HightValues.add(Hight2);
            HightValues.add(Hight3);
        }

        if (ScanCommence &&  Hight0 < ObjectDetectThreshhold && Hight1 < ObjectDetectThreshhold && Hight2 < ObjectDetectThreshhold && Hight3 < ObjectDetectThreshhold) {
            for (int value = 0; value < HightValues.size(); value++) {
                if (HightValues.get(value) > HighestValue ) {
                    HighestValue = HightValues.get(value);
                }
            }

            if (HighestValue >= GoldDetectThreshold ) {
                isSilver = true;
                isGold = false;
            }
            else {
                isGold = true;
                isSilver = false;
            }
        }



    }

    public void telemetry() {

        engine.telemetry.addData("Distance0", Hight0);
        engine.telemetry.addData("Distance1", Hight1);
        engine.telemetry.addData("Distance2", Hight2);
        engine.telemetry.addData("Distance3", Hight3);
        engine.telemetry.addData("MaxHight", HighestValue);

    }
}
