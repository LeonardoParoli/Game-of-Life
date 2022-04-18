package com.gof.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
   public static void main (String[] arg) {
      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
      config.setTitle("Game of Life");
      config.setWindowedMode(800, 600);
      config.useVsync(true);
      config.setForegroundFPS(60);
      config.setResizable(false);
      new Lwjgl3Application(new GameRunner(), config);
   }
}
