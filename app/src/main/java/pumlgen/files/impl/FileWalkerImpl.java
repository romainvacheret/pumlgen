package pumlgen.files.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import pumlgen.files.models.FileReader;
import pumlgen.files.models.FileWalker;
import pumlgen.uml.builders.UMLPackageBuilder;

public class FileWalkerImpl implements FileWalker {

    private static final String DOT = ".";
    private static final String JAVA = "java";
    private static final String TXT = "txt";

    private final Predicate<String> isJavaFile= fileExtension -> 
        fileExtension.equals(JAVA) || fileExtension.equals(TXT);

    private Optional<String> getFileExtension(final String filename) {
        System.out.println(filename);
        return Optional.ofNullable(filename)
            .filter(f -> f.contains(DOT))
            .map(f -> f.substring(filename.lastIndexOf(DOT) + 1));
    }

	@Override
	public Optional<UMLPackageBuilder> walk(final File initialFile) {
        final Optional<String> fileExtension = getFileExtension(initialFile.getName());

        if(fileExtension.isEmpty()) {
            return Optional.empty();
        }

		System.out.println(initialFile.isFile() + " " +  initialFile.isDirectory());

		return initialFile.isFile() ? visitFile(initialFile) : visitDirectory(initialFile);
    }

	private Optional<UMLPackageBuilder> visitDirectory(final File directory) {
		System.out.println(Arrays.stream(directory.listFiles()).map(File::getName).collect(Collectors.joining(" ")));
		Arrays.stream(directory.listFiles()).forEach(this::walk);

		return Optional.empty();
	}	

	private Optional<UMLPackageBuilder> visitFile(final File file) {
        final Optional<String> fileExtension = getFileExtension(file.getName());
        final FileReader fileReader = new FileReaderImpl();

        if(fileExtension.isPresent() && !isJavaFile.test(fileExtension.get())) {
            return Optional.empty();
        }

        return fileReader.read(file);
	}
	
}
