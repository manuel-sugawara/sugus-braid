$version: "1.0"

namespace mx.sugus.braid.plugins.data

/// Possibles modes to check if a member is nullable.
enum NullabilityCheckMode {
    /// All optional, all members are consider nullable.
    ALL_OPTIONAL = "all-optional"
    /// Client mode, equivalent to Smithy CheckMode#CLIENT.
    CLIENT = "client"
    /// Client careful mode, equivalent to Smithy CheckMode#CLIENT_CAREFUL.
    CLIENT_CAREFUL = "client-careful"
    /// Server mode, equivalent to Smithy CheckMode#SERVER.
    SERVER = "server"
}

/// Configuration settings for the
structure DataPluginConfig {
    /// The nullability mode to check if the member of an aggregate shape
    /// should be considered nullable. If not configured otherwise the
    /// CLIENT mode is used.
    nullabilityMode: NullabilityCheckMode = "client"
    /// If configured is the package name used for the codegen java classes.
    /// By default the namespace of the shape is used.
    packageName: String
}

