package org.cyberarm.engine.V2;

import android.util.Log;

import java.util.ArrayList;

/**
 * A State for use with CyberarmEngineV2
 */
public abstract class CyberarmStateV2 implements Runnable {

  private volatile boolean isRunning, hasFinished;
  public static String TAG = "PROGRAM.STATE";
  public org.cyberarm.engine.V2.CyberarmEngineV2 cyberarmEngine = CyberarmEngineV2.instance;
  public ArrayList<CyberarmStateV2> children;
  public long startTime;

  protected CyberarmStateV2() {
    startTime   = 0;
    isRunning   = false;
    hasFinished = false;

    children   = new ArrayList<>();
  }


  /**
   * Called when INIT button on Driver Station is pushed
   */
  public void init() {
  }

  /**
   * Called when state has begin to run
   */
  public void start() {
  }

  /**
   * Called while State is running
   */
  public abstract void exec();

  /**
   * State's main loop, calls exec() until hasFinished is true
   * DO NO OVERRIDE
   */
  @Override
  public void run() {
    isRunning = true;
    while (!hasFinished) {
      exec();
    }
    isRunning = false;
  }

  /**
   * Place telemetry calls in here instead of inside exec() to have them displayed correctly on the Driver Station
   * (States update thousands of times per second, resulting in missing or weirdly formatted telemetry if telemetry is added in exec())
   */
  public void telemetry() {
  }

  /**
   * Called when Engine is finished
   */
  public void stop() {
  }

  /**
   * Add a state which runs in parallel with this one
   */
  public void addParallelState(CyberarmStateV2 state) {
    Log.i(TAG, "Adding " + state.getClass() + " to " + this.getClass());
    children.add(state);
  }

  /**
   * Returns whether or not state has children
   * @return True if state has children, false otherwise
   */
  public boolean hasChildren() {
    return (children.size() > 0);
  }

  /**
   * Have all of the states children finished running themselves?
   * @return Wether or not all children have finished running
   */
  public boolean childrenHaveFinished() {
    return childrenHaveFinished(children);
  }

  /**
   * Have all of the states children finished running themselves?
   * @param kids ArrayList of children to check for hasFinished()
   * @return Whether or not all children have finished running
   */
  public boolean childrenHaveFinished(ArrayList<CyberarmStateV2> kids) {
    boolean allDone = true;

      for (CyberarmStateV2 state : kids) {
        if (!state.hasFinished) {
          allDone = false;
          break;
        } else {
          if (!state.childrenHaveFinished()) {
            allDone = false;
            break;
          }
        }
      }

    return allDone;
  }

  /**
   *
   * @return The number of milliseconds this state has been running for
   */
  public double runTime() {
    return (System.currentTimeMillis() - startTime);
  }

  /**
   * Set whether state has finished or not
   * @param value
   */
  public void setHasFinished(boolean value) {
    hasFinished = value;
  }

  /**
   *
   * @return Get value of hasFinished
   */
  public boolean getHasFinished() {
    return hasFinished;
  }

  /**
   *
   * @return Get value of isRunning
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**
   *
   * @param timems How long to sleep in milliseconds
   */
  public void sleep(long timems) {
    try {
      Thread.sleep(timems);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param width How many characters wide to be
   * @param percentCompleted Number between 0.0 and 100.0
   * @param bar What character to draw the completion bar with
   * @param padding What character to draw non-completed bar with
   * @return A string
   */
  public String progressBar(int width, double percentCompleted, String bar, String padding) {
    String percentCompletedString = "" + Math.round(percentCompleted) + "%";
    double activeWidth = (width - 2) - percentCompletedString.length();

    String string = "[";
    double completed = (percentCompleted / 100.0) * activeWidth;

    for (int i = 0; i <= ((int) activeWidth); i++) {
      if (i == ((int) activeWidth) / 2) {
        string += percentCompletedString;
      } else {
        if (i <= (int) completed && (int) completed > 0) {
          string += bar;
        } else {
          string += padding;
        }
      }
    }

    string += "]";
    return string;
  }

  /**
   *
   * @param width How many characters wide to be
   * @param percentCompleted Number between 0.0 and 100.0
   * @return A string
   */
  public String progressBar(int width, double percentCompleted) {
    return progressBar(width, percentCompleted, "=", "  ");
  }
}