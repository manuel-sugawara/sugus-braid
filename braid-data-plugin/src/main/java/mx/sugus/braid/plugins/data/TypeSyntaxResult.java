package mx.sugus.braid.plugins.data;

import mx.sugus.braid.jsyntax.CompilationUnit;

public final class TypeSyntaxResult {
    private final CompilationUnit syntax;
    private final String namespace;

    TypeSyntaxResult(TypeSyntaxResult.Builder builder) {
        this.syntax = builder.syntax;
        this.namespace = builder.namespace;
    }

    public CompilationUnit syntax() {
        return syntax;
    }

    public String namespace() {
        return namespace;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CompilationUnit syntax;
        private String namespace;

        Builder() {
        }

        Builder(TypeSyntaxResult result) {
            this.syntax = result.syntax;
            this.namespace = result.namespace;
        }

        public Builder syntax(CompilationUnit syntax) {
            this.syntax = syntax;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public TypeSyntaxResult build() {
            return new TypeSyntaxResult(this);
        }
    }
}
