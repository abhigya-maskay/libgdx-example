package com.badlogic.drop.desktop

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import com.badlogic.drop.Drop

object DesktopLauncher extends App:
  val config = new Lwjgl3ApplicationConfiguration()
  config.setTitle("drop")
  new Lwjgl3Application(new Drop(), config)
