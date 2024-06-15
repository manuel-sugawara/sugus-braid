package mx.sugus.braid.jsyntax.transforms;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.jsyntax.TypeSyntax;

/**
 * Interface to match types.
 */
@FunctionalInterface
public interface TypeMatcher {
    /**
     * Returns true if the given type matches.
     *
     * @param node The type to match against.
     * @return true if the given type matches.
     */
    boolean matches(TypeSyntax node);

    /**
     * Returns true if the matcher matches when no types are present.
     */
    default boolean matchesOnEmpty() {
        return false;
    }

    /**
     * Returns a new type matcher by name.
     *
     * @param name the name to match against
     * @return A new type matcher by name.
     */
    static TypeMatcher byName(String name) {
        return new NameMatcher(name);
    }

    /**
     * Returns a new type matcher by modifiers.
     *
     * @param modifiers the modifiers to match against
     * @return A new type matcher by contains all modifiers.
     */
    static TypeMatcher byModifiers(Modifier... modifiers) {
        return new ContainsAllModifiersMatcher(Arrays.asList(modifiers));
    }

    /**
     * Returns a new type matcher that matches if all the matchers match.
     *
     * @param matchers the matchers to match against
     * @return A new type matcher that matches if all the matchers match
     */
    static TypeMatcher byAllMatch(TypeMatcher... matchers) {
        return new AndMatcher(Arrays.asList(matchers));
    }

    /**
     * Returns an inner type matcher that matches the inner type. Callers need to also validate the parent type using
     * {@link InnerTypeMatcher#parentMatcher}.
     *
     * @param parentMatcher The parent type matcher
     * @param childMatcher  The child type matcher
     * @return An inner type matcher
     */
    static InnerTypeMatcher innerTypeMatcher(TypeMatcher parentMatcher, TypeMatcher childMatcher) {
        return new InnerTypeMatcher(parentMatcher, childMatcher);
    }

    /**
     * Matches a type by name.
     */
    class NameMatcher implements TypeMatcher {
        private final String name;

        NameMatcher(String name) {
            this.name = Objects.requireNonNull(name, "name");
        }

        @Override
        public boolean matches(TypeSyntax node) {
            return node.name().equals(name);
        }
    }

    /**
     * Matches a type by modifiers.
     */
    class ContainsAllModifiersMatcher implements TypeMatcher {
        private final Collection<Modifier> modifiers;

        ContainsAllModifiersMatcher(Collection<Modifier> modifiers) {
            this.modifiers = modifiers;
        }

        @Override
        public boolean matches(TypeSyntax node) {
            return node.modifiers().containsAll(modifiers);
        }
    }

    /**
     * Matches an inner type within a parent type. The parent type MUST be matched first using
     * {@link #parentMatches(TypeSyntax)}.
     */
    class InnerTypeMatcher implements TypeMatcher {
        private final TypeMatcher parentMatcher;
        private final TypeMatcher childMatcher;

        InnerTypeMatcher(TypeMatcher parentMatcher, TypeMatcher childMatcher) {
            this.parentMatcher = Objects.requireNonNull(parentMatcher, "parentMatcher");
            this.childMatcher = Objects.requireNonNull(childMatcher, "childMatcher");
        }

        @Override
        public boolean matches(TypeSyntax node) {
            return childMatcher.matches(node);
        }

        public boolean parentMatches(TypeSyntax node) {
            return parentMatcher.matches(node);
        }
    }

    /**
     * Matches if all the given matchers match.
     */
    class AndMatcher implements TypeMatcher {
        private final Collection<TypeMatcher> matchers;

        public AndMatcher(Collection<TypeMatcher> matchers) {
            this.matchers = Collections.unmodifiableCollection(matchers);
        }

        @Override
        public boolean matches(TypeSyntax node) {
            for (var matcher : matchers) {
                if (!matcher.matches(node)) {
                    return false;
                }
            }
            return true;
        }
    }
}
