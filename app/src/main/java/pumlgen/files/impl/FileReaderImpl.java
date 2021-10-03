package pumlgen.files.impl;

import java.io.File;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;

import pumlgen.analysis.parser.SourceCodeVisitor;
import pumlgen.files.models.FileReader;
import pumlgen.uml.builders.UMLPackageBuilder;

public class FileReaderImpl implements FileReader {
	private final SourceCodeVisitor visitor = new SourceCodeVisitor();

	@Override
	public Optional<UMLPackageBuilder> read(final File file) {
		Optional<CompilationUnit> parsedFile = SourceCodeVisitor.generateCompilationUnitFromFile(file);
		return parsedFile.flatMap(visitor::visit);
	}
	
}
