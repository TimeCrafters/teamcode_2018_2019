package org.timecrafters.PINKS_2018.Autonomous.States;

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
    private double SensorGround0;
    private double SensorGround1;
    private double SensorGround2;
    private double SensorGround3;
    private Servo LazerArmServo;
    public double BuildHeightMM;
    private int NumberOfDati;
    private double GoldDetectThreshold;
    public int ScanNumber;
    public boolean isGold;
    private boolean ScanCommence;
    private ArrayList<Double> HightValues;
    private ArrayList<Double> Cal0;
    private ArrayList<Double> Cal1;
    private ArrayList<Double> Cal2;
    private ArrayList<Double> Cal3;
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

    


    public Step09MineralDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
        this.Cal0 = new ArrayList<>();
        this.Cal1 = new ArrayList<>();
        this.Cal2 = new ArrayList<>();
        this.Cal3 = new ArrayList<>();
    }

    public void init() {
        DriveOnly = Control.AppReader.get("RunMineralDetect").variable("OnlyDrive");
        distanceIN = Control.AppReader.get("RunMineralDetect").variable("distanceIN");
        CalibrationTime = Control.AppReader.get("RunMineralDetect").variable("CalTime");


        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;

        if (DriveOnly) {
            Power = Control.AppReader.get("RunMineralDetect").variable("DOPower");
        } else {
            Power = Control.AppReader.get("RunMineralDetect").variable("ScanPower");
        }

        DistanceConverter();

        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect0);
        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect1);
        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect2);
        DistanceSensors.add(Control.PinksHardwareConfig.pMineralDetect3);

        LazerArmServo = Control.PinksHardwareConfig.pLaserArmServo;
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);
        HightValues.add(0.0);

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
                LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                StartTime = CurrentTime;
                PreviousTriggerTime = CurrentTime;
                RightDrive.setPower(Power);
                LeftDrive.setPower(Power);
            }

            //Finish if Drive limit reached and gold not found
            if (Math.abs(RightCurrentTick) >= distanceTicks) {
                RightDrive.setPower(0);
                LeftDrive.setPower(0);
                Complete = true;
            }

            if (!DriveOnly) {

                if (Calibrating) {
                    pause(true);
                    if (CurrentTime - StartTime < CalibrationTime) {
                        Cal0.add(DistanceSensors.get(0).getDistance(DistanceUnit.MM));
                        Cal1.add(DistanceSensors.get(1).getDistance(DistanceUnit.MM));
                        Cal2.add(DistanceSensors.get(2).getDistance(DistanceUnit.MM));
                        Cal3.add(DistanceSensors.get(3).getDistance(DistanceUnit.MM));
                    } else {
                        
                    }


                }


                if (ScanCommence) {
                    //Stops drive temporarily
                    pause(true);

                    //Remember the Highest value while scanning
                    for (int index = 0; index < (HightValues.size() - 2); index++) {
                        if (HightValues.get(index) > HightValues.get(HightValues.size() - 1)) {
                            HightValues.set(HightValues.size() - 1, HightValues.get(index));
                        }

                    }

                    //Waves Laser Arm
                    if (CurrentTime - PreviousTriggerTime >= ArmWavePeriod) {
                        lazerWave(0.7);
                        PreviousTriggerTime = CurrentTime;
                    }


                }
            }


            if (Complete) {
                engine.telemetry.addLine("Completed Step09MineralDetect");
                engine.telemetry.update();
                sleep(1000);
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



    public void pause(boolean pause) {
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
    }
}
