package mx.sugus.braid.core.plugin;

import mx.sugus.braid.core.BraidCodegenSettings;

/**
 * Default dependencies.
 */
public final class DefaultDependencies {

    public static final DependencyKey<BraidCodegenSettings> SETTINGS = DependencyKey.from("settings");

    private DefaultDependencies() {
    }
}


