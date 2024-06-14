package mx.sugus.braid.jsyntax.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.sugus.braid.jsyntax.BaseMethodSyntax;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.SyntaxNode;
import mx.sugus.braid.jsyntax.SyntaxNodeRewriteVisitor;
import mx.sugus.braid.jsyntax.TypeSyntax;

/**
 * A syntax node transform to add methods to a type.
 */
public final class AddMethodsTransform implements SyntaxNodeTransformer {
    private final TypeMatcher typeMatcher;
    private final MethodMatcher methodMatcher;
    private final AddPosition position;
    private final List<BaseMethodSyntax> methods;

    AddMethodsTransform(Builder builder) {
        this.typeMatcher = Objects.requireNonNull(builder.typeMatcher, "typeMatcher");
        this.methodMatcher = Objects.requireNonNull(builder.methodMatcher, "methodMatcher");
        this.position = Objects.requireNonNull(builder.position, "position");
        this.methods = Objects.requireNonNull(builder.methods, "methods");
    }

    public List<BaseMethodSyntax> methods() {
        return methods;
    }

    @Override
    public SyntaxNode transform(SyntaxNode node) {
        var visitor = new TransformVisitor();
        var result = node.accept(visitor);
        if (!visitor.added) {
            throw new IllegalArgumentException("The given type does not match any of the constraints, methods not added."
            + " node: " + node);
        }
        return result;
    }

    /**
     * Returns a new builder.
     *
     * @return a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TypeMatcher typeMatcher;
        private MethodMatcher methodMatcher;
        private AddPosition position = AddPosition.AFTER;
        private List<BaseMethodSyntax> methods = new ArrayList<>();

        public Builder methods(List<? extends BaseMethodSyntax> methods) {
            this.methods.addAll(methods);
            return this;
        }

        public Builder position(AddPosition position) {
            this.position = position;
            return this;
        }

        public Builder addAfter() {
            this.position = AddPosition.AFTER;
            return this;
        }

        public Builder addBefore() {
            this.position = AddPosition.BEFORE;
            return this;
        }

        public Builder methodMatcher(MethodMatcher methodMatcher) {
            this.methodMatcher = methodMatcher;
            return this;
        }

        public Builder typeMatcher(TypeMatcher typeMatcher) {
            this.typeMatcher = typeMatcher;
            return this;
        }

        public AddMethodsTransform build() {
            return new AddMethodsTransform(this);
        }
    }

    class TransformVisitor extends SyntaxNodeRewriteVisitor {
        private boolean added = false;

        @Override
        public ClassSyntax visitClassSyntax(ClassSyntax syntax) {
            var node = super.visitClassSyntax(syntax);
            return (ClassSyntax) visitTypeSyntax(node);
        }

        @Override
        public InterfaceSyntax visitInterfaceSyntax(InterfaceSyntax syntax) {
            var node = super.visitInterfaceSyntax(syntax);
            return (InterfaceSyntax) visitTypeSyntax(node);
        }

        @Override
        public EnumSyntax visitEnumSyntax(EnumSyntax syntax) {
            var node = super.visitEnumSyntax(syntax);
            return (EnumSyntax) visitTypeSyntax(node);
        }

        private TypeSyntax visitTypeSyntax(TypeSyntax typeSyntax) {
            if (!typeMatcher.matches(typeSyntax)) {
                return typeSyntax;
            }
            var methods = new ArrayList<BaseMethodSyntax>();
            var done = false;
            if (position == AddPosition.BEFORE) {
                for (var method : typeSyntax.methods()) {
                    if (!done && methodMatcher.matches(method)) {
                        methods.addAll(methods());
                        done = true;
                    }
                    methods.add(method);
                }
                if (!done) {
                    if (typeSyntax.methods().isEmpty() && methodMatcher.matchesOnEmpty()) {
                        methods.addAll(methods());
                        done = true;
                    }
                }
            } else if (position == AddPosition.AFTER) {
                var foundFirstMatch = false;
                for (var method : typeSyntax.methods()) {
                    if (methodMatcher.matches(method) && !done) {
                        foundFirstMatch = true;
                        methods.add(method);
                    } else if (foundFirstMatch && !done) {
                        methods.addAll(methods());
                        methods.add(method);
                        done = true;
                    } else {
                        methods.add(method);
                    }
                }
                if (!done) {
                    if (foundFirstMatch || (typeSyntax.methods().isEmpty() && methodMatcher.matchesOnEmpty())) {
                        methods.addAll(methods());
                        done = true;
                    }
                }
            }
            added = done;
            if (done) {
                return addMethods(typeSyntax, methods);
            }
            return typeSyntax;
        }

        private TypeSyntax addMethods(TypeSyntax typeSyntax, List<BaseMethodSyntax> methods) {
            if (typeSyntax instanceof InterfaceSyntax node) {
                return node.toBuilder().methods(methods).build();
            }
            if (typeSyntax instanceof ClassSyntax node) {
                return node.toBuilder().methods(methods).build();
            }
            if (typeSyntax instanceof EnumSyntax node) {
                return node.toBuilder().methods(methods).build();
            }
            throw new UnsupportedOperationException("the type syntax `" + typeSyntax + "` is unsupported");
        }
    }
}
