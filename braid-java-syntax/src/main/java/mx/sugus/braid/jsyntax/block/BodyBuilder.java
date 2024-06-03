package mx.sugus.braid.jsyntax.block;

import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.rt.util.BuilderReference;

/**
 * Builder class that allows to build java blocks, i.e., sequences of statements.
 */
public final class BodyBuilder extends AbstractBlockBuilder<BodyBuilder, Block> {
    /**
     * Creates a new empty body builder.
     */
    public BodyBuilder() {
    }

    /**
     * Creates a new body builder initialized with the statements in the given block.
     */
    public BodyBuilder(Block block) {
        if (block != null) {
            for (var stmt : block.statements()) {
                addStatement(stmt);
            }
        }
    }

    @Override
    public Block build() {
        return toBlock();
    }

    /**
     * Creates a new BodyBuilder instance.
     *
     * @return a new BodyBuilder instance.
     */
    public static BodyBuilder create() {
        return new BodyBuilder();
    }

    /**
     * Creates a new BodyBuilder builder reference using the given block as its persistent state.
     *
     * @param persistent the persistent state for the body builder
     * @return a new BodyBuilder builder reference using the given block as its persistent state.
     */
    public static BuilderReference<Block, BodyBuilder> fromPersistent(Block persistent) {
        return new BodyBuilderReference(persistent);
    }

    /**
     * Builder reference for BodyBuilder. Allows coding using lambdas while preserving the state of the builder
     * {@snippet :
     *   builder.body(b ->
     *       b.ifStatement("temp < 28",
     *                   then -> then.addStatement("OK"),
     *                   otherwise -> otherwise.addStatement("TOO_HOT"))
     *   );
     *}
     */
    static class BodyBuilderReference extends AbstractBuilderReference<Block, BodyBuilder> {
        private static final Block EMPTY = Block.builder().build();

        BodyBuilderReference(Block persistent) {
            super(persistent);
        }

        @Override
        protected Block emptyPersistent() {
            return EMPTY;
        }

        @Override
        protected BodyBuilder emptyTransient() {
            return new BodyBuilder();
        }

        @Override
        protected Block transientToPersistent(BodyBuilder source) {
            if (asTransient != null) {
                return asTransient.build();
            }
            return null;
        }

        @Override
        protected BodyBuilder persistentToTransient(Block source) {
            return new BodyBuilder(asPersistent);
        }

        @Override
        protected BodyBuilder clearTransient(BodyBuilder source) {
            return new BodyBuilder();
        }
    }
}
