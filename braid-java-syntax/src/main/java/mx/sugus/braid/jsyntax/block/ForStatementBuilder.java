package mx.sugus.braid.jsyntax.block;

import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ForStatement;

public class ForStatementBuilder extends AbstractBlockBuilder<ForStatementBuilder, ForStatement> {

    private final ForStatement.Builder builder;

    public ForStatementBuilder(CodeBlock initializer) {
        this.builder = ForStatement.builder()
                                   .initializer(initializer);
    }

    @Override
    public ForStatement build() {
        return builder.statement(toBlock())
                      .build();
    }
}
