package mx.sugus.braid.jsyntax.transforms;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.BaseMethodSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;

/**
 * Interface to match methods.
 */
public interface MethodMatcher {
    /**
     * Returns true if the given method matches.
     *
     * @param node The method to match against.
     * @return true if the given method matches.
     */
    boolean matches(BaseMethodSyntax node);

    /**
     * Returns true if the matcher matches when no methods are present.
     */
    default boolean matchesOnEmpty() {
        return false;
    }

    /**
     * Returns a new method matcher by name.
     *
     * @param name the name to match against
     * @return A new method matcher by name.
     */
    static MethodMatcher byName(String name) {
        return new NameMatcher(name);
    }

    /**
     * Returns a new method matcher by modifiers.
     *
     * @param modifiers the modifiers to match against
     * @return A new method matcher by contains all modifiers.
     */
    static MethodMatcher byModifiers(Modifier... modifiers) {
        return new ContainsAllModifiersMatcher(Arrays.asList(modifiers));
    }

    /**
     * Returns a new method matcher that matches any method.
     */
    static MethodMatcher any() {
        return AnyMatcher.INSTANCE;
    }

    /**
     * Returns a new method matcher that matches if all the matchers match.
     *
     * @param matchers the matchers to match against
     * @return A new method matcher that matches if all the matchers match
     */
    static MethodMatcher byAllMatch(MethodMatcher... matchers) {
        return new AndMatcher(Arrays.asList(matchers));
    }

    /**
     * Matches a method by name.
     */
    class NameMatcher implements MethodMatcher {
        private final String name;

        NameMatcher(String name) {
            this.name = Objects.requireNonNull(name, "name");
        }

        @Override
        public boolean matches(BaseMethodSyntax node) {
            if (node instanceof MethodSyntax m) {
                return m.name().equals(name);
            }
            if (node instanceof AbstractMethodSyntax m) {
                return m.name().equals(name);
            }
            return false;
        }
    }

    /**
     * Matches a method by modifiers.
     */
    class ContainsAllModifiersMatcher implements MethodMatcher {
        private final Collection<Modifier> modifiers;

        ContainsAllModifiersMatcher(Collection<Modifier> modifiers) {
            this.modifiers = Collections.unmodifiableCollection(modifiers);
        }

        @Override
        public boolean matches(BaseMethodSyntax node) {
            return node.modifiers().containsAll(modifiers);
        }
    }

    /**
     * Matches if all the given matchers match.
     */
    class AndMatcher implements MethodMatcher {
        private final Collection<MethodMatcher> matchers;

        public AndMatcher(Collection<MethodMatcher> matchers) {
            this.matchers = Collections.unmodifiableCollection(matchers);
        }

        @Override
        public boolean matches(BaseMethodSyntax node) {
            for (var matcher : matchers) {
                if (!matcher.matches(node)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Matches any method or matches when there are no methods
     */
    class AnyMatcher implements MethodMatcher {
        static final AnyMatcher INSTANCE = new AnyMatcher();

        @Override
        public boolean matches(BaseMethodSyntax node) {
            return true;
        }

        @Override
        public boolean matchesOnEmpty() {
            return true;
        }
    }

}
