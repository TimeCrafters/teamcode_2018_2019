package org.cyberarm.engine;

import android.media.ToneGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by t420 on 9/29/2016.
 */


public abstract class CyberarmState implements Runnable {

  private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:ms");
  private Date date = new Date();
  private volatile boolean isFinished = false;
  private byte layer = 0;
  public static String TAG = "PROGRAM.STATE";
  public CyberarmEngine cyberarmEngine = CyberarmEngine.instance;
  private long toneGeneratorStartedAt = 0;


  public void init() {
  }

  public void start() {
  }

  ;

  public abstract void exec();

  @Override
  public void run() {
    while (!isFinished) {
      exec();
    }
  }

  public void telemetry() {
  }

  public void stop() {
    /*
     * Override this and put your ending crap in here
     * */
  }

  public void playErrorTone() {
    if (cyberarmEngine.driver != null) {
      if (System.currentTimeMillis() - toneGeneratorStartedAt > 200) {
        cyberarmEngine.driver.toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        toneGeneratorStartedAt = System.currentTimeMillis();
      }
    }
  }

  public void setFinished(boolean value) {
    isFinished = value;
  }

  public boolean getIsFinished() {
    return isFinished;
  }

  public void sleep(long timems) {
    try {
      Thread.sleep(timems);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

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

  public String progressBar(int width, double percentCompleted) {
    return progressBar(width, percentCompleted, "=", "  ");
  }
}