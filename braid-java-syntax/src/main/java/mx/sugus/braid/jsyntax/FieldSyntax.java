package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a class field.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class FieldSyntax implements SyntaxNode {
    private final Javadoc javadoc;
    private final String name;
    private final TypeName type;
    private final Set<Modifier> modifiers;
    private final List<Annotation> annotations;
    private final Expression initializer;
    private int _hashCode = 0;

    private FieldSyntax(Builder builder) {
        this.javadoc = builder.javadoc;
        this.name = Objects.requireNonNull(builder.name, "name");
        this.type = Objects.requireNonNull(builder.type, "type");
        this.modifiers = Objects.requireNonNull(builder.modifiers.asPersistent(), "modifiers");
        this.annotations = Objects.requireNonNull(builder.annotations.asPersistent(), "annotations");
        this.initializer = builder.initializer;
    }

    /**
     * The javadoc for the type.
     * 
     * @return The value of the {@code javadoc} member
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * The name of the field.
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * The type of the field.
     * 
     * @return The value of the {@code type} member
     */
    public TypeName type() {
        return this.type;
    }

    /**
     * A list of modifiers for the field.
     * 
     * @return The value of the {@code modifiers} member
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * A list of annotations for the field.
     * 
     * @return The value of the {@code annotations} member
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * A initialization expression
     * 
     * @return The value of the {@code initializer} member
     */
    public Expression initializer() {
        return this.initializer;
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
        return visitor.visitFieldSyntax(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FieldSyntax that = (FieldSyntax) obj;
        return Objects.equals(this.javadoc, that.javadoc)
            && this.name.equals(that.name)
            && this.type.equals(that.type)
            && this.modifiers.equals(that.modifiers)
            && this.annotations.equals(that.annotations)
            && Objects.equals(this.initializer, that.initializer);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (javadoc != null ? javadoc.hashCode() : 0);
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + modifiers.hashCode();
            hashCode = 31 * hashCode + annotations.hashCode();
            hashCode = 31 * hashCode + (initializer != null ? initializer.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "FieldSyntax{"
            + "javadoc: " + javadoc
            + ", name: " + name
            + ", type: " + type
            + ", modifiers: " + modifiers
            + ", annotations: " + annotations
            + ", initializer: " + initializer + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static FieldSyntax from(TypeName type, String name) {
        return FieldSyntax.builder()
                       .addModifiers(javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.FINAL)
                       .name(name)
                       .type(type)
                       .build();
    }

    public static FieldSyntax from(Class<?> kclass, String name) {
        return FieldSyntax.builder()
                       .addModifiers(javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.FINAL)
                       .name(name)
                       .type(ClassName.from(kclass))
                       .build();
    }

    public static FieldSyntax mutableFrom(TypeName type, String name) {
        return FieldSyntax.builder()
                       .addModifier(javax.lang.model.element.Modifier.PRIVATE)
                       .name(name)
                       .type(type)
                       .build();
    }

    public static FieldSyntax mutableFrom(Class<?> kclass, String name) {
        return FieldSyntax.builder()
                       .addModifier(javax.lang.model.element.Modifier.PRIVATE)
                       .name(name)
                       .type(ClassName.from(kclass))
                       .build();
    }

    /**
     * A class to build instances of FieldSyntax
     */
    public static final class Builder implements SyntaxNode.Builder {
        private Javadoc javadoc;
        private String name;
        private TypeName type;
        private CollectionBuilderReference<Set<Modifier>> modifiers;
        private CollectionBuilderReference<List<Annotation>> annotations;
        private Expression initializer;

        Builder() {
            this.modifiers = CollectionBuilderReference.forOrderedSet();
            this.annotations = CollectionBuilderReference.forList();
        }

        Builder(FieldSyntax data) {
            this.javadoc = data.javadoc;
            this.name = data.name;
            this.type = data.type;
            this.modifiers = CollectionBuilderReference.fromPersistentOrderedSet(data.modifiers);
            this.annotations = CollectionBuilderReference.fromPersistentList(data.annotations);
            this.initializer = data.initializer;
        }

        /**
         * Sets the value for {@code javadoc}.
         * 
         * @param javadoc The value to be set.
         * @return This instance for chain calling.
         */
        public Builder javadoc(Javadoc javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        /**
         * Sets the value for {@code name}.
         * 
         * @param name The value to be set.
         * @return This instance for chain calling.
         */
        public Builder name(String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        /**
         * Sets the value for {@code type}.
         * 
         * @param type The value to be set.
         * @return This instance for chain calling.
         */
        public Builder type(TypeName type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        public Builder type(Class<?> clazz) {
            this.type = TypeName.from(clazz);
            return this;
        }

        /**
         * Sets the value for {@code modifiers}.
         * 
         * @param modifiers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder modifiers(Set<Modifier> modifiers) {
            this.modifiers.clear();
            this.modifiers.asTransient().addAll(modifiers);
            return this;
        }

        /**
         * Adds a value to {@code modifiers}.
         * 
         * @param modifiers The value tp add
         * @return This instance for chain calling.
         */
        public Builder addModifier(Modifier modifier) {
            this.modifiers.asTransient().add(modifier);
            return this;
        }

        /**
         * Adds the given values to {@code modifiers}
         */
        public Builder addModifiers(Modifier modifier1, Modifier modifier2) {
            this.modifiers.asTransient().add(modifier1);
            this.modifiers.asTransient().add(modifier2);
            return this;
        }

        /**
         * Adds the given values to {@code modifiers}
         */
        public Builder addModifiers(Modifier modifier1, Modifier modifier2, Modifier modifier3) {
            this.modifiers.asTransient().add(modifier1);
            this.modifiers.asTransient().add(modifier2);
            this.modifiers.asTransient().add(modifier3);
            return this;
        }

        /**
         * Sets the value for {@code annotations}.
         * 
         * @param annotations The value to be set.
         * @return This instance for chain calling.
         */
        public Builder annotations(List<Annotation> annotations) {
            this.annotations.clear();
            this.annotations.asTransient().addAll(annotations);
            return this;
        }

        /**
         * Adds a value to {@code annotations}.
         * 
         * @param annotations The value tp add
         * @return This instance for chain calling.
         */
        public Builder addAnnotation(Annotation annotation) {
            this.annotations.asTransient().add(annotation);
            return this;
        }

        /**
         * Adds to {@code annotations} building the value using the given arguments
         */
        public Builder addAnnotation(ClassName type) {
            this.annotations.asTransient().add(Annotation.builder(type).build());
            return this;
        }

        /**
         * Adds to {@code annotations} building the value using the given arguments
         */
        public Builder addAnnotation(Class<?> kclass) {
            this.annotations.asTransient().add(Annotation.builder(kclass).build());
            return this;
        }

        /**
         * Sets the value for {@code initializer}.
         * 
         * @param initializer The value to be set.
         * @return This instance for chain calling.
         */
        public Builder initializer(Expression initializer) {
            this.initializer = initializer;
            return this;
        }

        /**
         * Returns a new instance of {@link FieldSyntax}
         * 
         * @return A new instance of {@link FieldSyntax}
         */
        public FieldSyntax build() {
            return new FieldSyntax(this);
        }
    }
}
