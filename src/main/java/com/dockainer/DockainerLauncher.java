package com.dockainer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public class DockainerLauncher {

    static Logger LOGGER = LoggerFactory.getLogger(DockainerLauncher.class);

    static void main(String[] args) {
        LOGGER.info("Starting Dockainer Launcher...");

        Quarkus.run(DockainerLauncherApp.class, args);
    }
}
