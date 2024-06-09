package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a Java class.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ClassSyntax implements TypeSyntax {
    private final TypeName superClass;
    private final List<TypeVariableTypeName> typeParams;
    private final Javadoc javadoc;
    private final List<BaseMethodSyntax> methods;
    private final String name;
    private final List<Annotation> annotations;
    private final Set<Modifier> modifiers;
    private final List<FieldSyntax> fields;
    private final List<TypeName> superInterfaces;
    private final List<TypeSyntax> innerTypes;
    private int _hashCode = 0;

    private ClassSyntax(Builder builder) {
        this.superClass = builder.superClass;
        this.typeParams = builder.typeParams.asPersistent();
        this.javadoc = builder.javadoc;
        this.methods = builder.methods.asPersistent();
        this.name = Objects.requireNonNull(builder.name, "name");
        this.annotations = builder.annotations.asPersistent();
        this.modifiers = builder.modifiers.asPersistent();
        this.fields = builder.fields.asPersistent();
        this.superInterfaces = builder.superInterfaces.asPersistent();
        this.innerTypes = builder.innerTypes.asPersistent();
    }

    public TypeSyntaxKind kind() {
        return TypeSyntaxKind.CLASS;
    }

    /**
     * <p>The super class of this class.</p>
     */
    public TypeName superClass() {
        return this.superClass;
    }

    /**
     * <p>A of types parameters for this type.</p>
     */
    public List<TypeVariableTypeName> typeParams() {
        return this.typeParams;
    }

    /**
     * <p>The javadoc for the type.</p>
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * <p>A list of methods for this type.</p>
     */
    public List<BaseMethodSyntax> methods() {
        return this.methods;
    }

    /**
     * <p>The simple name for the type.</p>
     */
    public String name() {
        return this.name;
    }

    /**
     * <p>A list of annotations for this type.</p>
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * <p>A list of modifiers for this type.</p>
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * <p>A list of fields for this type.</p>
     */
    public List<FieldSyntax> fields() {
        return this.fields;
    }

    /**
     * <p>A list of super interfaces for this type.</p>
     */
    public List<TypeName> superInterfaces() {
        return this.superInterfaces;
    }

    /**
     * <p>A list of inner types enclosed by this type.</p>
     */
    public List<TypeSyntax> innerTypes() {
        return this.innerTypes;
    }

    /**
     * Returns a new builder to modify a copy of this instance
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitClassSyntax(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassSyntax that = (ClassSyntax) obj;
        return Objects.equals(this.superClass, that.superClass)
            && this.typeParams.equals(that.typeParams)
            && Objects.equals(this.javadoc, that.javadoc)
            && this.methods.equals(that.methods)
            && this.name.equals(that.name)
            && this.annotations.equals(that.annotations)
            && this.modifiers.equals(that.modifiers)
            && this.fields.equals(that.fields)
            && this.superInterfaces.equals(that.superInterfaces)
            && this.innerTypes.equals(that.innerTypes);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.kind().hashCode();
            hashCode = 31 * hashCode + (superClass != null ? superClass.hashCode() : 0);
            hashCode = 31 * hashCode + typeParams.hashCode();
            hashCode = 31 * hashCode + (javadoc != null ? javadoc.hashCode() : 0);
            hashCode = 31 * hashCode + methods.hashCode();
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + annotations.hashCode();
            hashCode = 31 * hashCode + modifiers.hashCode();
            hashCode = 31 * hashCode + fields.hashCode();
            hashCode = 31 * hashCode + superInterfaces.hashCode();
            hashCode = 31 * hashCode + innerTypes.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "ClassSyntax{"
            + "kind: " + kind()
            + ", superClass: " + superClass
            + ", typeParams: " + typeParams
            + ", javadoc: " + javadoc
            + ", methods: " + methods
            + ", name: " + name
            + ", annotations: " + annotations
            + ", modifiers: " + modifiers
            + ", fields: " + fields
            + ", superInterfaces: " + superInterfaces
            + ", innerTypes: " + innerTypes + "}";
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
        private TypeName superClass;
        private CollectionBuilderReference<List<TypeVariableTypeName>> typeParams;
        private Javadoc javadoc;
        private CollectionBuilderReference<List<BaseMethodSyntax>> methods;
        private String name;
        private CollectionBuilderReference<List<Annotation>> annotations;
        private CollectionBuilderReference<Set<Modifier>> modifiers;
        private CollectionBuilderReference<List<FieldSyntax>> fields;
        private CollectionBuilderReference<List<TypeName>> superInterfaces;
        private CollectionBuilderReference<List<TypeSyntax>> innerTypes;

        Builder() {
            this.typeParams = CollectionBuilderReference.forList();
            this.methods = CollectionBuilderReference.forList();
            this.annotations = CollectionBuilderReference.forList();
            this.modifiers = CollectionBuilderReference.forOrderedSet();
            this.fields = CollectionBuilderReference.forList();
            this.superInterfaces = CollectionBuilderReference.forList();
            this.innerTypes = CollectionBuilderReference.forList();
        }

        Builder(ClassSyntax data) {
            this.superClass = data.superClass;
            this.typeParams = CollectionBuilderReference.fromPersistentList(data.typeParams);
            this.javadoc = data.javadoc;
            this.methods = CollectionBuilderReference.fromPersistentList(data.methods);
            this.name = data.name;
            this.annotations = CollectionBuilderReference.fromPersistentList(data.annotations);
            this.modifiers = CollectionBuilderReference.fromPersistentOrderedSet(data.modifiers);
            this.fields = CollectionBuilderReference.fromPersistentList(data.fields);
            this.superInterfaces = CollectionBuilderReference.fromPersistentList(data.superInterfaces);
            this.innerTypes = CollectionBuilderReference.fromPersistentList(data.innerTypes);
        }

        /**
         * <p>Sets the value for <code>superClass</code></p>
         * <p>The super class of this class.</p>
         */
        public Builder superClass(TypeName superClass) {
            this.superClass = superClass;
            return this;
        }

        public Builder superClass(Class<?> clazz) {
            this.superClass = TypeName.from(clazz);
            return this;
        }

        /**
         * <p>Sets the value for <code>typeParams</code></p>
         * <p>A of types parameters for this type.</p>
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
         * <p>Sets the value for <code>methods</code></p>
         * <p>A list of methods for this type.</p>
         */
        public Builder methods(List<BaseMethodSyntax> methods) {
            this.methods.clear();
            this.methods.asTransient().addAll(methods);
            return this;
        }

        /**
         * <p>Adds a single value for <code>methods</code></p>
         */
        public Builder addMethod(BaseMethodSyntax method) {
            this.methods.asTransient().add(method);
            return this;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         * <p>The simple name for the type.</p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>annotations</code></p>
         * <p>A list of annotations for this type.</p>
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
         * <p>A list of modifiers for this type.</p>
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
         * <p>Sets the value for <code>fields</code></p>
         * <p>A list of fields for this type.</p>
         */
        public Builder fields(List<FieldSyntax> fields) {
            this.fields.clear();
            this.fields.asTransient().addAll(fields);
            return this;
        }

        /**
         * <p>Adds a single value for <code>fields</code></p>
         */
        public Builder addField(FieldSyntax field) {
            this.fields.asTransient().add(field);
            return this;
        }

        /**
         * <p>Adds to <code>fields</code> building the value using the given arguments</p>
         */
        public Builder addField(TypeName type, String name) {
            this.fields.asTransient().add(FieldSyntax.from(type, name));
            return this;
        }

        /**
         * <p>Adds to <code>fields</code> building the value using the given arguments</p>
         */
        public Builder addField(Class<?> kclass, String name) {
            this.fields.asTransient().add(FieldSyntax.from(kclass, name));
            return this;
        }

        /**
         * <p>Sets the value for <code>superInterfaces</code></p>
         * <p>A list of super interfaces for this type.</p>
         */
        public Builder superInterfaces(List<TypeName> superInterfaces) {
            this.superInterfaces.clear();
            this.superInterfaces.asTransient().addAll(superInterfaces);
            return this;
        }

        /**
         * <p>Adds a single value for <code>superInterfaces</code></p>
         */
        public Builder addSuperInterface(TypeName superInterface) {
            this.superInterfaces.asTransient().add(superInterface);
            return this;
        }

        /**
         * <p>Creates a new TypeName instance out of the given class.</p>
         */
        public Builder addSuperInterface(Class<?> kclass) {
            this.superInterfaces.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * <p>Sets the value for <code>innerTypes</code></p>
         * <p>A list of inner types enclosed by this type.</p>
         */
        public Builder innerTypes(List<TypeSyntax> innerTypes) {
            this.innerTypes.clear();
            this.innerTypes.asTransient().addAll(innerTypes);
            return this;
        }

        /**
         * <p>Adds a single value for <code>innerTypes</code></p>
         */
        public Builder addInnerType(TypeSyntax innerType) {
            this.innerTypes.asTransient().add(innerType);
            return this;
        }

        public ClassSyntax build() {
            return new ClassSyntax(this);
        }
    }
}
