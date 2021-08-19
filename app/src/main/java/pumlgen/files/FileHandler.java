package pumlgen.files;

import com.github.javaparser.ast.CompilationUnit;
import pumlgen.analysis.parser.SourceCodeVisitor;
import pumlgen.uml.builders.UMLClassOrInterfaceBuilder;

import java.io.File;
import java.util.Optional;

public class FileHandler {
   public void handleFile(File file) {
      SourceCodeVisitor visitor = new SourceCodeVisitor();
      Optional<CompilationUnit> parsedFile = SourceCodeVisitor.generateCompilationUnitFromFile(file);
      parsedFile.ifPresent(cu -> {
          visitor.visit(cu).stream()
               .map(summary -> new UMLClassOrInterfaceBuilder(
                    summary.getName(),
                    summary.getIsInterface(),
                    summary.getModifiers().contains("abstract"))
                       .withMethods(summary.getMethods())
                       .withAttributes(summary.getAttributes())
                       .build())
               .forEach(System.out::println);
      });
   }
}
