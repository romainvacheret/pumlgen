package pumlgen.files.models;

import java.io.File;
import java.util.Optional;

import pumlgen.uml.builders.UMLPackageBuilder;

@FunctionalInterface
public interface FileWalker {
    Optional<UMLPackageBuilder> walk(final File initialFile);
}
