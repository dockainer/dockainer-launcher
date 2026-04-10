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
            updater.update();
        }

        System.out.println("Dockainer Launcher started.");
    }

    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
