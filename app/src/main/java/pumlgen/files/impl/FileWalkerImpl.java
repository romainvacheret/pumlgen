package pumlgen.files.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
        return Optional.ofNullable(filename)
            .filter(f -> f.contains(DOT))
            .map(f -> f.substring(filename.lastIndexOf(DOT) + 1));
    }

	@Override
	public Optional<UMLPackageBuilder> walk(final File initialFile) {
        final Optional<String> fileExtension = getFileExtension(initialFile.getName());

        if(initialFile.isFile() && fileExtension.isEmpty()) {
            return Optional.empty();
        }

		return initialFile.isFile() ? visitFile(initialFile) : visitDirectory(initialFile);
    }

	private Optional<UMLPackageBuilder> visitDirectory(final File directory) {
        final Stream<UMLPackageBuilder> builderStream = Arrays
            .stream(directory.listFiles()).map(this::walk).flatMap(Optional::stream); 
        final Iterator<UMLPackageBuilder> builderIterator = builderStream.iterator();
        final UMLPackageBuilder firstBuilder = builderIterator.next();

        builderIterator.forEachRemaining(firstBuilder::merge);
        

		return Optional.of(firstBuilder);
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
