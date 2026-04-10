package com.dockainer;

import com.dockainer.update.Updater;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class DockainerLauncher {

    static Updater updater = new Updater();

    static void main(String[] args) {
        checkForUpdates();

        System.out.println("Starting Dockainer Launcher...");
        Quarkus.run(DockainerLauncherApp.class, args);
    }

    private static void checkForUpdates() {
        if (updater.newVersionAvailable()) {
            System.out.println("New version of the launcher is available: " + updater.latestVersion());
            return;
        }
        System.out.println("You are running the latett version of the launcher.");
    }
}
