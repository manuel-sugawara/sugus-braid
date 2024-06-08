package mx.sugus.braid.jsyntax.transforms;

import java.util.Arrays;
import java.util.Collection;
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
     * Matches if all the given matchers match.
     */
    class AndMatcher implements TypeMatcher {
        private final Collection<TypeMatcher> matchers;

        public AndMatcher(Collection<TypeMatcher> matchers) {
            this.matchers = matchers;
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
