package org.timecrafters.Nartaniel.Autonomous.Arcitecture.Arc1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.Nartaniel.Autonomous.Arcitecture.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.ArrayList;

public class MineralDetect extends State {
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

    


    public MineralDetect(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
        this.HightValues = new ArrayList<>();
        this.DistanceSensors = new ArrayList<>();
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
    }

    @Override
    public void exec() {
        if (Control.RunMineralDetect) {
            CurrentTime = System.currentTimeMillis();
            if (FirstRun) {
                FirstRun = false;
                PreviousTriggerTime = CurrentTime;
                engine.telemetry.addLine("FirstRun");
                engine.telemetry.update();
                sleep(1000);
            }


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

    @Override
    public void telemetry() {
        engine.telemetry.addData("Current Time", CurrentTime);
        engine.telemetry.addData("PreviousTime", PreviousTriggerTime);
    }
}
