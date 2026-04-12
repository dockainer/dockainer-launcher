package com.dockainer;

import com.dockainer.update.Updater;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DockainerLauncherApp implements QuarkusApplication {

    static Logger LOGGER = LoggerFactory.getLogger(DockainerLauncherApp.class);

    @Inject
    Updater updater;

    @PostConstruct
    void load() {
        updater.tryUpdate();

        if(!updater.isGithubSync()) {
            LOGGER.warn("No version found on github, you are running the latest version.");
        }

        if (updater.newVersionAvailable()) {
            LOGGER.info("New version of the launcher is available: {}", updater.latestVersion());

            updater.update();
        }

        LOGGER.info("You are running the latest version of the launcher.");
    }


    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}
