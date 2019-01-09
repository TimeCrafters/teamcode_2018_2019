package org.timecrafters.PINKS_2018.Autonomous.SubEngines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step09DCMineralBump;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step10DCDriveToDepot;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step11DCReverse;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step12DCTurn;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.CenterPath.Depot.Step13DCMineralStrait;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.Step07MineralPosId;
import org.timecrafters.PINKS_2018.Autonomous.States.V2States.StepPlaceMarker;
import org.timecrafters.PINKS_2018.Autonomous.Support.PinksHardwareConfig;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class DMineralPathCenter extends SubEngine {
    Engine engine;
    private Step07MineralPosId GoldPosIdentifier;
    private PinksHardwareConfig PinksHardwareConfig;
    private StateConfiguration AppReader;

    public DMineralPathCenter(Engine engine, Step07MineralPosId mineralPosId, StateConfiguration appReader, PinksHardwareConfig pinksHardwareConfig) {
        this.engine = engine;
        this.AppReader = appReader;
        this.PinksHardwareConfig = pinksHardwareConfig;
        this.GoldPosIdentifier = mineralPosId;
    }

    @Override
    public void setProcesses() {
        addState(new Step09DCMineralBump(engine, AppReader, PinksHardwareConfig));
        addState(new Step10DCDriveToDepot(engine, AppReader, PinksHardwareConfig));
        addState(new StepPlaceMarker(engine, AppReader, PinksHardwareConfig));
        addState(new Step11DCReverse(engine, AppReader, PinksHardwareConfig));
        addState(new Step12DCTurn(engine, AppReader, PinksHardwareConfig));
        addState(new Step13DCMineralStrait(engine, AppReader, PinksHardwareConfig));
    }

    @Override
    public void evaluate() {
        if (GoldPosIdentifier.GoldPosition == 2) {
            setRunable(true);
        }
    }
}
