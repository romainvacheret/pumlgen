package pumlgen.files;

import java.io.File;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;

import pumlgen.analysis.parser.SourceCodeVisitor;

public class FileHandler {
   public void handleFile(File file) {
      SourceCodeVisitor visitor = new SourceCodeVisitor();
      Optional<CompilationUnit> parsedFile = SourceCodeVisitor.generateCompilationUnitFromFile(file);
      parsedFile.ifPresent(cu -> visitor
         .visit(cu)
         .ifPresent(result -> System.out.println("----\n" + result.build() + "\n------")));
   }
}
