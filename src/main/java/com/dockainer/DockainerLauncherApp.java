package com.dockainer;

import com.dockainer.update.Updater;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class DockainerLauncherApp implements QuarkusApplication {

    Updater updater = new Updater();

    void start(@Observes StartupEvent event) {
        if (updater.newVersionAvailable()) {
            System.out.println("New version of the launcher is available: " + updater.latestVersion());
            updater.update();
        }

        System.out.println("You are running the latest version of the launcher.");

        System.out.println("Dockainer Launcher started.");
    }


    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
