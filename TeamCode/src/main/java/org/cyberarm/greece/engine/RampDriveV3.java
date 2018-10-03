package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@Disabled
//@TeleOp(name = "RampDriveV3")
public class RampDriveV3 extends OpMode {
    private DcMotor motor;
    private long timeToRampMS = 5000;
    private long startTimeMs;
    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("leftMotor");
        motor.setPower(0.0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        startTimeMs = System.currentTimeMillis();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        double power = ((double) (System.currentTimeMillis()-startTimeMs) / (double) timeToRampMS);
        power = Range.clip(power, -1.0, 1.0);
        motor.setPower(power);

        telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        telemetry.addData("Power (ratio)", power);
        telemetry.addData("Time elapsed", System.currentTimeMillis()-startTimeMs);
        telemetry.addData("Ramp Time (ms)", timeToRampMS);
        telemetry.addLine();
        int devisor = 5;
        int percent = (int) ((power*100.0)/devisor);
        String progress = new String(""+percent*devisor+"% ");
        for (int i = 0; i < percent; i++) {
            progress+="☼";
        }
        telemetry.addLine(progress);
        telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }
}
