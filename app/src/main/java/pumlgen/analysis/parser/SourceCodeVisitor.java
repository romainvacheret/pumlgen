package pumlgen.analysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import pumlgen.analysis.builders.ClassOrInterfaceBuilder;
import pumlgen.analysis.builders.MethodBuilder;
import pumlgen.analysis.builders.VariableBuilder;
import pumlgen.analysis.summaries.ClassOrInterfaceSummary;
import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

public class SourceCodeVisitor {

	public static Optional<CompilationUnit> generateCompilationUnitFromFile(File file) {
		CompilationUnit compilationUnit = null;
		try {
			compilationUnit = StaticJavaParser.parse(file);
		} catch(FileNotFoundException e) {
			// Nothing to do
		}

		return Optional.of(compilationUnit);
	}

	public Set<ClassOrInterfaceSummary> visit(CompilationUnit compilationUnit) {
		return compilationUnit.getTypes().stream()
				.filter(type -> type.isClassOrInterfaceDeclaration())
				.map(TypeDeclaration::asClassOrInterfaceDeclaration)
				.map(declaration -> visit(declaration))
				.collect(Collectors.toSet());
	}

	public ClassOrInterfaceSummary visit(ClassOrInterfaceDeclaration declaration) {
		return new ClassOrInterfaceBuilder()
			.withName(declaration.getNameAsString())
			.withIsInterface(declaration.isInterface())
			.withModifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.withImplementedTypes(declaration.getImplementedTypes().stream()
				.map(ClassOrInterfaceType::getNameAsString)
				.collect(Collectors.toSet()))
			.withMethods(declaration.getMethods().stream()
				.map(method -> visit(method))
				.collect(Collectors.toSet()))
			.withAttributes(declaration.getFields().stream()
				.map(attribute -> visit(attribute))
				.collect(Collectors.toSet()))
			.build();
	}

	public MethodSummary visit(MethodDeclaration declaration) {
		return new MethodBuilder()
			.withName(declaration.getNameAsString())
			.withType(declaration.getTypeAsString())
			.withModifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.withParameters(declaration.getParameters().stream()
				.map(parameter -> visit(parameter))
				.collect(Collectors.toList()))
			.build();
	}

	public VariableSummary visit(Parameter parameter) {
		return new VariableBuilder()
			.withName(parameter.getNameAsString())
			.withType(parameter.getTypeAsString())
			.build();
	}

	public VariableSummary visit(FieldDeclaration declaration) {
		 return new VariableBuilder()
			 .withName(declaration.getVariable(0).getNameAsString())
			 .withModifiers(declaration.getModifiers().stream()
			 	.map(Modifier::toString)
			 	.collect(Collectors.toSet()))
			 .withType(declaration.getVariables().get(0).getTypeAsString())
			 .build();
	}
}
