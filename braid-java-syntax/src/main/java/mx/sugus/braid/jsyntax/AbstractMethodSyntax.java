package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents an abstract method.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class AbstractMethodSyntax implements BaseMethodSyntax {
    private final String name;
    private final List<TypeVariableTypeName> typeParams;
    private final TypeName returns;
    private final Javadoc javadoc;
    private final List<Annotation> annotations;
    private final Set<Modifier> modifiers;
    private final List<Parameter> parameters;
    private int _hashCode = 0;

    private AbstractMethodSyntax(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.typeParams = Objects.requireNonNull(builder.typeParams.asPersistent(), "typeParams");
        this.returns = Objects.requireNonNull(builder.returns, "returns");
        this.javadoc = builder.javadoc;
        this.annotations = Objects.requireNonNull(builder.annotations.asPersistent(), "annotations");
        this.modifiers = Objects.requireNonNull(builder.modifiers.asPersistent(), "modifiers");
        this.parameters = Objects.requireNonNull(builder.parameters.asPersistent(), "parameters");
    }

    public MethodKind kind() {
        return MethodKind.ABSTRACT;
    }

    /**
     * <p>The name of the method</p>
     */
    public String name() {
        return this.name;
    }

    /**
     * <p>An optional set of type params for this method</p>
     */
    public List<TypeVariableTypeName> typeParams() {
        return this.typeParams;
    }

    /**
     * <p>The retrun type for the method</p>
     */
    public TypeName returns() {
        return this.returns;
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
        return visitor.visitAbstractMethodSyntax(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractMethodSyntax that = (AbstractMethodSyntax) obj;
        return this.name.equals(that.name)
            && this.typeParams.equals(that.typeParams)
            && this.returns.equals(that.returns)
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
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + typeParams.hashCode();
            hashCode = 31 * hashCode + returns.hashCode();
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
        return "AbstractMethodSyntax{"
            + "kind: " + kind()
            + ", name: " + name
            + ", typeParams: " + typeParams
            + ", returns: " + returns
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

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder(String name) {
        return builder().name(name);
    }

    public static final class Builder {
        private String name;
        private CollectionBuilderReference<List<TypeVariableTypeName>> typeParams;
        private TypeName returns;
        private Javadoc javadoc;
        private CollectionBuilderReference<List<Annotation>> annotations;
        private CollectionBuilderReference<Set<Modifier>> modifiers;
        private CollectionBuilderReference<List<Parameter>> parameters;

        Builder() {
            this.typeParams = CollectionBuilderReference.forList();
            this.annotations = CollectionBuilderReference.forList();
            this.modifiers = CollectionBuilderReference.forOrderedSet();
            this.parameters = CollectionBuilderReference.forList();
        }

        Builder(AbstractMethodSyntax data) {
            this.name = data.name;
            this.typeParams = CollectionBuilderReference.fromPersistentList(data.typeParams);
            this.returns = data.returns;
            this.javadoc = data.javadoc;
            this.annotations = CollectionBuilderReference.fromPersistentList(data.annotations);
            this.modifiers = CollectionBuilderReference.fromPersistentOrderedSet(data.modifiers);
            this.parameters = CollectionBuilderReference.fromPersistentList(data.parameters);
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         * <p>The name of the method</p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>typeParams</code></p>
         * <p>An optional set of type params for this method</p>
         */
        public Builder typeParams(List<TypeVariableTypeName> typeParams) {
            this.typeParams.clear();
            this.typeParams.asTransient().addAll(typeParams);
            return this;
        }

        /**
         * <p>Adds a single value for <code>typeParams</code></p>
         */
        public Builder addTypeParam(TypeVariableTypeName typeParam) {
            this.typeParams.asTransient().add(typeParam);
            return this;
        }

        /**
         * <p>Adds to <code>typeParams</code> building the value using the given arguments</p>
         */
        public Builder addTypeParam(String name) {
            this.typeParams.asTransient().add(TypeVariableTypeName.from(name));
            return this;
        }

        /**
         * <p>Sets the value for <code>returns</code></p>
         * <p>The retrun type for the method</p>
         */
        public Builder returns(TypeName returns) {
            this.returns = returns;
            return this;
        }

        public Builder returns(Class<?> clazz) {
            this.returns = TypeName.from(clazz);
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

        public AbstractMethodSyntax build() {
            return new AbstractMethodSyntax(this);
        }
    }
}
