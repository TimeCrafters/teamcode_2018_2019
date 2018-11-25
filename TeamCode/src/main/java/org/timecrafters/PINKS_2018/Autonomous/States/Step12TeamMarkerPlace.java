package org.timecrafters.PINKS_2018.Autonomous.States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.PINKS_2018.Autonomous.Support.ArchitectureControl;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Step12TeamMarkerPlace extends State {
    private boolean Complete = false;
    public ArchitectureControl Control;
    private DcMotor ClipArm;
    private int PlacePosition;
    private long WaitTime;
    private boolean ReachedTarget;


    public Step12TeamMarkerPlace(Engine engine, ArchitectureControl control) {
        this.engine = engine;
        this.Control = control;
    }

    public void init() {
        ClipArm = Control.PinksHardwareConfig.pClipArm;
        PlacePosition = Control.AppReader.get("RunTeamMarkerPlace").variable("Pos"); // probably 130
        WaitTime = Control.AppReader.get("RunTeamMarkerPlace").variable("Pause");
        ClipArm.setDirection(DcMotorSimple.Direction.REVERSE);
        ClipArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ClipArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void exec() {
        if (Control.RunTeamMarkerPlace) {

            if (!ReachedTarget) {
                ClipArm.setTargetPosition(PlacePosition);
                ClipArm.setPower(0.5);
                if (ClipArm.getCurrentPosition() >= PlacePosition) {
                    ReachedTarget = true;
                    sleep(WaitTime);
                }
            }

            if (ReachedTarget) {
                ClipArm.setTargetPosition(0);
                ClipArm.setPower(-0.15);
                if (ClipArm.getCurrentPosition() <= 0) {
                    sleep(WaitTime);
                    ClipArm.setPower(0);
                    Complete = true;
                }
            }

            if (Complete) {
                engine.telemetry.addLine("Completed Step12TeamMarkerPlace");
                engine.telemetry.update();
                sleep(1000);
                setFinished(true);
            }
        } else {
            setFinished(true);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("Reached Target", ReachedTarget);
        engine.telemetry.addData("Current Position", ClipArm.getCurrentPosition());
    }
}
