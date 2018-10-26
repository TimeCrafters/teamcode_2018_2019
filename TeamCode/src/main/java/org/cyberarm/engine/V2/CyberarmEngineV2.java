package org.cyberarm.engine.V2;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

/**
 * CyberarmEngine Version 2.0 | October 26th 2018
 * AN Experimental reimplementation of GoldfishPi's original Engine system.
 * Designed to be easily maintainable, extensible, and easy to understand.
 */
public abstract class CyberarmEngineV2 extends OpMode {

  public static CyberarmEngineV2 instance;
  //Array To Hold States
  public ArrayList<CyberarmStateV2> cyberarmStates = new ArrayList<>();
  public int activeStateIndex = 0;

  private static String TAG = "PROGRAM.ENGINE: ";

  /**
   * Called when INIT button on Driver Station is pushed
   * ENSURE to call super.init() if you override this method
   */
  public void init() {
    CyberarmEngineV2.instance = this;

    setup();

    for (CyberarmStateV2 state: cyberarmStates) {
      initState(state);
    }
  }

  /**
   * Called when START button on Driver Station is pushed
   * ENSURE to call super.start() if you override this method
   */
  public void start() {
    if (cyberarmStates.size() > 0) {
      runState(cyberarmStates.get(0));
    }
  }

  /**
   * Engine main loop
   * ENSURE to call super.loop() if you override this method
   */
  public void loop() {
    CyberarmStateV2 state;

    try {
       state = cyberarmStates.get(activeStateIndex);
    } catch(IndexOutOfBoundsException e) {
      // The engine is now out of states.
      stop();

      telemetry.addLine("" + this.getClass() + " is out of states to run!");
      telemetry.addLine();
      return;
    }
    telemetry.addLine("Running state: " + activeStateIndex + " of " + (cyberarmStates.size()-1));
    telemetry.addLine();

    if (state.getHasFinished() && state.childrenHaveFinished()) {
      runState(state);
      activeStateIndex++;
    } else {
      stateTelemetry(state);
    }
  }

  /**
   * Recursively calls telemetry() on states
   * @param state State to get telemetry
   */
  private void stateTelemetry(CyberarmStateV2 state) {
    if (!state.getHasFinished()) {
      state.telemetry();
    }

    for(CyberarmStateV2 childState : state.children) {
      if (!childState.getHasFinished()) {
        stateTelemetry(childState);
      }
    }
  }

  /**
   * Called when INIT button on Driver Station is pressed
   * Recursively initiates states
   * @param state State to initiate
   */
  private void initState(CyberarmStateV2 state) {
    state.init();

    for(CyberarmStateV2 childState : state.children) {
      initState(childState);
    }
  }



  /**
   * Called when programs ends or STOP button on Driver Station is pressed
   * Recursively stop states
   * @param state State to stop
   */
  private void stopState(CyberarmStateV2 state) {
    state.stop();

    for(CyberarmStateV2 childState : state.children) {
      stopState(childState);
    }
  }

  /**
   * Recursively start up states
   * @param state State to run
   */
  private void runState(CyberarmStateV2 state) {
    final CyberarmStateV2 finalState = state;
    state.startTime = System.currentTimeMillis();

    new Thread(new Runnable() {
      @Override
      public void run() {
        finalState.run();
      }
    }).start();

    state.start();

    for (CyberarmStateV2 kid : state.children) {
      runState(kid);
    }
  }

  /**
   * Stops every known state
   */
  @Override
  public void stop() {
    for (CyberarmStateV2 state: cyberarmStates) {
      stopState(state);
    }
  }

  /**
   *
   */
  public abstract void setup();

  /**
   * Add state
   * @param state State to add to queue
   */
  public void addState(CyberarmStateV2 state) {
    Log.i(TAG, "Adding cyberarmState "+ state.getClass());
    cyberarmStates.add(state);
  }

  /**
   * Dynamically add state after main loop has started (Robot is active)
   * Calls init() immediately
   * @param state State to add to queue
   */
  public void addStateAtRuntime(CyberarmStateV2 state) {
    Log.i(TAG, "Adding cyberarmState (AT RUNTIME) "+ state.getClass());

    cyberarmStates.add(state);
    state.init();
  }
}
