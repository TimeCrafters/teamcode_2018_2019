package org.timecrafters.Nartaniel.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class LazerScanv3 extends State {
    private long CurrentTime;
    private boolean ServoDirection;
    private boolean FirstRun = true;
    private double ArmWaveAmount;
    private long ArmWavePeriod;
    private long StartTime;
    private long TimeOutTime;
    private ArrayList <DistanceSensor> DistanceSensors;
    private LazerArmCalibrate Calibration;
    private Servo LazerArmServo;
    private Drive Drive;
    public double BuildHeightMM;
    private int NumberOfDati;
    private int DatiPerSec;
    private long PreviousTriggerTime;
    private double GoldDetectThreshold;
    private double ObjectDetectThreshhold;
    private double ScanEndVal;
    public int ScanNumber;
    public boolean isGold;
    private boolean ScanCommence;
    private ArrayList<Double> HightValues;
    private int DatiRequirement;
    private double ServoCurrentPos;


    public LazerScanv3(Engine engine, LazerArmCalibrate calibration, Drive drive, double goldDetectThreshold, double objectDetectThreshhold, double armWaveAmount, long armWavePeriod, int datiRequirement) {
        this.engine = engine;
        this.Calibration = calibration;
        this.GoldDetectThreshold = goldDetectThreshold;
        this.ObjectDetectThreshhold = objectDetectThreshhold;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
        this.Drive = drive;
        this.ArmWaveAmount = armWaveAmount;
        this.ArmWavePeriod = armWavePeriod;
        this.DatiRequirement = datiRequirement;


    }

    public void init() {
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance0"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance1"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance2"));
        DistanceSensors.add(engine.hardwareMap.get(DistanceSensor.class,"distance3"));
        LazerArmServo = engine.hardwareMap.servo.get("lazerArmServo");
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

        CurrentTime = System.currentTimeMillis();

        //First Run:
        if (FirstRun) {
            BuildHeightMM = Calibration.BuildHeightMM;
            StartTime = CurrentTime;
            PreviousTriggerTime = CurrentTime;
            FirstRun = false;
        }

        HightValues.set(0, hightsFromGround(DistanceSensors.get(0)));
        HightValues.set(1, hightsFromGround(DistanceSensors.get(1)));
        HightValues.set(2, hightsFromGround(DistanceSensors.get(2)));
        HightValues.set(3, hightsFromGround(DistanceSensors.get(3)));


        if (!ScanCommence) {
            //Checks the 4 hight values to see if an object is present and act accordingly
            for (int index = 0; index < (HightValues.size() - 2); index++) {
                if (HightValues.get(index) >= ObjectDetectThreshhold) {
                    ScanCommence = true;
                    NumberOfDati = 0;
                    Drive.pause(true);
                    ServoDirection = true;
                    PreviousTriggerTime = CurrentTime;
                }
            }
        }



        //Scan Start if object detected
//        if (!ScanCommence && HightValues.get(0) >= ObjectDetectThreshhold || HightValues.get(1) >= ObjectDetectThreshhold || HightValues.get(2) >= ObjectDetectThreshhold || HightValues.get(3) >= ObjectDetectThreshhold) {
//            ScanCommence = true;
//            NumberOfDati = 0;
//            PinksDrive.pause(true);
//            ServoDirection = true;
//            PreviousTriggerTime = CurrentTime;
//        }

        
        if (ScanCommence) {
            //Store Highest value while Scaning
            for (int index = 0; index < (HightValues.size() - 2); index++) {
                if (HightValues.get(index) > HightValues.get(HightValues.size() - 1)) {
                    HightValues.set(HightValues.size() - 1, HightValues.get(index));
                }

            }
        }
            
            //Waves Scan arm every second for more unique values

            if (ScanCommence && CurrentTime - PreviousTriggerTime >= ArmWavePeriod) {

                ServoCurrentPos = LazerArmServo.getPosition();

               if (ServoDirection && ServoCurrentPos <= 0.6 + ArmWaveAmount) {
                   LazerArmServo.setPosition(ServoCurrentPos + 0.01);
               } else if (!ServoDirection && ServoCurrentPos >= 0.6 - ArmWaveAmount) {
                   LazerArmServo.setPosition(ServoCurrentPos - 0.01);
               } else if (ServoDirection && ServoCurrentPos >= 0.6 + ArmWaveAmount) {
                   ServoDirection = false;
               } else if (!ServoDirection && ServoCurrentPos <= 0.6 - ArmWaveAmount) {
                   ServoDirection = true;
               }

                PreviousTriggerTime = CurrentTime;
            }

            if (ScanCommence) {
                //Counts the number of unique data values detected in scan.
                for (DistanceSensor sensor : DistanceSensors) {
                    for (double PrevDataValue : HightValues) {
                        if (hightsFromGround(sensor) != PrevDataValue) {
                            NumberOfDati++;
                        }
                    }
                }
            }


        //When Enough Dati are gathered, determine if gold and act accordingly.
        if (ScanCommence &&  NumberOfDati >= DatiRequirement) {
            Log.i("Y", "reset "+NumberOfDati);
            ScanCommence = false;
            if (HightValues.get(HightValues.size() - 1) <= GoldDetectThreshold) {
                isGold = true;
                Drive.stop();
                setFinished(true);
            } else {
                ScanNumber++;
                resetHights();
                Drive.pause(false);
            }
        }

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

        engine.telemetry.addData("Arm Position", LazerArmServo.getPosition());

        for (double sensor: HightValues) {
            engine.telemetry.addData("Hight Value", sensor);
        }
        engine.telemetry.addData("Current Time", CurrentTime);
        engine.telemetry.addData("Previous Trigger Time", PreviousTriggerTime);
        engine.telemetry.addData("Max Hight", HightValues.get(HightValues.size() - 1));
        engine.telemetry.addData("Dati in one Scan", NumberOfDati);
        if (isGold) {engine.telemetry.addLine("IS GOLD!!!");}
        if (ScanCommence) {engine.telemetry.addLine("Object Detected");}
        if (!ScanCommence) {engine.telemetry.addLine("Not Detecting Object");}

    }

}
