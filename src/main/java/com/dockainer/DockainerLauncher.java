package com.dockainer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class DockainerLauncher {

    static void main(String[] args) {
        System.out.println("Starting Dockainer Launcher...");
        Quarkus.run(DockainerLauncherApp.class, args);
    }
}
