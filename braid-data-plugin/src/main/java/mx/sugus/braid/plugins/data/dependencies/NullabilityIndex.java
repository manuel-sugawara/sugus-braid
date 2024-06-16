package mx.sugus.braid.plugins.data.dependencies;

import software.amazon.smithy.model.knowledge.NullableIndex;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DefaultTrait;
import software.amazon.smithy.model.traits.RequiredTrait;

/**
 * Represents the logic to determine if a given member is nullable. Similar to {@link NullableIndex} but the check modes are
 * encapsulated.
 */
public interface NullabilityIndex {
    /**
     * Returns true if the given member is nullable.
     *
     * @param member The member to check.
     * @return true if the given member is nullable.
     */
    boolean isNullable(MemberShape member);

    /**
     * Returns true if the given member is required. Equivalent to {@code !isMemberNullable(member)}.
     *
     * @param member The member to check.
     * @return true if the given member is reqired.
     */
    default boolean isRequired(MemberShape member) {
        return !isNullable(member);
    }

    /**
     * Returns true if the given member is explicitly required. Similar to {@link #isRequired(MemberShape)} but checks if
     * the shape has the default trait but not the required trait.
     *
     * @param member The member to check.
     * @return true if the given member is required but not implicitly by having a default trait.
     */
    default boolean isExplicitlyRequired(MemberShape member) {
        var isMemberExplicitlyRequired = !isNullable(member);
        if (isMemberExplicitlyRequired) {
            if (member.hasTrait(DefaultTrait.class)) {
                return member.hasTrait(RequiredTrait.class);
            }
        }
        return isMemberExplicitlyRequired;
    }
}
