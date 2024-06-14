package mx.sugus.braid.jsyntax.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.SyntaxNode;
import mx.sugus.braid.jsyntax.SyntaxNodeRewriteVisitor;
import mx.sugus.braid.jsyntax.TypeSyntax;

public class AddInnerTypesTransform implements SyntaxNodeTransformer {
    private final TypeMatcher.InnerTypeMatcher typeMatcher;
    private final AddPosition position;
    private final List<TypeSyntax> types;

    AddInnerTypesTransform(Builder builder) {
        this.typeMatcher = Objects.requireNonNull(builder.typeMatcher, "typeMatcher");
        this.position = Objects.requireNonNull(builder.position, "position");
        this.types = Objects.requireNonNull(builder.types, "types");
    }

    @Override
    public SyntaxNode transform(SyntaxNode node) {
        var visitor = new TransformVisitor();
        var result = node.accept(visitor);
        if (!visitor.added) {
            throw new IllegalArgumentException("The given type does not match any of the constraints, types not added."
                                               + " node: " + node);
        }
        return result;
    }

    public List<TypeSyntax> types() {
        return types;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TypeMatcher.InnerTypeMatcher typeMatcher;
        private AddPosition position = AddPosition.AFTER;
        private List<TypeSyntax> types = new ArrayList<>();

        public Builder types(List<? extends TypeSyntax> types) {
            this.types.addAll(types);
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

        public Builder typeMatcher(TypeMatcher.InnerTypeMatcher typeMatcher) {
            this.typeMatcher = typeMatcher;
            return this;
        }

        public Builder typeMatcher(TypeMatcher parentMatcher, TypeMatcher childMatcher) {
            this.typeMatcher = TypeMatcher.innerTypeMatcher(parentMatcher, childMatcher);
            return this;
        }

        public AddInnerTypesTransform build() {
            return new AddInnerTypesTransform(this);
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
            if (!typeMatcher.parentMatches(typeSyntax)) {
                return typeSyntax;
            }
            var innerTypes = new ArrayList<TypeSyntax>();
            var done = false;
            if (position == AddPosition.BEFORE) {
                for (var innerType : typeSyntax.innerTypes()) {
                    if (!done && typeMatcher.matches(innerType)) {
                        innerTypes.addAll(types());
                        done = true;
                    }
                    innerTypes.add(innerType);
                }
                if (!done) {
                    if (typeSyntax.methods().isEmpty() && typeMatcher.matchesOnEmpty()) {
                        innerTypes.addAll(types());
                        done = true;
                    }
                }
            } else if (position == AddPosition.AFTER) {
                var foundFirstMatch = false;
                for (var innerType : typeSyntax.innerTypes()) {
                    if (typeMatcher.matches(innerType) && !done) {
                        foundFirstMatch = true;
                        innerTypes.add(innerType);
                    } else if (foundFirstMatch && !done) {
                        innerTypes.addAll(types());
                        innerTypes.add(innerType);
                        done = true;
                    } else {
                        innerTypes.add(innerType);
                    }
                }
                if (!done) {
                    if (foundFirstMatch || (typeSyntax.methods().isEmpty() && typeMatcher.matchesOnEmpty())) {
                        innerTypes.addAll(types());
                        done = true;
                    }
                }
            }
            added = done;
            if (done) {
                return addInnerTypes(typeSyntax, innerTypes);
            }
            return typeSyntax;
        }

        private TypeSyntax addInnerTypes(TypeSyntax typeSyntax, List<TypeSyntax> innerTypes) {
            if (typeSyntax instanceof InterfaceSyntax node) {
                return node.toBuilder().innerTypes(innerTypes).build();
            }
            if (typeSyntax instanceof ClassSyntax node) {
                return node.toBuilder().innerTypes(innerTypes).build();
            }
            if (typeSyntax instanceof EnumSyntax node) {
                return node.toBuilder().innerTypes(innerTypes).build();
            }
            throw new UnsupportedOperationException("the type syntax `" + typeSyntax + "` is unsupported");
        }
    }
}