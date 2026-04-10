package com.dockainer.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Updater {

    private static final String VERSION = System.getenv("DOCKAINER_LAUNCHER_VERSION");
    private static final String LAUNCHER = "dockainer-launcher.jar";

    List<String> versions = new LinkedList<>();

    public Updater() {
        this.tryUpdate();
    }

    public void update() {
        var version = this.latestVersion();

        System.out.println("Updating from " + VERSION + " to " + version);

        File target = new File("../../" + LAUNCHER);

        var url = "https://github.com/dockainer/dockainer-launcher/releases/download/" + version + "/" + LAUNCHER;

        if (!this.download(url, target)) {
            System.out.println("Update failed");
            return;
        }

        System.getProperties().setProperty("DOCKAINER_LAUNCHER_VERSION", version);

        System.out.println("Update successful");
    }

    private boolean download(String url, File target) {
        try (InputStream input = new URI(url).toURL().openStream();
             ReadableByteChannel channel = Channels.newChannel(input);
             FileOutputStream output = new FileOutputStream(target)) {

            output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
            System.out.println("Downloaded " + target);

            return true;
        } catch (Exception e) {
            System.out.println("Failed to download " + target + ":");
            e.printStackTrace(System.err);
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

    public boolean newVersionAvailable() {
        String latest = this.latestVersion();
        return latest != null && !Objects.equals(VERSION, latest);
    }
}
