package mx.sugus.braid.core.plugin;

import mx.sugus.braid.core.BrideCodegenSettings;

/**
 * Default dependencies.
 */
public final class DefaultDependencies {

    public static final DependencyKey<BrideCodegenSettings> SETTINGS = DependencyKey.from("settings");

    private DefaultDependencies() {
    }
}


