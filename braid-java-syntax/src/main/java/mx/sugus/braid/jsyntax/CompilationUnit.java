package mx.sugus.braid.jsyntax;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a unit of compilation for the Java compiler, i.e., a single Java source file.
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

    /**
     * 
     * @return The value of the {@code packageName} member
     */
    public String packageName() {
        return this.packageName;
    }

    /**
     * 
     * @return The value of the {@code imports} member
     */
    public Set<ClassName> imports() {
        return this.imports;
    }

    /**
     * 
     * @return The value of the {@code type} member
     */
    public TypeSyntax type() {
        return this.type;
    }

    /**
     * 
     * @return The value of the {@code definedNames} member
     */
    public Map<String, ClassName> definedNames() {
        return this.definedNames;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Accepts a {@link SyntaxNodeVisitor<VisitorR>} visitor
     * 
     * @param visitor The visitor to accept
     * @param <VisitorR> The result type from the visitor
     * @return The result from the visitor
     */
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of CompilationUnit
     */
    public static final class Builder implements SyntaxNode.Builder {
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
         * Sets the value for {@code packageName}.
         * 
         * @param packageName The value to be set.
         * @return This instance for chain calling.
         */
        public Builder packageName(String packageName) {
            this.packageName = Objects.requireNonNull(packageName, "packageName");
            return this;
        }

        /**
         * Sets the value for {@code imports}.
         * 
         * @param imports The value to be set.
         * @return This instance for chain calling.
         */
        public Builder imports(Set<ClassName> imports) {
            this.imports.clear();
            this.imports.asTransient().addAll(imports);
            return this;
        }

        /**
         * Adds a value to {@code imports}.
         * 
         * @param imports The value tp add
         * @return This instance for chain calling.
         */
        public Builder addImport(ClassName anImport) {
            this.imports.asTransient().add(anImport);
            return this;
        }

        /**
         * Creates a class name using the given java class.
         */
        public Builder addImport(Class<?> kclass) {
            this.imports.asTransient().add(ClassName.from(kclass));
            return this;
        }

        /**
         * Creates a class name with the given package and simple name.
         */
        public Builder addImport(String packageName, String simpleName) {
            this.imports.asTransient().add(ClassName.from(packageName, simpleName));
            return this;
        }

        /**
         * Creates a class name without a package.
         */
        public Builder addImport(String simpleName) {
            this.imports.asTransient().add(ClassName.from(simpleName));
            return this;
        }

        /**
         * Sets the value for {@code type}.
         * 
         * @param type The value to be set.
         * @return This instance for chain calling.
         */
        public Builder type(TypeSyntax type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        /**
         * Sets the value for {@code definedNames}.
         * 
         * @param definedNames The value to be set.
         * @return This instance for chain calling.
         */
        public Builder definedNames(Map<String, ClassName> definedNames) {
            this.definedNames.clear();
            this.definedNames.asTransient().putAll(definedNames);
            return this;
        }

        /**
         * Puts a new entry to the {@code definedNames} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param definedName The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putDefinedName(String key, ClassName definedName) {
            this.definedNames.asTransient().put(key, definedName);
            return this;
        }

        /**
         * Returns a new instance of {@link CompilationUnit}
         * 
         * @return A new instance of {@link CompilationUnit}
         */
        public CompilationUnit build() {
            return new CompilationUnit(this);
        }
    }
}
