package pumlgen.analysis.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		List<ClassOrInterfaceType> extendedTypes = declaration.getExtendedTypes();
		List<ClassOrInterfaceType> implementedTypes = declaration.getImplementedTypes();
		System.out.println("Extended" + extendedTypes.toString());
		System.out.println("Implemented" + implementedTypes.toString());

		// TODO: refactor ClassOrInterfaceSummary's builder with @Singular
		// to allow to chain several adding to the same collection
		return ClassOrInterfaceSummary.builder()
			.name(declaration.getNameAsString())
			.isInterface(declaration.isInterface())
			.modifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.map(modifier -> modifier.replace(" ", ""))
				.collect(Collectors.toSet()))
			.implementedTypes(declaration.getImplementedTypes().stream()
				.map(ClassOrInterfaceType::getNameAsString)
				.collect(Collectors.toSet()))
			.extendedTypes(declaration.getExtendedTypes().stream()
				.map(ClassOrInterfaceType::getNameAsString)
				.collect(Collectors.toSet()))
			.methods(Stream.concat(
				declaration.getConstructors().stream().map(this::visit),
				declaration.getMethods().stream().map(this::visit)
			).collect(Collectors.toSet()))
			.attributes(declaration.getFields().stream()
				.map(this::visit)
				.collect(Collectors.toSet()))
			.build();
	}

	public MethodSummary visit(MethodDeclaration declaration) {
		return MethodSummary.builder()
			.name(declaration.getNameAsString())
			.type(declaration.getTypeAsString())
			.modifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.parameters(declaration.getParameters().stream()
				.map(this::visit)
				.toList())
			.build();
	}

	public MethodSummary visit(ConstructorDeclaration declaration) {
		return MethodSummary.builder()
			.name("this")
			.type(declaration.getSignature().getName())
			.modifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.parameters(declaration.getParameters().stream()
				.map(this::visit)
				.toList())
			.build();
	}

	public VariableSummary visit(Parameter parameter) {
		return VariableSummary.builder()
			.name(parameter.getNameAsString())
			.type(parameter.getTypeAsString())
			.build();
	}

	public VariableSummary visit(FieldDeclaration declaration) {
		return VariableSummary.builder()
			.name(declaration.getVariable(0).getNameAsString())
			.modifiers(declaration.getModifiers().stream()
				.map(Modifier::toString)
				.collect(Collectors.toSet()))
			.type(declaration.getVariables().get(0).getTypeAsString())
			.build();
	}

	public String visit(PackageDeclaration declaration) {
		return declaration.getNameAsString();
	}
}
