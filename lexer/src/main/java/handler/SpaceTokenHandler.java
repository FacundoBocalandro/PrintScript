package handler;

import java.util.Optional;
import stream.CharacterStream;
import token.Token;
import token.TokenType;

public class SpaceTokenHandler implements TokenHandler {
  private static final char SPACE_CHAR = ' ';

  @Override
  public Optional<Token> handle(CharacterStream statement) {
    char read = statement.peek();
    if (read == SPACE_CHAR) {
      statement.skipNChars(1);
      return Optional.of(new Token(TokenType.SPACE_CHAR, String.valueOf(SPACE_CHAR)));
    }
    return Optional.empty();
  }
}