package org.cyberarm.greece.statues;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.cyberarm.engine.CyberarmEngine;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class LaserObjectDetector {
  private final DistanceSensor laserDistanceSensor2, laserDistanceSensor3;
  private ArrayList<DistanceSensor> distanceSensors;
  private ArrayList<Double> allDistances;
  private ArrayList<Double> readings;

  private double distanceKP = 5;
  private double averageHeight;
  private boolean detectingObject = false;

  private final int BLOCK = 0,
                    SPHERE= 1,
                    UNKNOWN=3;

  private int lastDetectedObject = UNKNOWN;

  private int BLOCK_MAX_HEIGHT = 50; // mm
  private int BLOCK_MIN_HEIGHT = 30; // mm

  private int SPHERE_MAX_HEIGHT= 70; // mm
  private int SPHERE_MIN_HEIGHT= 60; // mm

  private int loops = 0;

  public LaserObjectDetector(CyberarmEngine cyberarmEngine) {
    laserDistanceSensor2 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance2");
    laserDistanceSensor3 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance3");

    distanceSensors = new ArrayList<>();
    distanceSensors.add(laserDistanceSensor2);
    distanceSensors.add(laserDistanceSensor3);

    this.allDistances = new ArrayList<>();
    this.readings = new ArrayList<>();

    this.averageHeight = averageDistance();
  }

  // I update
  public void update() {
    allDistances.clear();
    storeDistances();

    if (objectDetected()) {
      senseObject();
    } else {
      if (detectingObject) {
        scanComplete();
      }

      noObjectDetected();
    }

    loops+=1;
  }

  private void storeDistances() {
    for (int i = 0; i < distanceSensors.size(); i++) {
      allDistances.add(mmDistance(distanceSensors.get(i)));
    }
  }

  public void telemetry(OpMode opmode) {
    opmode.telemetry.addLine();
    opmode.telemetry.addLine("Laser Object Detector");
    opmode.telemetry.addData("Sensing Object", detectingObject);
    opmode.telemetry.addData("Last Detected Object", objectType(lastDetectedObject));
    opmode.telemetry.addData("Highest Sensor", getHighestSensor());
    opmode.telemetry.addData("Average", averageHeight);
    opmode.telemetry.addLine();

    for (int i = 0; i < allDistances.size(); i++) {
      opmode.telemetry.addData("allDistances"+i, allDistances.get(i));
    }

    opmode.telemetry.addLine();
    opmode.telemetry.addData("Loops", loops);
    opmode.telemetry.addLine();
  }

  private String objectType(int lastDetectedObject) {
    switch (lastDetectedObject) {
      case SPHERE: return "Sphere";
      case BLOCK:  return "Block";
      case UNKNOWN: return "Unknown";
      default: return "???";
    }
  }

  // Object is detected, record highest sensor to later detection.
  private void senseObject() {
    detectingObject = true;

    readings.add(getHighestSensor());
  }

  // No longer sensing the presence of an object, figure out what is was.
  private void scanComplete() {
    // set last detected object
    detectObject();

    readings.clear();
    detectingObject = false;
  }

  // ?
  private void noObjectDetected() {}

  // Are we detecting an object?
  private boolean objectDetected() {
    if (getHighestSensor() >= distanceKP * 2) {
      return true;
    } else {
      return false;
    }
  }

  private void detectObject() {
    boolean isBlock = false,
            isSphere= false,
            isUnknown=true;

    double least = 100,
           most  = -100;

    for (int i = 0; i < readings.size(); i++) {
      double read = readings.get(i);

      if (read < least) { least = read; }
      else if (read > most) {most = read; }
    }

    // detect unknown
    if (most > SPHERE_MAX_HEIGHT + distanceKP) {
      isBlock   = false;
      isSphere  = false;

      isUnknown = true;

    // detect sphere
    } else if (most - distanceKP >= SPHERE_MIN_HEIGHT) {
      isUnknown = false;
      isBlock = false;

      isSphere = true;

      // detect block
    } else if (most - distanceKP >= BLOCK_MIN_HEIGHT) {
      isUnknown = false;
      isSphere  = false;

      isBlock   = true;

      // detect unknown
    } else if (most - distanceKP < BLOCK_MIN_HEIGHT - distanceKP) {
      isBlock   = false;
      isSphere  = false;

      isUnknown = true;
    }

    if (isSphere) {
      lastDetectedObject = SPHERE;
    } else if (isBlock) {
      lastDetectedObject = BLOCK;
    } else {
      lastDetectedObject = UNKNOWN;
    }
  }

  /*
    Support methods below
   */

  private double getHighestSensor() {
    double highest = 0;

    for (int i = 0; i < allDistances.size(); i++) {
      double sensedHeight = allDistances.get(i);
      if ((sensedHeight) > highest) {
        highest = sensedHeight;
      }
    }

    return highest;
  }

  public double mmDistance(DistanceSensor sensor) {
    return Math.abs(averageHeight-sensor.getDistance(DistanceUnit.MM));
  }

  // Should only be called once
  private double averageDistance() {
    double total = 0;

    for (int i = 0; i < distanceSensors.size(); i++) {
      total += mmDistance(distanceSensors.get(i));
    }

    return total / distanceSensors.size();
  }
}
