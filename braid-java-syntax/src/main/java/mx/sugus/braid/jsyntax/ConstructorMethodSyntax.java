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
 * <p>Represents a constructor method</p>
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
        this.annotations = builder.annotations.asPersistent();
        this.modifiers = builder.modifiers.asPersistent();
        this.parameters = builder.parameters.asPersistent();
    }

    public MethodKind kind() {
        return MethodKind.CONSTRUCTOR;
    }

    public Block body() {
        return this.body;
    }

    /**
     * <p>The javadoc for the type.</p>
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * <p>A list of annotations for this method</p>
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * <p>A list of modifiers for this method</p>
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * <p>A list of parameters method</p>
     */
    public List<Parameter> parameters() {
        return this.parameters;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitConstructorMethodSyntax(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ConstructorMethodSyntax that = (ConstructorMethodSyntax) obj;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
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
         * <p>Sets the value for <code>body</code></p>
         */
        public Builder body(Block body) {
            this.body.setPersistent(body);
            return this;
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
         * <p>Sets the value for <code>annotations</code></p>
         * <p>A list of annotations for this method</p>
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
         * <p>Sets the value for <code>modifiers</code></p>
         * <p>A list of modifiers for this method</p>
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
         * <p>Sets the value for <code>parameters</code></p>
         * <p>A list of parameters method</p>
         */
        public Builder parameters(List<Parameter> parameters) {
            this.parameters.clear();
            this.parameters.asTransient().addAll(parameters);
            return this;
        }

        /**
         * <p>Adds a single value for <code>parameters</code></p>
         */
        public Builder addParameter(Parameter parameter) {
            this.parameters.asTransient().add(parameter);
            return this;
        }

        /**
         * <p>Adds to <code>parameters</code> building the value using the given arguments</p>
         */
        public Builder addParameter(Class<?> kclass, String name) {
            this.parameters.asTransient().add(Parameter.builder().name(name).type(TypeName.from(kclass)).build());
            return this;
        }

        /**
         * <p>Adds to <code>parameters</code> building the value using the given arguments</p>
         */
        public Builder addParameter(TypeName type, String name) {
            this.parameters.asTransient().add(Parameter.builder().name(name).type(type).build());
            return this;
        }

        public ConstructorMethodSyntax build() {
            return new ConstructorMethodSyntax(this);
        }
    }
}
