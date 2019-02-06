package org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V1States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class Step09MineralDetect extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private long StartTime;
    private long CurrentTime;
    private long CalibrationTime;
    private long PreviousTriggerTime;
    private boolean ServoDirection;
    private boolean FirstRun = true;
    private double ArmWaveAmount = 0.25;
    private long ArmWavePeriod = 50;
    private ArrayList<DistanceSensor> DistanceSensors;

    private Servo LazerArmServo;
    private int NumberOfDati;
    private double GoldDetectThreshold;
    private double ObjectDetectThreshold;
    public int ScanNumber;
    public boolean isGold;
    private boolean ScanCommence;

    private ArrayList<Double> HightValues;
    private ArrayList<Double> Cal0;
    private ArrayList<Double> Cal1;
    private ArrayList<Double> Cal2;
    private ArrayList<Double> Cal3;
    private ArrayList<Double> GroundValues;

    private int DatiRequirement;
    private double ServoCurrentPos;
    private boolean DriveOnly;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private double Power;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceIN;
    private int distanceTicks;
    private double whealCircumference = 4;
    private boolean Calibrating;
    private double LazerMidPosition = 0.7;



    public Step09MineralDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
        this.Cal0 = new ArrayList<>();
        this.Cal1 = new ArrayList<>();
        this.Cal2 = new ArrayList<>();
        this.Cal3 = new ArrayList<>();
        this.GroundValues = new ArrayList<>();
    }

    public void init() {
        DriveOnly = Control.AppReader.get("RunMineralDetect").variable("OnlyDrive");
        distanceIN = Control.AppReader.get("RunMineralDetect").variable("distanceIN");
        CalibrationTime = Control.AppReader.get("RunMineralDetect").variable("CalTime");
        GoldDetectThreshold = Control.AppReader.get("RunMineralDetect").variable("GoldDT");
        ObjectDetectThreshold = Control.AppReader.get("RunMineralDetect").variable("ObjectDT");
        DatiRequirement = Control.AppReader.get("RunMineralDetect").variable("Dati");

        if (DriveOnly) {
            Power = Control.AppReader.get("RunMineralDetect").variable("DOPower");
        } else {
            Power = Control.AppReader.get("RunMineralDetect").variable("ScanPower");
        }

        LeftDrive = Control.PinksHardwareConfig.LeftMotor;
        RightDrive = Control.PinksHardwareConfig.RightMotor;
        //LazerArmServo = Control.PinksHardwareConfig.pLaserArmServo;

//        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect0);
//        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect1);
//        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect2);
//        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect3);

        DistanceConverter();

        for (int i = 0; i < DistanceSensors.size() + 1; i++) {
            HightValues.add(0.0);
        }

        FirstRun = true;
        Calibrating = true;

    }

    @Override
    public void exec() {
        if (Control.RunMineralDetect) {

            CurrentTime = System.currentTimeMillis();
            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();

            if (FirstRun) {
                FirstRun = false;
                Log.i("LazerScan", "First Run");
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                StartTime = CurrentTime;
                RightDrive.setPower(Power);
                LeftDrive.setPower(Power);
                Log.i("LazerScan", "RightDrive Power "+RightDrive.getPower());
                Log.i("LazerScan", "LeftDrive Power "+LeftDrive.getPower());
                LazerArmServo.setPosition(LazerMidPosition);
                sleep(500 );
            }

            //Finish if PinksDrive limit reached and gold not found
            if (Math.abs(RightCurrentTick) >= distanceTicks) {
                Log.i("LazerScan", "Stopped PinksDrive");
                RightDrive.setPower(0);
                LeftDrive.setPower(0);
                LazerArmServo.setPosition(0);
                Complete = true;
            }

            if (!DriveOnly) {

// -------------------------------------------------------------------------------------------------
// The Calibration Records several values, then averages for each sensor individually, so that we know
//the distance from ground, and thus the height of the object;

                if (Calibrating) {
                    //pause(true);
                    if (CurrentTime - StartTime < CalibrationTime) {
                        Cal0.add(DistanceSensors.get(0).getDistance(DistanceUnit.MM));
                        Cal1.add(DistanceSensors.get(1).getDistance(DistanceUnit.MM));
                        Cal2.add(DistanceSensors.get(2).getDistance(DistanceUnit.MM));
                        Cal3.add(DistanceSensors.get(3).getDistance(DistanceUnit.MM));
                    } else {

                        for (int i = 0; i < DistanceSensors.size(); i++) {
                            GroundValues.add(0.0);
                        }

                        for (int i = 0; i < Cal0.size(); i++) {
                            GroundValues.set(0, GroundValues.get(0) + Cal0.get(i));
                        }
                        GroundValues.set(0, GroundValues.get(0) / Cal0.size());

                        for (int i = 0; i < Cal1.size(); i++) {
                            GroundValues.set(1, GroundValues.get(1) + Cal1.get(i));
                        }
                        GroundValues.set(1, GroundValues.get(1) / Cal1.size());

                        for (int i = 0; i < Cal2.size(); i++) {
                            GroundValues.set(2, GroundValues.get(2) + Cal2.get(i));
                        }
                        GroundValues.set(2, GroundValues.get(2) / Cal2.size());

                        for (int i = 0; i < Cal3.size(); i++) {
                            GroundValues.set(3, GroundValues.get(3) + Cal3.get(3));
                        }
                        GroundValues.set(3, GroundValues.get(3) / Cal3.size());

                        Calibrating = false;
                    }
                }

// -------------------------------------------------------------------------------------------------

                if (!Calibrating) {

                    //records heights of objects using sensor height from ground
                    for (int i = 0; i < HightValues.size() - 1; i++) {
                        HightValues.set(i, GroundValues.get(i) - DistanceSensors.get(i).getDistance(DistanceUnit.MM));
                    }


                    //Checks the 4 height values to see if an object is present and act accordingly
                    if (!ScanCommence) {
                        Log.i("LazerScan", "Scan Not Commenced");
                        for (int index = 0; index < (HightValues.size() - 1); index++) {
                            if (HightValues.get(index) >= ObjectDetectThreshold) {
                                ScanCommence = true;
                                NumberOfDati = 0;
                                ServoDirection = true;
                                PreviousTriggerTime = CurrentTime;
                            }
                        }
                    }


                    if (ScanCommence) {
                        Log.i("LazerScan", "Object Detected");
                        //Remember the Highest value while scanning
                        for (int index = 0; index < (HightValues.size() - 1); index++) {
                            if (HightValues.get(index) > HightValues.get(HightValues.size() - 1)) {
                                HightValues.set(HightValues.size() - 1, HightValues.get(index));
                            }
                        }

                        //Counts the number of unique data values detected in scan
                        int i = 0;
                        for (DistanceSensor sensor : DistanceSensors) {
                            for (double PrevDataValue : HightValues) {
                                if (GroundValues.get(i) - sensor.getDistance(DistanceUnit.MM) != PrevDataValue) {
                                    NumberOfDati++;
                                }
                            }
                            i++;
                        }

                        //if it reaches the end of the object before sufficient Dati is acquired, it
                        //pauses the drive and waves the scan arm over the object for More Dati
                        if (HightValues.get(0) < ObjectDetectThreshold && HightValues.get(1) < ObjectDetectThreshold && HightValues.get(2) < ObjectDetectThreshold && HightValues.get(3) < ObjectDetectThreshold) {
                            //Temporarily Stops PinksDrive
                            pause(true);

                            //Waves Laser Arm
                            if (CurrentTime - PreviousTriggerTime >= ArmWavePeriod) {
                                lazerWave(LazerMidPosition);
                                PreviousTriggerTime = CurrentTime;
                            }
                        }

                        //When Enough Dati are gathered, determine if gold and act accordingly.
                        if (NumberOfDati >= DatiRequirement) {
                            ScanCommence = false;
                            if (HightValues.get(HightValues.size() - 1) <= GoldDetectThreshold) {
                                isGold = true;
                                Complete = true;
                            } else {
                                ScanNumber++;
                                resetHights();
                                pause(false);
                            }
                        }
                    }
                }
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step09MineralDetect");
                engine.telemetry.update();

                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

    private void lazerWave(double servoMiddleVal) {
        ServoCurrentPos = LazerArmServo.getPosition();

        if (ServoDirection && ServoCurrentPos <= servoMiddleVal + ArmWaveAmount) {
            LazerArmServo.setPosition(ServoCurrentPos + 0.02);
        } else if (!ServoDirection && ServoCurrentPos >= servoMiddleVal - ArmWaveAmount) {
            LazerArmServo.setPosition(ServoCurrentPos - 0.02);
        } else if (ServoDirection && ServoCurrentPos >= servoMiddleVal + ArmWaveAmount) {
            ServoDirection = false;
        } else if (!ServoDirection && ServoCurrentPos <= servoMiddleVal - ArmWaveAmount) {
            ServoDirection = true;
        }
    }

    private void DistanceConverter() {
        distanceTicks = (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }

    public void resetHights() {
        for (int i = 0; i < HightValues.size(); i++) {
            HightValues.set(i, 0.0);
        }
    }

    public void pause(boolean pause) {
        Log.i("LazerScan", "Pause "+pause);
        if (pause) {
            RightDrive.setPower(0);
            LeftDrive.setPower(0);
        } else {
            RightDrive.setPower(Power);
            LeftDrive.setPower(Power);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addLine("Running Mineral Detect");

        engine.telemetry.addData("Variable Power", Power);
        engine.telemetry.addData("Actual RightDrive Power", RightDrive.getPower());
        engine.telemetry.addData("Actual LeftDrive Power", RightDrive.getPower());

        engine.telemetry.addData("Calibrating", Calibrating);

        engine.telemetry.addData("Scan #", ScanNumber);

        engine.telemetry.addData("Arm Position", LazerArmServo.getPosition());

        for (double sensor: HightValues) {
            engine.telemetry.addData("Hight Value", sensor);
        }

        engine.telemetry.addData("Max Hight", HightValues.get(HightValues.size() - 1));
        engine.telemetry.addData("Dati in one Scan", NumberOfDati);
        if (isGold) {engine.telemetry.addLine("IS GOLD!!!");}
        if (ScanCommence) {engine.telemetry.addLine("Scanning");}
        if (!ScanCommence) {engine.telemetry.addLine("Not Not Scanning");}

    }
}
