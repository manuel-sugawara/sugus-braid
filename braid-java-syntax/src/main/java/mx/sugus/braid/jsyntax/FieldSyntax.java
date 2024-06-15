package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a class field.</p>
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
     * <p>The javadoc for the type.</p>
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * <p>The name of the field.</p>
     */
    public String name() {
        return this.name;
    }

    /**
     * <p>The type of the field.</p>
     */
    public TypeName type() {
        return this.type;
    }

    /**
     * <p>A list of modifiers for the field.</p>
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * <p>A list of annotations for the field.</p>
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * <p>A initialization expression</p>
     */
    public Expression initializer() {
        return this.initializer;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
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

    public static final class Builder {
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
         * <p>Sets the value for <code>javadoc</code></p>
         * <p>The javadoc for the type.</p>
         */
        public Builder javadoc(Javadoc javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        public Builder javadoc(String format, Object... args) {
            this.javadoc = CodeBlock.from(format, args);
            return this;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         * <p>The name of the field.</p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>type</code></p>
         * <p>The type of the field.</p>
         */
        public Builder type(TypeName type) {
            this.type = type;
            return this;
        }

        public Builder type(Class<?> clazz) {
            this.type = TypeName.from(clazz);
            return this;
        }

        /**
         * <p>Sets the value for <code>modifiers</code></p>
         * <p>A list of modifiers for the field.</p>
         */
        public Builder modifiers(Set<Modifier> modifiers) {
            this.modifiers.clear();
            this.modifiers.asTransient().addAll(modifiers);
            return this;
        }

        /**
         * <p>Adds a single value for <code>modifiers</code></p>
         */
        public Builder addModifier(Modifier modifier) {
            this.modifiers.asTransient().add(modifier);
            return this;
        }

        /**
         * <p>Adds the given values to <code>modifiers</code></p>
         */
        public Builder addModifiers(Modifier modifier1, Modifier modifier2) {
            this.modifiers.asTransient().add(modifier1);
            this.modifiers.asTransient().add(modifier2);
            return this;
        }

        /**
         * <p>Adds the given values to <code>modifiers</code></p>
         */
        public Builder addModifiers(Modifier modifier1, Modifier modifier2, Modifier modifier3) {
            this.modifiers.asTransient().add(modifier1);
            this.modifiers.asTransient().add(modifier2);
            this.modifiers.asTransient().add(modifier3);
            return this;
        }

        /**
         * <p>Sets the value for <code>annotations</code></p>
         * <p>A list of annotations for the field.</p>
         */
        public Builder annotations(List<Annotation> annotations) {
            this.annotations.clear();
            this.annotations.asTransient().addAll(annotations);
            return this;
        }

        /**
         * <p>Adds a single value for <code>annotations</code></p>
         */
        public Builder addAnnotation(Annotation annotation) {
            this.annotations.asTransient().add(annotation);
            return this;
        }

        /**
         * <p>Adds to <code>annotations</code> building the value using the given arguments</p>
         */
        public Builder addAnnotation(ClassName type) {
            this.annotations.asTransient().add(Annotation.builder(type).build());
            return this;
        }

        /**
         * <p>Adds to <code>annotations</code> building the value using the given arguments</p>
         */
        public Builder addAnnotation(Class<?> kclass) {
            this.annotations.asTransient().add(Annotation.builder(kclass).build());
            return this;
        }

        /**
         * <p>Sets the value for <code>initializer</code></p>
         * <p>A initialization expression</p>
         */
        public Builder initializer(Expression initializer) {
            this.initializer = initializer;
            return this;
        }

        public FieldSyntax build() {
            return new FieldSyntax(this);
        }
    }
}
