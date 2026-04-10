package com.dockainer;

import com.dockainer.update.Updater;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DockainerLauncherApp implements QuarkusApplication {

    static Logger LOGGER = LoggerFactory.getLogger(DockainerLauncherApp.class);

    Updater updater = new Updater();

    void start(@Observes StartupEvent event) {
        if (updater.newVersionAvailable()) {
            LOGGER.info("New version of the launcher is available: {}", updater.latestVersion());

            updater.update();
        }

        LOGGER.warn("You are running version {} of the launcher.", updater.currentVersion());

        LOGGER.info("Starting Dockainer Launcher...");
    }


    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
