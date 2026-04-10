package com.dockainer.update;


import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class Updater {


    @ConfigProperty(name = "quarkus.application.version")
    String version;

    private static final String LAUNCHER = "dockainer-launcher.jar";

    private static final Logger LOGGER = LoggerFactory.getLogger(Updater.class);

    List<String> versions = new LinkedList<>();

    public Updater() {
        this.tryUpdate();
        System.out.println(currentVersion());
    }

    public void update() {
        var latestVersion = this.latestVersion();

        LOGGER.info("Updating to version {}", latestVersion);

        File target = new File("../../" + LAUNCHER);

        var url = "https://github.com/dockainer/dockainer-launcher/releases/download/" + latestVersion + "/" + LAUNCHER;

        if (!this.download(url, target)) {
            LOGGER.error("Update failed");
            return;
        }

        this.version = latestVersion;

        LOGGER.info("Updated successfully to version {}", latestVersion);
    }

    private boolean download(String url, File target) {
        try (InputStream input = new URI(url).toURL().openStream();
             ReadableByteChannel channel = Channels.newChannel(input);
             FileOutputStream output = new FileOutputStream(target)) {

            output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);

            LOGGER.info("Downloaded {}", target);

            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to download {}", target, e);
            return false;
        }
    }

    public void tryUpdate() {
        this.versions.clear();
        this.versions.addAll(GitHubReader.getLatestTags());
    }

    public String latestVersion() {
        return versions.getFirst();
    }

    public String currentVersion() {
        return this.version;
    }

    public boolean newVersionAvailable() {
        String latest = this.latestVersion();
        return latest != null && !Objects.equals(this.version, latest);
    }
}
