$version: "2.0"

namespace mx.sugus.braid.plugins.syntax.config

list SyntaxNodes {
    member: String
}

/// Config settings for the SyntaxModelPlugin
structure SyntaxModelPluginConfig {
    /// The shape ids of the shapes to be consider roots
    /// for the syntax nodes.
    syntaxNodes: SyntaxNodes
}

