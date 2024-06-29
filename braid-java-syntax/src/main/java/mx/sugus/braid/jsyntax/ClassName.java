package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represent the name of a Java class</p>
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

    public String name() {
        return this.name;
    }

    public String packageName() {
        return this.packageName;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Creates a class name using the given java class.</p>
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
     * <p>Creates a class name with the given package and simple name.</p>
     */
    public static ClassName from(String packageName, String simpleName) {
        return builder().packageName(packageName).name(simpleName).build();
    }

    /**
     * <p>Creates a class name without a package.</p>
     */
    public static ClassName from(String simpleName) {
        return builder()
                .name(simpleName)
                .build();
    }

    /**
     * <p>Parses the given name as qualified java type. Recognizes <code>#</code> as package separator to
     * distinguish the package name from the class name. If not uses dots and takes the last
     * segment as a class name and the previous ones as package name.</p>
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
     * <p>Returns the top level enclosing class name if this class name represents an inner class,
     * otherwise returns this className unchanged.</p>
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
     * <p>Returns the type name as class name. If the given type name is a parametrized type name it
     * returns its base class, if this is an array type, the component class.</p>
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
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>packageName</code></p>
         */
        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public ClassName build() {
            return new ClassName(this);
        }
    }
}
