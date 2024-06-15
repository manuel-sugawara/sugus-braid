package mx.sugus.braid.jsyntax;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a unit of compilation for the Java compiler, i.e., a single Java source file.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class CompilationUnit implements SyntaxNode {
    private final String packageName;
    private final Set<ClassName> imports;
    private final TypeSyntax type;
    private final Map<String, ClassName> definedNames;
    private int _hashCode = 0;

    private CompilationUnit(Builder builder) {
        this.packageName = Objects.requireNonNull(builder.packageName, "packageName");
        this.imports = Objects.requireNonNull(builder.imports.asPersistent(), "imports");
        this.type = Objects.requireNonNull(builder.type, "type");
        this.definedNames = Objects.requireNonNull(builder.definedNames.asPersistent(), "definedNames");
    }

    public String packageName() {
        return this.packageName;
    }

    public Set<ClassName> imports() {
        return this.imports;
    }

    public TypeSyntax type() {
        return this.type;
    }

    public Map<String, ClassName> definedNames() {
        return this.definedNames;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitCompilationUnit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CompilationUnit that = (CompilationUnit) obj;
        return this.packageName.equals(that.packageName)
            && this.imports.equals(that.imports)
            && this.type.equals(that.type)
            && this.definedNames.equals(that.definedNames);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + packageName.hashCode();
            hashCode = 31 * hashCode + imports.hashCode();
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + definedNames.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "CompilationUnit{"
            + "packageName: " + packageName
            + ", imports: " + imports
            + ", type: " + type
            + ", definedNames: " + definedNames + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String packageName;
        private CollectionBuilderReference<Set<ClassName>> imports;
        private TypeSyntax type;
        private CollectionBuilderReference<Map<String, ClassName>> definedNames;

        Builder() {
            this.imports = CollectionBuilderReference.forOrderedSet();
            this.definedNames = CollectionBuilderReference.forOrderedMap();
        }

        Builder(CompilationUnit data) {
            this.packageName = data.packageName;
            this.imports = CollectionBuilderReference.fromPersistentOrderedSet(data.imports);
            this.type = data.type;
            this.definedNames = CollectionBuilderReference.fromPersistentOrderedMap(data.definedNames);
        }

        /**
         * <p>Sets the value for <code>packageName</code></p>
         */
        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        /**
         * <p>Sets the value for <code>imports</code></p>
         */
        public Builder imports(Set<ClassName> imports) {
            this.imports.clear();
            this.imports.asTransient().addAll(imports);
            return this;
        }

        /**
         * <p>Adds a single value for <code>imports</code></p>
         */
        public Builder addImport(ClassName anImport) {
            this.imports.asTransient().add(anImport);
            return this;
        }

        /**
         * <p>Creates a class name using the given java class.</p>
         */
        public Builder addImport(Class<?> kclass) {
            this.imports.asTransient().add(ClassName.from(kclass));
            return this;
        }

        /**
         * <p>Creates a class name with the given package and simple name.</p>
         */
        public Builder addImport(String packageName, String simpleName) {
            this.imports.asTransient().add(ClassName.from(packageName, simpleName));
            return this;
        }

        /**
         * <p>Creates a class name without a package.</p>
         */
        public Builder addImport(String simpleName) {
            this.imports.asTransient().add(ClassName.from(simpleName));
            return this;
        }

        /**
         * <p>Sets the value for <code>type</code></p>
         */
        public Builder type(TypeSyntax type) {
            this.type = type;
            return this;
        }

        /**
         * <p>Sets the value for <code>definedNames</code></p>
         */
        public Builder definedNames(Map<String, ClassName> definedNames) {
            this.definedNames.clear();
            this.definedNames.asTransient().putAll(definedNames);
            return this;
        }

        public Builder putDefinedName(String key, ClassName definedName) {
            this.definedNames.asTransient().put(key, definedName);
            return this;
        }

        public CompilationUnit build() {
            return new CompilationUnit(this);
        }
    }
}
