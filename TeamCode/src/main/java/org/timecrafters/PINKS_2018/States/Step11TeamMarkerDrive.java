package org.timecrafters.PINKS_2018.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.PINKS_2018.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step11TeamMarkerDrive extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private int DriveStep;
    private boolean FirstRun = true;
    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    private double Power;
    private int RightCurrentTick;
    private int LeftCurrentTick;
    private int distanceTicks;



    public Step11TeamMarkerDrive(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        LeftDrive = Control.PinksHardwareConfig.pLeftMotor;
        RightDrive = Control.PinksHardwareConfig.pRightMotor;
        DriveStep = 1;
    }

    @Override
    public void exec() {
        if (Control.RunTeamMarkerDrive) {
            
            if (DriveStep == 1) {

            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step11TeamMarkerDrive");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }


    }

    private int DistanceConverter(int distanceIN, int whealCircumference) {
        return (int) ((distanceIN * 288) / (whealCircumference * Math.PI));
    }


}
