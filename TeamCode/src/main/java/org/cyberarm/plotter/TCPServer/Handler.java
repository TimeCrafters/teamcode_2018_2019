package org.cyberarm.plotter.TCPServer;

import android.system.ErrnoException;
import android.util.Base64;
import android.util.Log;

import org.cyberarm.driver.Driver;
import org.cyberarm.driver.states.Home;
import org.cyberarm.driver.states.Move;
import org.cyberarm.driver.states.PenDown;
import org.cyberarm.driver.states.PenUp;
import org.cyberarm.driver.states.Stop;
import org.cyberarm.engine.CyberarmEngine;

import java.io.IOException;
import java.net.SocketException;

public class Handler {
  private Client client;

  public Handler(Client client) throws IOException,SocketException,ErrnoException {
    this.client = client;

    client.write(encode(client.uuid));
    client.flush();
    loop();
  }

  private void loop() throws IOException, SocketException, ErrnoException {
    while (client.connected()) {
      String string = client.read();
      try {
        string = new String(decode(string));
      } catch (IllegalArgumentException error) {
        error.printStackTrace();
      }

      if (!string.isEmpty()) {
        Log.i("Handler", "Received string: "+string);
        if (client.authenticated) {
          client.write(respond(string));
        } else {
          if (string.equals(client.uuid)) {
            client.authenticated = true;
          } else {
            client.write(encode("401"));
          }
        }
        client.flush();
      }

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    System.out.println("Client " + client.uuid + " disconnected.");
    client.close();
  }

  private String respond(String request) {
    String response = "";

    switch (request.split(" ")[0]) {
      case "download": {
        Log.i("Handler", "Received 'download' command, processing...");

        response += handleDownload(request); // handleDownload returns a string to pass to client
        break;
      }
      case "stop": {
        Log.i("Handler", "Halting plotter!");
        CyberarmEngine.instance.cyberarmStates.clear();
        CyberarmEngine.instance.activeStateIndex = 0;
        CyberarmEngine.instance.addState(new Stop());
        response+="Halting plotter";
        break;
      }
      case "move": {
        String substring = sub(request, "move");
        try {
          String[] list = substring.trim().split(":");
          response += "Moving to x: " + list[0] + ", y: " + list[1];
          ((Driver) Driver.instance).pendingWork = true;
          CyberarmEngine.instance.addState(new Move(Integer.parseInt(list[0]), Integer.parseInt(list[1])));
        } catch (ArrayIndexOutOfBoundsException e) {
          response+="ERROR: malformed string: "+request;
          e.printStackTrace();
        }
        break;
      }
      case "home": {
        response += "Homing to 0:0";
        ((Driver) Driver.instance).pendingWork = true;
        CyberarmEngine.instance.addState(new Home());
        break;
      }
      case "pen_up": {
        response += "Raised Pen";
        ((Driver) Driver.instance).pendingWork = true;
        CyberarmEngine.instance.addState(new PenUp());
        break;
      }
      case "pen_down": {
        response += "Lowered Pen";
        ((Driver) Driver.instance).pendingWork = true;
        CyberarmEngine.instance.addState(new PenDown());
        break;
      }
      case "status": {
        response += "TIME:" + System.currentTimeMillis() + "\n";
        response += "PEN: "+ CyberarmEngine.instance.hardwareMap.crservo.get("svPen").getPower()+"\n";
        if (((Driver) Driver.instance).offlineDebugging) {
          response += "X: " + ((Driver) Driver.instance).xAxisV.getCurrentPosition() + "\n";
          response += "Y: " + ((Driver) Driver.instance).yAxisV.getCurrentPosition() + "\n";
        } else {
          response += "X: " + CyberarmEngine.instance.hardwareMap.dcMotor.get("xAxis").getCurrentPosition() + "\n";
          response += "Y: " + CyberarmEngine.instance.hardwareMap.dcMotor.get("yAxis").getCurrentPosition() + "\n";
        }
        response += "X_ENDSTOP: "+CyberarmEngine.instance.hardwareMap.touchSensor.get("xAxisEndStop").isPressed()+"\n";
        response += "Y_ENDSTOP: "+CyberarmEngine.instance.hardwareMap.touchSensor.get("yAxisEndStop").isPressed()+"\n";
        break;
      }
      case "00000000": {
        response += "admin";
        break;
      }
      case "help": {
        response += "Thank you for using the helpline:\n\n";
        response += "download RCODE - accepts list of rcode instructions separated by newlines.\n";
        response += "stop - Attempts to immediately stop plotter.\n";
        response += "status - returns machine status.\n";
        response += "home\n";
        response += "pen_up\n";
        response += "pen_down\n";
        response += "move X:Y - Moves head to XY position\n";
        response += "00000000 - ADMIN\n";
        response += "";
        break;
      }

      default: {
        response += "Unknown command '" + request + "', use `help` if needed.";
        break;
      }
    }

    System.out.println(response);
    response = encode(response);
    System.out.println("Encoded: "+response);
//    response+="\n";
    return response;
  }

  private String handleDownload(String request) {
    String substring = sub(request, "download");
    String[] list = substring.trim().split("\n");
    String bit;
    int numberOfEvents = 0;

    for (int i = 0; i < list.length; i++) {
       bit = list[i].split(" ")[0];
      switch (bit) {
        case "pen_down": {
          Log.i("Handler", "download processed pen_down");
          CyberarmEngine.instance.addState(new PenDown());
          numberOfEvents++;
          break;
        }
        case "pen_up": {
          Log.i("Handler", "download processed pen_up");
          CyberarmEngine.instance.addState(new PenUp());
          numberOfEvents++;
          break;
        }
        case "move": {
          Log.i("Handler", "download processed move");
          try {
            String localSubstring = sub(list[i], "move");
            String[] localList = localSubstring.trim().split(":");

            ((Driver) Driver.instance).pendingWork = true;
            CyberarmEngine.instance.addState(new Move(Integer.parseInt(localList[0]), Integer.parseInt(localList[1])));
            numberOfEvents++;
          } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
          }
          break;
        }
        case "home": {
          Log.i("Handler", "download processed home");
          numberOfEvents++;
          CyberarmEngine.instance.addState(new Home());
          break;
        }
        default: {
          Log.i("Handler", "download failed to process: "+bit);
          break;
        }
      }
    }

    return "Download processed "+numberOfEvents+" events";
  }

  public String encode(String string) {
    return new String(Base64.encode(string.getBytes(), Base64.URL_SAFE));
  }

  public String decode(String string) {
    return new String(Base64.decode(string, Base64.URL_SAFE));
  }

  /* NOTE: will recursively remove 'removed' string from 'string' */
  public String sub(String string, String removed) {
    String actor = "";
    try {
      actor = string.replace(removed, "");
    } catch (StringIndexOutOfBoundsException error) {
    }

    return actor.trim();
  }
}
