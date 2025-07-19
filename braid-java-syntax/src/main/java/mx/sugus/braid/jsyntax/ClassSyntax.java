package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a Java class.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ClassSyntax implements TypeSyntax {
    private final TypeName superClass;
    private final List<TypeVariableTypeName> typeParams;
    private final Javadoc javadoc;
    private final String name;
    private final Set<Modifier> modifiers;
    private final List<Annotation> annotations;
    private final List<TypeName> superInterfaces;
    private final List<FieldSyntax> fields;
    private final List<BaseMethodSyntax> methods;
    private final List<TypeSyntax> innerTypes;
    private int _hashCode = 0;

    private ClassSyntax(Builder builder) {
        this.superClass = builder.superClass;
        this.typeParams = Objects.requireNonNull(builder.typeParams.asPersistent(), "typeParams");
        this.javadoc = builder.javadoc;
        this.name = Objects.requireNonNull(builder.name, "name");
        this.modifiers = Objects.requireNonNull(builder.modifiers.asPersistent(), "modifiers");
        this.annotations = Objects.requireNonNull(builder.annotations.asPersistent(), "annotations");
        this.superInterfaces = Objects.requireNonNull(builder.superInterfaces.asPersistent(), "superInterfaces");
        this.fields = Objects.requireNonNull(builder.fields.asPersistent(), "fields");
        this.methods = Objects.requireNonNull(builder.methods.asPersistent(), "methods");
        this.innerTypes = Objects.requireNonNull(builder.innerTypes.asPersistent(), "innerTypes");
    }

    public TypeSyntaxKind kind() {
        return TypeSyntaxKind.CLASS;
    }

    /**
     * The super class of this class.
     * 
     * @return The value of the {@code superClass} member
     */
    public TypeName superClass() {
        return this.superClass;
    }

    /**
     * A of types parameters for this type.
     * 
     * @return The value of the {@code typeParams} member
     */
    public List<TypeVariableTypeName> typeParams() {
        return this.typeParams;
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
     * The simple name for the type.
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * A list of modifiers for this type.
     * 
     * @return The value of the {@code modifiers} member
     */
    public Set<Modifier> modifiers() {
        return this.modifiers;
    }

    /**
     * A list of annotations for this type.
     * 
     * @return The value of the {@code annotations} member
     */
    public List<Annotation> annotations() {
        return this.annotations;
    }

    /**
     * A list of super interfaces for this type.
     * 
     * @return The value of the {@code superInterfaces} member
     */
    public List<TypeName> superInterfaces() {
        return this.superInterfaces;
    }

    /**
     * A list of fields for this type.
     * 
     * @return The value of the {@code fields} member
     */
    public List<FieldSyntax> fields() {
        return this.fields;
    }

    /**
     * A list of methods for this type.
     * 
     * @return The value of the {@code methods} member
     */
    public List<BaseMethodSyntax> methods() {
        return this.methods;
    }

    /**
     * A list of inner types enclosed by this type.
     * 
     * @return The value of the {@code innerTypes} member
     */
    public List<TypeSyntax> innerTypes() {
        return this.innerTypes;
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
            && this.name.equals(that.name)
            && this.modifiers.equals(that.modifiers)
            && this.annotations.equals(that.annotations)
            && this.superInterfaces.equals(that.superInterfaces)
            && this.fields.equals(that.fields)
            && this.methods.equals(that.methods)
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
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + modifiers.hashCode();
            hashCode = 31 * hashCode + annotations.hashCode();
            hashCode = 31 * hashCode + superInterfaces.hashCode();
            hashCode = 31 * hashCode + fields.hashCode();
            hashCode = 31 * hashCode + methods.hashCode();
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
            + ", name: " + name
            + ", modifiers: " + modifiers
            + ", annotations: " + annotations
            + ", superInterfaces: " + superInterfaces
            + ", fields: " + fields
            + ", methods: " + methods
            + ", innerTypes: " + innerTypes + "}";
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
     * Creates a new builder
     */
    public static Builder builder(String name) {
        return builder().name(name);
    }

    /**
     * A class to build instances of ClassSyntax
     */
    public static final class Builder implements TypeSyntax.Builder {
        private TypeName superClass;
        private CollectionBuilderReference<List<TypeVariableTypeName>> typeParams;
        private Javadoc javadoc;
        private String name;
        private CollectionBuilderReference<Set<Modifier>> modifiers;
        private CollectionBuilderReference<List<Annotation>> annotations;
        private CollectionBuilderReference<List<TypeName>> superInterfaces;
        private CollectionBuilderReference<List<FieldSyntax>> fields;
        private CollectionBuilderReference<List<BaseMethodSyntax>> methods;
        private CollectionBuilderReference<List<TypeSyntax>> innerTypes;

        Builder() {
            this.typeParams = CollectionBuilderReference.forList();
            this.modifiers = CollectionBuilderReference.forOrderedSet();
            this.annotations = CollectionBuilderReference.forList();
            this.superInterfaces = CollectionBuilderReference.forList();
            this.fields = CollectionBuilderReference.forList();
            this.methods = CollectionBuilderReference.forList();
            this.innerTypes = CollectionBuilderReference.forList();
        }

        Builder(ClassSyntax data) {
            this.superClass = data.superClass;
            this.typeParams = CollectionBuilderReference.fromPersistentList(data.typeParams);
            this.javadoc = data.javadoc;
            this.name = data.name;
            this.modifiers = CollectionBuilderReference.fromPersistentOrderedSet(data.modifiers);
            this.annotations = CollectionBuilderReference.fromPersistentList(data.annotations);
            this.superInterfaces = CollectionBuilderReference.fromPersistentList(data.superInterfaces);
            this.fields = CollectionBuilderReference.fromPersistentList(data.fields);
            this.methods = CollectionBuilderReference.fromPersistentList(data.methods);
            this.innerTypes = CollectionBuilderReference.fromPersistentList(data.innerTypes);
        }

        /**
         * Sets the value for {@code superClass}.
         * 
         * @param superClass The value to be set.
         * @return This instance for chain calling.
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
         * Sets the value for {@code typeParams}.
         * 
         * @param typeParams The value to be set.
         * @return This instance for chain calling.
         */
        public Builder typeParams(List<TypeVariableTypeName> typeParams) {
            this.typeParams.clear();
            this.typeParams.asTransient().addAll(typeParams);
            return this;
        }

        /**
         * Adds a value to {@code typeParams}.
         * 
         * @param typeParams The value tp add
         * @return This instance for chain calling.
         */
        public Builder addTypeParam(TypeVariableTypeName typeParam) {
            this.typeParams.asTransient().add(typeParam);
            return this;
        }

        /**
         * Adds to {@code typeParams} building the value using the given arguments
         */
        public Builder addTypeParam(String name) {
            this.typeParams.asTransient().add(TypeVariableTypeName.from(name));
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
         * Sets the value for {@code superInterfaces}.
         * 
         * @param superInterfaces The value to be set.
         * @return This instance for chain calling.
         */
        public Builder superInterfaces(List<TypeName> superInterfaces) {
            this.superInterfaces.clear();
            this.superInterfaces.asTransient().addAll(superInterfaces);
            return this;
        }

        /**
         * Adds a value to {@code superInterfaces}.
         * 
         * @param superInterfaces The value tp add
         * @return This instance for chain calling.
         */
        public Builder addSuperInterface(TypeName superInterface) {
            this.superInterfaces.asTransient().add(superInterface);
            return this;
        }

        /**
         * Creates a new TypeName instance out of the given class.
         */
        public Builder addSuperInterface(Class<?> kclass) {
            this.superInterfaces.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * Sets the value for {@code fields}.
         * 
         * @param fields The value to be set.
         * @return This instance for chain calling.
         */
        public Builder fields(List<FieldSyntax> fields) {
            this.fields.clear();
            this.fields.asTransient().addAll(fields);
            return this;
        }

        /**
         * Adds a value to {@code fields}.
         * 
         * @param fields The value tp add
         * @return This instance for chain calling.
         */
        public Builder addField(FieldSyntax field) {
            this.fields.asTransient().add(field);
            return this;
        }

        /**
         * Adds to {@code fields} building the value using the given arguments
         */
        public Builder addField(TypeName type, String name) {
            this.fields.asTransient().add(FieldSyntax.from(type, name));
            return this;
        }

        /**
         * Adds to {@code fields} building the value using the given arguments
         */
        public Builder addField(Class<?> kclass, String name) {
            this.fields.asTransient().add(FieldSyntax.from(kclass, name));
            return this;
        }

        /**
         * Sets the value for {@code methods}.
         * 
         * @param methods The value to be set.
         * @return This instance for chain calling.
         */
        public Builder methods(List<BaseMethodSyntax> methods) {
            this.methods.clear();
            this.methods.asTransient().addAll(methods);
            return this;
        }

        /**
         * Adds a value to {@code methods}.
         * 
         * @param methods The value tp add
         * @return This instance for chain calling.
         */
        public Builder addMethod(BaseMethodSyntax method) {
            this.methods.asTransient().add(method);
            return this;
        }

        /**
         * Sets the value for {@code innerTypes}.
         * 
         * @param innerTypes The value to be set.
         * @return This instance for chain calling.
         */
        public Builder innerTypes(List<TypeSyntax> innerTypes) {
            this.innerTypes.clear();
            this.innerTypes.asTransient().addAll(innerTypes);
            return this;
        }

        /**
         * Adds a value to {@code innerTypes}.
         * 
         * @param innerTypes The value tp add
         * @return This instance for chain calling.
         */
        public Builder addInnerType(TypeSyntax innerType) {
            this.innerTypes.asTransient().add(innerType);
            return this;
        }

        /**
         * Returns a new instance of {@link ClassSyntax}
         * 
         * @return A new instance of {@link ClassSyntax}
         */
        public ClassSyntax build() {
            return new ClassSyntax(this);
        }
    }
}
