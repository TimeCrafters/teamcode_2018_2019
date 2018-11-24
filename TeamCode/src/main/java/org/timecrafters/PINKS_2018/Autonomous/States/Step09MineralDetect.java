package org.timecrafters.PINKS_2018.Autonomous.States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class Step09MineralDetect extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private long CurrentTime;
    private boolean ServoDirection;
    private boolean FirstRun = true;
    private double ArmWaveAmount = 0.25;
    private long ArmWavePeriod = 50;
    private ArrayList<DistanceSensor> DistanceSensors;
    private Servo LazerArmServo;
    public double BuildHeightMM;
    private int NumberOfDati;
    private long PreviousTriggerTime;
    private double GoldDetectThreshold;
    public int ScanNumber;
    public boolean isGold;
    private boolean ScanCommence;
    private ArrayList<Double> HightValues;
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

    


    public Step09MineralDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
    }

    public void init() {
        DriveOnly = Control.AppReader.get("RunMineralDetect").variable("OnlyDrive");
        distanceIN = Control.AppReader.get("RunMineralDetect").variable("distanceIN");
        Power = Control.AppReader.get("RunMineralDetect").variable("Power");

        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;

        LeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

    }

    @Override
    public void exec() {
        if (Control.RunMineralDetect) {
            CurrentTime = System.currentTimeMillis();
            if (FirstRun) {
                FirstRun = false;
                PreviousTriggerTime = CurrentTime;
                RightDrive.setPower(Power);
                LeftDrive.setPower(Power);
            }

            RightCurrentTick = RightDrive.getCurrentPosition();
            LeftCurrentTick = LeftDrive.getCurrentPosition();


            if (Math.abs(RightCurrentTick) >= distanceTicks) {
                RightDrive.setPower(0);
                LeftDrive.setPower(0);
                Complete = true;
            }


            if (!DriveOnly) {

                ScanCommence = true;

                if (ScanCommence) {
                    for (int index = 0; index < (HightValues.size() - 2); index++) {
                        if (HightValues.get(index) > HightValues.get(HightValues.size() - 1)) {
                            HightValues.set(HightValues.size() - 1, HightValues.get(index));
                        }

                    }

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
