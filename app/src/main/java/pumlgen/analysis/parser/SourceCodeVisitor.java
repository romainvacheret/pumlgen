package pumlgen.analysis.parser;

import java.util.stream.Collectors;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import pumlgen.analysis.builders.ClassOrInterfaceBuilder;
import pumlgen.analysis.builders.MethodBuilder;
import pumlgen.analysis.builders.VariableBuilder;
import pumlgen.analysis.summaries.ClassOrInterfaceSummary;
import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

public class SourceCodeVisitor {

	public ClassOrInterfaceSummary visit(ClassOrInterfaceDeclaration declaration) {
		return new ClassOrInterfaceBuilder()
				.withName(declaration.getNameAsString())
				.withModifiers(declaration.getModifiers().stream()
						.map(Modifier::toString)
						.collect(Collectors.toSet()))
				.withImplementedTypes(declaration.getImplementedTypes().stream()
						.map(ClassOrInterfaceType::getNameAsString)
						.collect(Collectors.toSet()))
				.withMethods(declaration.getMethods().stream()
						.map(method -> visit(method))
						.collect(Collectors.toSet()))
				.build();
		// TODO add attributes
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
}
