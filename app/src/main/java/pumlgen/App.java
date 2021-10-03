package pumlgen;

import java.io.File;

import pumlgen.files.impl.FileWalkerImpl;
import pumlgen.files.models.FileWalker;
import pumlgen.uml.builders.UMLAbstractBuilder;

public class App {

    public static void main(String[] args) {
        final FileWalker walker = new FileWalkerImpl();
        walker.walk(new File("app/src/main/resources/App.txt"))
            .map(UMLAbstractBuilder::build)
            .ifPresent(System.out::println);
    }
}
