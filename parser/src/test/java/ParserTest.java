import ast.AST;
import exceptions.ASTBuildException;
import exceptions.BadTokenException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lexer.Lexer;
import lexer.PrintScriptLexer2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import token.Token;
import token.TokenType;

@SuppressWarnings("PMD")
public class ParserTest {
  private static String VARIABLE_KEYWORD = "let";

  @Test
  public void testParser2() throws BadTokenException, ASTBuildException {
    Parser parser = new Parser();

    List<Token> generatedTokens = new ArrayList<>();
    generatedTokens.add(new Token(TokenType.IDENTIFIER, "a"));
    generatedTokens.add(new Token(TokenType.EQUALS, "="));
    generatedTokens.add(new Token(TokenType.NUMBER, "2"));
    generatedTokens.add(new Token(TokenType.ESC_CHAR, ";"));

    AST ast = parser.parse(generatedTokens);

    Assertions.assertEquals(TokenType.EQUALS, ast.getValue().getType());
    Assertions.assertEquals(TokenType.IDENTIFIER, ast.getLeftChild().getValue().getType());
    Assertions.assertEquals(TokenType.NUMBER, ast.getRightChild().getValue().getType());
  }

  @Test
  public void testParserShouldFail() {
    List<Token> generatedTokens = new ArrayList<>();

    generatedTokens.add(new Token(TokenType.VARIABLE_KEYWORD, VARIABLE_KEYWORD));
    generatedTokens.add(new Token(TokenType.IDENTIFIER, "a"));
    generatedTokens.add(new Token(TokenType.COLON, ":"));
    generatedTokens.add(new Token(TokenType.NUMBER_TYPE, "number"));
    generatedTokens.add(new Token(TokenType.EQUALS, "="));
    generatedTokens.add(new Token(TokenType.NUMBER, "5"));
    generatedTokens.add(new Token(TokenType.MULTIPLICATION_OPERATOR, "*"));
    generatedTokens.add(new Token(TokenType.NUMBER, "3"));
    generatedTokens.add(new Token(TokenType.MULTIPLICATION_OPERATOR, "*"));
    generatedTokens.add(new Token(TokenType.MULTIPLICATION_OPERATOR, "*"));
    generatedTokens.add(new Token(TokenType.PLUS_OPERATOR, "+"));
    generatedTokens.add(new Token(TokenType.NUMBER, "1"));
    generatedTokens.add(new Token(TokenType.ESC_CHAR, ";"));

    Assertions.assertThrows(
        BadTokenException.class,
        () -> {
          Parser parser = new Parser();
          parser.parse(generatedTokens);
        });
  }

  @Test
  public void testParserShouldFail_2() {
    List<Token> generatedTokens = new ArrayList<>();
    generatedTokens.add(new Token(TokenType.VARIABLE_KEYWORD, VARIABLE_KEYWORD));
    generatedTokens.add(new Token(TokenType.IDENTIFIER, "a"));
    generatedTokens.add(new Token(TokenType.COLON, ":"));
    generatedTokens.add(new Token(TokenType.NUMBER_TYPE, "number"));
    generatedTokens.add(new Token(TokenType.EQUALS, "="));
    generatedTokens.add(new Token(TokenType.NUMBER, "5"));
    generatedTokens.add(new Token(TokenType.MULTIPLICATION_OPERATOR, "*"));
    generatedTokens.add(new Token(TokenType.NUMBER, "3"));
    generatedTokens.add(new Token(TokenType.PLUS_OPERATOR, "+"));
    generatedTokens.add(new Token(TokenType.NUMBER, "1"));
    generatedTokens.add(new Token(TokenType.MINUS_OPERATOR, "-"));
    generatedTokens.add(new Token(TokenType.MINUS_OPERATOR, "-"));
    generatedTokens.add(new Token(TokenType.NUMBER, "2"));

    Assertions.assertThrows(
        BadTokenException.class,
        () -> {
          Parser parser = new Parser();
          parser.parse(generatedTokens);
        });
  }

  @Test
  public void assignation() throws FileNotFoundException {
    Lexer lexer = new PrintScriptLexer2();
    Parser parser = new Parser();
    String codeDirectory = Utils.getCodeDirectory("assignation");
    String outputDirectory = Utils.getOutputDirectory("assignation");

    List<String> statements = Utils.getCode(codeDirectory);

    List<AST> ASTs =
        statements.stream()
            .map(
                statement -> {
                  try {
                    List<Token> resultTokens = lexer.lex(statement);
                    return parser.parse(resultTokens);
                  } catch (BadTokenException | ASTBuildException e) {
                    System.out.println(statement);
                    e.printStackTrace();
                    throw new RuntimeException();
                  }
                })
            .collect(Collectors.toList());

    String result = ASTs.stream().map(AST::print).collect(Collectors.joining("\n"));

    Assertions.assertEquals(result, Utils.getOutput(outputDirectory));
  }

  @Test
  public void conditional() throws FileNotFoundException {
    Lexer lexer = new PrintScriptLexer2();
    Parser parser = new Parser();
    String codeDirectory = Utils.getCodeDirectory("conditional");
    String outputDirectory = Utils.getOutputDirectory("conditional");

    List<String> statements = Utils.getCode(codeDirectory);

    List<AST> ASTs =
        statements.stream()
            .map(
                statement -> {
                  try {
                    List<Token> resultTokens = lexer.lex(statement);
                    return parser.parse(resultTokens);
                  } catch (BadTokenException | ASTBuildException e) {
                    System.out.println(statement);
                    e.printStackTrace();
                    throw new RuntimeException();
                  }
                })
            .collect(Collectors.toList());

    String result = ASTs.stream().map(AST::print).collect(Collectors.joining("\n"));

    Assertions.assertEquals(result, Utils.getOutput(outputDirectory));
  }

  @Test
  public void operation() throws FileNotFoundException {
    Lexer lexer = new PrintScriptLexer2();
    Parser parser = new Parser();
    String codeDirectory = Utils.getCodeDirectory("operation");
    String outputDirectory = Utils.getOutputDirectory("operation");

    List<String> statements = Utils.getCode(codeDirectory);

    List<AST> ASTs =
        statements.stream()
            .map(
                statement -> {
                  try {
                    List<Token> resultTokens = lexer.lex(statement);
                    return parser.parse(resultTokens);
                  } catch (BadTokenException | ASTBuildException e) {
                    throw new RuntimeException();
                  }
                })
            .collect(Collectors.toList());

    String result = ASTs.stream().map(AST::print).collect(Collectors.joining("\n"));

    Assertions.assertEquals(result, Utils.getOutput(outputDirectory));
  }

  @Test
  public void invalid() throws FileNotFoundException {
    Lexer lexer = new PrintScriptLexer2();
    Parser parser = new Parser();
    String codeDirectory = Utils.getCodeDirectory("invalid");

    List<String> statements = Utils.getCode(codeDirectory);

    statements.forEach(
        statement -> {
          System.out.println(statement);
          Assertions.assertThrows(
              BadTokenException.class,
              () -> {
                parser.parse(lexer.lex(statement));
              });
        });
  }
}
