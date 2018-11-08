package org.cyberarm.NeXT.states;

import android.media.AudioManager;
import android.media.ToneGenerator;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class CyberarmPanic extends CyberarmStateV2 {
  ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
  private boolean tonePlaying;
  private long toneStartAt;
  private String message;

  public CyberarmPanic(String message) {
    this.message = message;
  }

  @Override
  public void exec() {
    if (runTime() < 10_000) {
      if (!tonePlaying) {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE, 250);
        toneStartAt = System.currentTimeMillis();
        tonePlaying = true;

      } else {
        if (System.currentTimeMillis() - toneStartAt >= 275) {
          tonePlaying = false;
        }
      }

    } else {
      stop();
    }
  }

  @Override
  public void stop() {
    toneGenerator.stopTone();
    toneGenerator.release();
    setHasFinished(true);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addData("PANIC MESSAGE", ""+message);
  }
}
