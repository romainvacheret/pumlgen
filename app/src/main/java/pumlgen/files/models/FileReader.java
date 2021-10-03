package pumlgen.files.models;

import java.io.File;
import java.util.Optional;

import pumlgen.uml.builders.UMLPackageBuilder;

@FunctionalInterface
public interface FileReader {
	Optional<UMLPackageBuilder> read(final File file);
}
