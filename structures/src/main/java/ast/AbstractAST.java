package ast;

import token.Token;

public abstract class AbstractAST implements AST {
  private Token value;
  private AST leftChild;
  private AST rightChild;

  public AbstractAST(Token value) {
    this.value = value;
  }

  public AbstractAST(Token value, AST leftChild, AST rightChild) {
    this.value = value;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  @Override
  public Token getValue() {
    return value;
  }

  @Override
  public AST getRightChild() {
    return rightChild;
  }

  @Override
  public AST getLeftChild() {
    return leftChild;
  }
}
