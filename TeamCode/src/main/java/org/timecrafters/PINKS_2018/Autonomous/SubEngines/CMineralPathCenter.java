package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCDriveToCrater;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCReturnArc;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCReturnReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.OldDriveStates.V2States.V2States.CenterPath.Crater.CCTurnToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.ExtendArm;
import org.timecrafters.PINKS_2018.Autonomous.States.MineralPosId;
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
        addState(new CCMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new CCReturnReverse(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CCReturnArc(engine, AppReader, PinksHardwareConfig));
        addState(new CCMineralStrait(engine, AppReader, PinksHardwareConfig));
        addState(new CCTurnToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new CCDriveToDepot(engine, AppReader, PinksHardwareConfig)); //!
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new ExtendArm(engine, AppReader, PinksHardwareConfig));
        addState(new CCDriveToCrater(engine, AppReader, PinksHardwareConfig)); //!
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
