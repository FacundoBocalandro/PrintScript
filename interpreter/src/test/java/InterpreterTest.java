import exceptions.ASTBuildException;
import exceptions.BadTokenException;
import interpreter.Interpreter;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.*;

public class InterpreterTest {

  @Test
  public void invalid() {
    String invalidStatement = "const x: int = |123|";

    Assertions.assertThrows(
        BadTokenException.class,
        () -> {
          Interpreter interpreter = new Interpreter("1.0", new LinkedList<>()::add);
          interpreter.interpret(invalidStatement);
        });
  }

  @Test
  public void assignation() throws FileNotFoundException, InvalidAlgorithmParameterException {
    List<String> output = new LinkedList<>();
    Interpreter interpreter = new Interpreter("1.0", output::add);

    String codeDirectory = Utils.getCodeDirectory("assignation");
    String outputDirectory = Utils.getOutputDirectory("assignation");

    Utils.getCode(codeDirectory)
        .forEach(
            statement -> {
              try {
                interpreter.interpret(statement);
              } catch (BadTokenException | ASTBuildException e) {
                e.printStackTrace();
              }
            });

    Assertions.assertEquals(String.join("\n", output), Utils.getOutput(outputDirectory));
  }

  @Test
  public void operation() throws FileNotFoundException, InvalidAlgorithmParameterException {
    List<String> output = new LinkedList<>();
    Interpreter interpreter = new Interpreter("1.1", output::add);

    String codeDirectory = Utils.getCodeDirectory("operation");
    String outputDirectory = Utils.getOutputDirectory("operation");

    Utils.getCode(codeDirectory)
        .forEach(
            statement -> {
              try {
                interpreter.interpret(statement);
              } catch (BadTokenException | ASTBuildException e) {
                e.printStackTrace();
              }
            });

    Assertions.assertEquals(String.join("\n", output), Utils.getOutput(outputDirectory));
  }

  @Test
  public void conditional() throws FileNotFoundException, InvalidAlgorithmParameterException {
    List<String> output = new LinkedList<>();
    Interpreter interpreter = new Interpreter("1.1", output::add);

    String codeDirectory = Utils.getCodeDirectory("conditional");
    String outputDirectory = Utils.getOutputDirectory("conditional");

    Utils.getCode(codeDirectory)
        .forEach(
            statement -> {
              try {
                interpreter.interpret(statement);
              } catch (BadTokenException | ASTBuildException e) {
                System.out.println(statement);
                e.printStackTrace();
              }
            });

    Assertions.assertEquals(String.join("\n", output), Utils.getOutput(outputDirectory));
  }
}
