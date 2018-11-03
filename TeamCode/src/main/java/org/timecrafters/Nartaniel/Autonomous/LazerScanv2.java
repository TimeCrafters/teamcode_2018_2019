package org.timecrafters.Nartaniel.Autonomous;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class LazerScanv2 extends State {
    private boolean FirstRun = true;
    private long StartTime;
    private long TimeOutTime;
    private ArrayList <DistanceSensor> DistanceSensors;
    private LazerArmCalibrate Calibration;
    private Drive Drive;
    public double BuildHeightMM;
    private int NumberOfDati;
    private int DatiPerSec;
    private long LastSecond;
    private double GoldDetectThreshold;
    private double ObjectDetectThreshhold;
    private double ScanEndVal;
    public int ScanNumber;
    public boolean isGold;
    private boolean ScanCommence;
    private ArrayList<Double> HightValues;


    public LazerScanv2(Engine engine, LazerArmCalibrate calibration,Drive drive, double goldDetectThreshold, double objectDetectThreshhold, long timeOutTime) {
        this.engine = engine;
        this.TimeOutTime = timeOutTime;
        this.Calibration = calibration;
        this.GoldDetectThreshold = goldDetectThreshold;
        this.ObjectDetectThreshhold = objectDetectThreshhold;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
        this.Drive = drive;
    }

    public void init() {
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance0"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance1"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance2"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance3"));
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);
        resetHights();

        ScanEndVal = ObjectDetectThreshhold - 7.5;
        isGold = false;

    }

    @Override
    public void exec() throws InterruptedException {

        //First Run:
        if (FirstRun) {
            BuildHeightMM = Calibration.BuildHeightMM;
            StartTime = System.currentTimeMillis();
            LastSecond = System.currentTimeMillis();
            FirstRun = false;
        }

        /*Number of Dati detection


        if (LastSecond + 1000 >= System.currentTimeMillis()) {
            LastSecond = System.currentTimeMillis();
            DatiPerSec = NumberOfDati;
            NumberOfDati = 0;
        }
*/
        HightValues.set(0, hightsFromGround(DistanceSensors.get(0)));
        HightValues.set(1, hightsFromGround(DistanceSensors.get(1)));
        HightValues.set(2, hightsFromGround(DistanceSensors.get(2)));
        HightValues.set(3, hightsFromGround(DistanceSensors.get(3)));

        //Scan Start if object detected
        if (!ScanCommence && HightValues.get(0) >= ObjectDetectThreshhold || HightValues.get(1) >= ObjectDetectThreshhold || HightValues.get(2) >= ObjectDetectThreshhold || HightValues.get(3) >= ObjectDetectThreshhold) {
            ScanCommence = true;
            NumberOfDati = 0;
        }

        //Store Highest value while Scaning
        if (ScanCommence) {
            for (int value = 0; value < (HightValues.size() - 2); value++) {
                if (HightValues.get(value) > HightValues.get(HightValues.size()-1)) {
                    HightValues.set(HightValues.size() - 1, HightValues.get(value));
                }
            }

            for (DistanceSensor sensor : DistanceSensors) {
                for (double HightValue : HightValues) {
                    if  (hightsFromGround(sensor) != HightValue) {
                        NumberOfDati++;
                    }
                }
            }
        }

        //When Object no longer being detected, finish scan and determine if gold
        if (ScanCommence &&  HightValues.get(0) < ScanEndVal && HightValues.get(1) < ScanEndVal && HightValues.get(2) < ScanEndVal && HightValues.get(3) < ScanEndVal ) {
            ScanCommence = false;
            if (HightValues.get(HightValues.size() - 1) <= GoldDetectThreshold) {
                isGold = true;
                Drive.stop();
                setFinished(true);
            } else {
                ScanNumber++;
                resetHights();
            }
        }

        //Time Out
        /*
        if (System.currentTimeMillis() > StartTime + TimeOutTime) {
            setFinished(true);
        }
*/

    }

    public double hightsFromGround(DistanceSensor sensor) {
       return BuildHeightMM - sensor.getDistance(DistanceUnit.MM);
    }

    public void resetHights() {
        HightValues.set(0, 0.0);
        HightValues.set(1, 0.0);
        HightValues.set(2, 0.0);
        HightValues.set(3, 0.0);
        HightValues.set(4, 0.0);
    }

    public void telemetry() {

        engine.telemetry.addData("Build Hight", BuildHeightMM);

        engine.telemetry.addData("Scan #", ScanNumber);

        engine.telemetry.addData("Max Hight", HightValues.get(HightValues.size() - 1));
        //engine.telemetry.addData("Dati Per Second", DatiPerSec);
        engine.telemetry.addData("Dati in one Scan", NumberOfDati);
        if (isGold) {engine.telemetry.addLine("IS GOLD!!!");}
        if (ScanCommence) {engine.telemetry.addLine("Object Detected");}
        if (!ScanCommence) {engine.telemetry.addLine("Not Detecting Object");}

    }

}
