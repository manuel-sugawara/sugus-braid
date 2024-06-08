package mx.sugus.braid.jsyntax.block;

import java.util.ArrayDeque;
import java.util.Deque;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.Expression;
import mx.sugus.braid.jsyntax.IfStatement;

public class IfStatementBuilder extends AbstractBlockBuilder<IfStatementBuilder, IfStatement> {

    private final IfStatement.Builder builder;
    private final Deque<ElseIfBuilder> elseIfStack;
    private ElseBuilder elseBlock;

    public IfStatementBuilder(Expression condition) {
        this.builder = IfStatement.builder()
                                  .expression(condition);
        this.elseIfStack = new ArrayDeque<>();
    }

    @Override
    public IfStatement build() {
        if (elseIfStack.isEmpty()) {
            if (elseBlock != null) {
                builder.elseStatement(elseBlock.build());
            }
            return builder.statement(toBlock())
                          .build();
        }
        var current = elseIfStack.pop();
        if (elseBlock != null) {
            current.elseStatement(elseBlock.build());
        }
        var ifStatement = current.build();
        while (!elseIfStack.isEmpty()) {
            current = elseIfStack.pop();
            current.elseStatement(ifStatement);
            ifStatement = current.build();
        }

        return builder.statement(toBlock())
                      .elseStatement(ifStatement)
                      .build();
    }

    public ElseIfBuilder addElseIf(Expression condition) {
        var result = new ElseIfBuilder(condition);
        elseIfStack.push(result);
        return result;
    }

    public ElseBuilder addElse() {
        this.elseBlock = new ElseBuilder();
        return elseBlock;
    }

    static class ElseIfBuilder extends AbstractBlockBuilder<ElseIfBuilder, IfStatement> {
        private final IfStatement.Builder builder;

        ElseIfBuilder(Expression condition) {
            this.builder = IfStatement.builder()
                                      .expression(condition);
        }

        public ElseIfBuilder elseStatement(Block block) {
            this.builder.elseStatement(block);
            return this;
        }

        public ElseIfBuilder elseStatement(IfStatement ifStatement) {
            this.builder.elseStatement(ifStatement);
            return this;
        }

        @Override
        public IfStatement build() {
            return builder.statement(toBlock())
                          .build();
        }
    }

    static class ElseBuilder extends AbstractBlockBuilder<ElseBuilder, Block> {
        @Override
        public Block build() {
            return toBlock();
        }
    }
}
