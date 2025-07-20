package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a constructor method
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ConstructorMethodSyntax implements BaseMethodSyntax {
    private final Block body;
    private final Javadoc javadoc;
    private final List<Annotation> annotations;
    private final Set<Modifier> modifiers;
    private final List<Parameter> parameters;
    private int _hashCode = 0;

    private ConstructorMethodSyntax(Builder builder) {
        this.body = Objects.requireNonNull(builder.body.asPersistent(), "body");
        this.javadoc = builder.javadoc;
        this.annotations = Objects.requireNonNull(builder.annotations.asPersistent(), "annotations");
        this.modifiers = Objects.requireNonNull(builder.modifiers.asPersistent(), "modifiers");
        this.parameters = Objects.requireNonNull(builder.parameters.asPersistent(), "parameters");
    }

    public MethodKind kind() {
        return MethodKind.CONSTRUCTOR;
    }

    /**
     * 
     * @return The value of the {@code body} member
     */
    public Block body() {
        return this.body;
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
     * A list of annotations for this method
     * 
     * @return The value of the {@code annotations} member
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * A list of modifiers for this method
     * 
     * @return The value of the {@code modifiers} member
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * A list of parameters method
     * 
     * @return The value of the {@code parameters} member
     */
    public List<Parameter> parameters() {
        return this.parameters;
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
        return visitor.visitConstructorMethodSyntax(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ConstructorMethodSyntax that = (ConstructorMethodSyntax) other;
        return this.body.equals(that.body)
            && Objects.equals(this.javadoc, that.javadoc)
            && this.annotations.equals(that.annotations)
            && this.modifiers.equals(that.modifiers)
            && this.parameters.equals(that.parameters);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.kind().hashCode();
            hashCode = 31 * hashCode + body.hashCode();
            hashCode = 31 * hashCode + (javadoc != null ? javadoc.hashCode() : 0);
            hashCode = 31 * hashCode + annotations.hashCode();
            hashCode = 31 * hashCode + modifiers.hashCode();
            hashCode = 31 * hashCode + parameters.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "ConstructorMethodSyntax{"
            + "kind: " + kind()
            + ", body: " + body
            + ", javadoc: " + javadoc
            + ", annotations: " + annotations
            + ", modifiers: " + modifiers
            + ", parameters: " + parameters + "}";
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
     * A class to build instances of ConstructorMethodSyntax
     */
    public static final class Builder implements BaseMethodSyntax.Builder {
        private BuilderReference<Block, BodyBuilder> body;
        private Javadoc javadoc;
        private CollectionBuilderReference<List<Annotation>> annotations;
        private CollectionBuilderReference<Set<Modifier>> modifiers;
        private CollectionBuilderReference<List<Parameter>> parameters;

        Builder() {
            this.body = BodyBuilder.fromPersistent(null);
            this.annotations = CollectionBuilderReference.forList();
            this.modifiers = CollectionBuilderReference.forOrderedSet();
            this.parameters = CollectionBuilderReference.forList();
        }

        Builder(ConstructorMethodSyntax data) {
            this.body = BodyBuilder.fromPersistent(data.body);
            this.javadoc = data.javadoc;
            this.annotations = CollectionBuilderReference.fromPersistentList(data.annotations);
            this.modifiers = CollectionBuilderReference.fromPersistentOrderedSet(data.modifiers);
            this.parameters = CollectionBuilderReference.fromPersistentList(data.parameters);
        }

        public Builder body(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.body.asTransient());
            return this;
        }

        /**
         * Sets the value for {@code body}.
         * 
         * @param body The value to be set.
         * @return This instance for chain calling.
         */
        public Builder body(Block body) {
            this.body.setPersistent(body);
            return this;
        }

        public Builder addStatement(String format, Object... args) {
            this.body.asTransient().addStatement(format, args);
            return this;
        }

        public Builder addStatement(Statement stmt) {
            this.body.asTransient().addStatement(stmt);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.body.asTransient().ifStatement(format, then);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.body.asTransient().ifStatement(format, arg, then);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.body.asTransient().ifStatement(format, then, otherwise);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.body.asTransient().ifStatement(format, arg, then, otherwise);
            return this;
        }

        public Builder beginControlFlow(String format, Object... args) {
            this.body.asTransient().beginControlFlow(format, args);
            return this;
        }

        public Builder nextControlFlow(String format, Object... args) {
            this.body.asTransient().nextControlFlow(format, args);
            return this;
        }

        public Builder endControlFlow() {
            this.body.asTransient().endControlFlow();
            return this;
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
         * Sets the value for {@code parameters}.
         * 
         * @param parameters The value to be set.
         * @return This instance for chain calling.
         */
        public Builder parameters(List<Parameter> parameters) {
            this.parameters.clear();
            this.parameters.asTransient().addAll(parameters);
            return this;
        }

        /**
         * Adds a value to {@code parameters}.
         * 
         * @param parameters The value tp add
         * @return This instance for chain calling.
         */
        public Builder addParameter(Parameter parameter) {
            this.parameters.asTransient().add(parameter);
            return this;
        }

        /**
         * Adds to {@code parameters} building the value using the given arguments
         */
        public Builder addParameter(Class<?> kclass, String name) {
            this.parameters.asTransient().add(Parameter.builder().name(name).type(TypeName.from(kclass)).build());
            return this;
        }

        /**
         * Adds to {@code parameters} building the value using the given arguments
         */
        public Builder addParameter(TypeName type, String name) {
            this.parameters.asTransient().add(Parameter.builder().name(name).type(type).build());
            return this;
        }

        /**
         * Returns a new instance of {@link ConstructorMethodSyntax}
         * 
         * @return A new instance of {@link ConstructorMethodSyntax}
         */
        public ConstructorMethodSyntax build() {
            return new ConstructorMethodSyntax(this);
        }
    }
}
