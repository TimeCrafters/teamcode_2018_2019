package org.timecrafters.PINKS_2018.Autonomous.SubEngines.V2;

/**********************************************************************************************
 * Name: CMineralPathCenter
 * Inputs: engine, mineralPosId, appReader, pinksHardwareConfig
 * Outputs: none
 * Use: Perform the drive path corresponding to a center gold mineral
 * History:
 * 1/20/19 - Made Position Identification work when silver minerals aren't detected
 * 1/19/19 - Made Position Identification work when the leftmost mineral is out of frame
 * 12/27/18 - Used sample code to create basic position identifier
 * 12/15/18 - Tensor Flow Experiment
 * 12/13/18 - Created State
 **********************************************************************************************/


import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Crater.CCTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.PlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.Drive;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class CMineralPathCenter extends SubEngine {
    Engine engine;
    private MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public CMineralPathCenter(Engine engine, MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        //The first Letter in the StepID indicates crater side or depot side (C/D). The second,
        //letter indicates the mineral drive paths Left, Right, or Center (L/R/C).
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCMineralBump"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCReturnReverse"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCReturnArc"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCMineralStrait"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCTurnToDepot"));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCDriveToDepot"));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new PlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Drive(engine, AppReader, PinksHardwareConfig, "CCDriveToCrater"));
    }

    //
    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
