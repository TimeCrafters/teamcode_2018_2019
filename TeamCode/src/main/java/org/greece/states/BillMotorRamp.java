package org.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.engine.State;

public class BillMotorRamp extends State {
    private int travelDistance, travelOffset;
    private DcMotor motor;
    private long timeToRampMS;
    private long startTimeMs;
    private boolean hasRampedUp, hasTravelled = false;
    private double power;

    public BillMotorRamp(String motorName, long timeToRampMS, int travelDistance) {
        this.motor = engine.hardwareMap.dcMotor.get(motorName);
        this.timeToRampMS = timeToRampMS;
        this.travelDistance = travelDistance;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        startTimeMs = System.currentTimeMillis();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void exec() {
        if (!hasRampedUp) {
            power = ((double) (System.currentTimeMillis() - startTimeMs) / (double) timeToRampMS);
            if (power >= 1.0) { hasRampedUp = true; travelOffset = motor.getCurrentPosition(); }
        } else if (hasRampedUp && !hasTravelled) {
            if (Math.abs(motor.getCurrentPosition()) >= relativeTravelDistance()) { hasTravelled = true; startTimeMs = System.currentTimeMillis(); }
        } else {
            // Ramp Down
            power = ((double) (System.currentTimeMillis() - startTimeMs) / (double) timeToRampMS);
            power = Math.abs(power-1.0);
        }


        power = Range.clip(power, -1.0, 1.0);
        motor.setPower(power);

        if (hasRampedUp && hasTravelled && motor.getPower() <= 0.05) { motor.setPower(0); setFinished(true); }
    }

    public int relativeTravelDistance(int distance, boolean negate) {
        if (negate) {
            if (travelOffset > distance) {
                return travelOffset - distance;
            } else {
                return distance - travelOffset;
            }
        } else {
            if (travelOffset > distance) {
                return travelOffset + distance;
            } else {
                return distance + travelOffset;
            }
        }
    }

    public int relativeTravelDistance() {
        return relativeTravelDistance(travelDistance, false);
    }

        public void telemetry() {
        engine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        if (hasRampedUp) {
            engine.telemetry.addData("Ramp Up?", "✅");
        } else {
            engine.telemetry.addData("Ramp Up?", "X");
        }
        if (hasTravelled) {
            engine.telemetry.addData("Travelled?", "✅");
        } else {
            engine.telemetry.addData("Travelled?", "X");
        }

        if (!hasRampedUp) {
            engine.telemetry.addData("Power (ratio)", power);
            engine.telemetry.addData("Time elapsed", System.currentTimeMillis() - startTimeMs);
            engine.telemetry.addData("Ramp Time (ms)", timeToRampMS);
            engine.telemetry.addLine();
            engine.telemetry.addLine(progressBar(25, power * 100.0));

        } else if (hasRampedUp && !hasTravelled) {
            double distanceRatio = ((motor.getCurrentPosition()-travelOffset) / (double) travelDistance);
            engine.telemetry.addData("Distance (ratio)", distanceRatio);
            engine.telemetry.addData("Travelled", motor.getCurrentPosition()-travelOffset);
            engine.telemetry.addLine();
            engine.telemetry.addLine(progressBar(25, distanceRatio * 100.0));

        } else {
            engine.telemetry.addData("Power (ratio)", power);
            engine.telemetry.addData("Time elapsed", System.currentTimeMillis() - startTimeMs);
            engine.telemetry.addData("Ramp Time (ms)", timeToRampMS);
            engine.telemetry.addLine();
            engine.telemetry.addLine(progressBar(25, Math.abs(power-1.0) * 100.0));
        }

        engine.telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }
}
