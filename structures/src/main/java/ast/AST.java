package ast;

import ASTVisitor.ASTVisitor;
import token.Token;

public interface AST {
  Token getValue();

  AST getRightChild();

  AST getLeftChild();

  void accept(ASTVisitor visitor);

  default String print() {
    return "{"
        + ((getLeftChild() == null) ? "null" : getLeftChild().print())
        + "|"
        + getValue().print()
        + "|"
        + ((getRightChild() == null) ? "null" : getRightChild().print())
        + "}";
  }
}
