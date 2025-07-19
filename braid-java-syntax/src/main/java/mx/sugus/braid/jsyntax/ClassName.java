package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represent the name of a Java class
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ClassName implements TypeName {
    private final String name;
    private final String packageName;

    private ClassName(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.packageName = builder.packageName;
    }

    public TypeKind kind() {
        return TypeKind.CLASS;
    }

    /**
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * 
     * @return The value of the {@code packageName} member
     */
    public String packageName() {
        return this.packageName;
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
        return visitor.visitClassName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassName that = (ClassName) obj;
        return this.name.equals(that.name)
            && Objects.equals(this.packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + name.hashCode();
        hashCode = 31 * hashCode + (packageName != null ? packageName.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "ClassName{"
            + "kind: " + kind()
            + ", name: " + name
            + ", packageName: " + packageName + "}";
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
     * Creates a class name using the given java class.
     */
    public static ClassName from(Class<?> kclass) {
        if (kclass.isArray()) {
                throw new IllegalArgumentException("Array types not supported, try using TypeName.from(Class<?>) instead");
            }
            Class<?> enclosing = kclass.getEnclosingClass();
            if (enclosing == null) {
                return builder().packageName(kclass.getPackageName()).name(kclass.getSimpleName()).build();
            }
            java.util.Deque<String> deque = new java.util.ArrayDeque<>();
            deque.add(kclass.getSimpleName());
            while (enclosing != null) {
                deque.push(enclosing.getSimpleName());
                enclosing = enclosing.getEnclosingClass();
            }
            return builder().packageName(kclass.getPackageName()).name(String.join(".", deque)).build();
    }

    /**
     * Creates a class name with the given package and simple name.
     */
    public static ClassName from(String packageName, String simpleName) {
        return builder().packageName(packageName).name(simpleName).build();
    }

    /**
     * Creates a class name without a package.
     */
    public static ClassName from(String simpleName) {
        return builder()
                .name(simpleName)
                .build();
    }

    /**
     * Parses the given name as qualified java type. Recognizes {@code #} as package separator to
     * distinguish the package name from the class name. If not uses dots and takes the last
     * segment as a class name and the previous ones as package name.
     */
    public static ClassName parse(String name) {
        int splitIndex = -1;
            // Check if the `name` is from a shape
            int sharpIndex = name.indexOf('#');
            if (sharpIndex != -1) {
                splitIndex = sharpIndex;
            } else {
                // Check if the `name` is from a fully qualified class name
                int lastDotIndex = name.lastIndexOf('.');
                if (lastDotIndex == -1) {
                    return ClassName.builder().name(name).build();
                }
                splitIndex = lastDotIndex;
            }
            return builder()
                .packageName(name.substring(0, splitIndex))
                .name(name.substring(splitIndex + 1))
                .build();
    }

    /**
     * Returns the top level enclosing class name if this class name represents an inner class,
     * otherwise returns this className unchanged.
     */
    public static ClassName toEnclosing(ClassName className) {
        String name = className.name();
            int indexOfDot = name.indexOf('.');
            if (indexOfDot == -1) {
                return className;
            }
            return className.toBuilder().name(name.substring(0, indexOfDot)).build();
    }

    /**
     * Returns the type name as class name. If the given type name is a parametrized type name it
     * returns its base class, if this is an array type, the component class.
     */
    public static ClassName toClassName(TypeName type) {
        while (true) {
                if (type instanceof ClassName c) {
                    return c;
                }
                if (type instanceof ParameterizedTypeName p) {
                    return p.rawType();
                }
                if (type instanceof ArrayTypeName a) {
                    type = a.componentType();
                    continue;
                }
                break;
            }
            throw new IllegalArgumentException("Cannot convert type: " + type.kind() + ", to java class");
    }

    /**
     * A class to build instances of ClassName
     */
    public static final class Builder implements TypeName.Builder {
        private String name;
        private String packageName;

        Builder() {
        }

        Builder(ClassName data) {
            this.name = data.name;
            this.packageName = data.packageName;
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
         * Sets the value for {@code packageName}.
         * 
         * @param packageName The value to be set.
         * @return This instance for chain calling.
         */
        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        /**
         * Returns a new instance of {@link ClassName}
         * 
         * @return A new instance of {@link ClassName}
         */
        public ClassName build() {
            return new ClassName(this);
        }
    }
}
