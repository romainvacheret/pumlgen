package pumlgen.analysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import pumlgen.analysis.builders.ClassOrInterfaceBuilder;
import pumlgen.analysis.builders.MethodBuilder;
import pumlgen.analysis.builders.VariableBuilder;
import pumlgen.analysis.summaries.ClassOrInterfaceSummary;
import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;
import pumlgen.uml.builders.UMLPackageBuilder;

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

	public Optional<UMLPackageBuilder> visit(CompilationUnit compilationUnit) {
		Optional<String> packageName = compilationUnit
			.getPackageDeclaration()
			.flatMap(p -> Optional.of(p.getNameAsString()));

		if(packageName.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(new UMLPackageBuilder(packageName.get())
			.withClassesOrInterfaces(compilationUnit.getTypes().stream()
				.filter(BodyDeclaration::isClassOrInterfaceDeclaration)
				.map(TypeDeclaration::asClassOrInterfaceDeclaration)
				.map(this::visit)
				.collect(Collectors.toSet())));
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
			.withMethods(declaration.getConstructors().stream()
				.map(this::visit)
				.collect(Collectors.toSet()))
			.withMethods(declaration.getMethods().stream()
				.map(this::visit)
				.collect(Collectors.toSet()))
			.withAttributes(declaration.getFields().stream()
				.map(this::visit)
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
				.map(this::visit)
				.toList())
			.build();
	}

	public MethodSummary visit(ConstructorDeclaration declaration) {
		return new MethodBuilder()
			.withName("this")
			.withType(declaration.getSignature().getName())
			.withModifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.withParameters(declaration.getParameters().stream()
				.map(this::visit)
				.toList())
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

	public String visit(PackageDeclaration declaration) {
		return declaration.getNameAsString();
	}
}
