package org.cyberarm.NeXT;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StateConfiguration {
  public class DataStruct {
    public String name;
    public boolean enabled;

    public DataStruct() {
    }

    public DataStruct(String name, boolean enabled) {
      this.name = name;
      this.enabled = enabled;
    }
  }


  private boolean loadSuccessful = false;
  private ArrayList<DataStruct> dataStructs;
  private HashMap<String, DataStruct> actions;

//  public boolean allowDrop, allowPostDropUTurn, allowMineralPositioning;

  public StateConfiguration() {
    dataStructs = new ArrayList<>();
    actions = new HashMap<>();

    // Load configuration file.
    if (loadJSON()) {
      Log.i("TC_CONFIG", "Successfully loaded configuration file.");

      for (DataStruct dataStruct : dataStructs) {
        actions.put(dataStruct.name, dataStruct);
      }

    } else {
      Log.e("TC_CONFIG", "FAILED TO LOAD CONFIGURATION FILE!");
    }
  }

  public DataStruct get(String key) {
    return actions.get(key);
  }

  public boolean allow(String key) {
    try {
      return get(key).enabled;
    } catch (NullPointerException e) {
      Log.e("TC_CONFIG", ""+key+" is not found!");
      return false;
    }
  }

  private boolean loadJSON() {
    boolean loadSuccessful = false;

    File file = new File(getDirectory() + File.separator + "config.json");
    StringBuilder text = new StringBuilder();

    if (file.exists()) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
          text.append(line);
          text.append('\n');
        }
        br.close();

        Gson gson = new Gson();
        DataStruct[] array = gson.fromJson(text.toString(), DataStruct[].class);
        this.dataStructs = new ArrayList<>(Arrays.asList(array));

        loadSuccessful = true;

      } catch (IOException e) {
        System.out.println(e);
        // TODO: handle this
      }
    }

    this.loadSuccessful = loadSuccessful;
    return loadSuccessful;
  }

  private String getDirectory() {
    return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "FIRST_TC_CONFIG";
  }

  public boolean wasLoadSuccessful() { return loadSuccessful; }
}
