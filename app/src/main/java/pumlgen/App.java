package pumlgen;

import java.io.File;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import pumlgen.files.impl.FileWalkerImpl;
import pumlgen.files.models.FileWalker;
import pumlgen.uml.builders.UMLAbstractBuilder;

public class App {

    @Parameter(names = {"--path", "-p"})
    private String path;

    @Parameter(names = {"--example", "-e"})
    private boolean isExampleSeleted = false;

    private static final String EXAMPLE = "src/main/resources";
    public static void main(String... argv) {
        final App app = new App();

        JCommander.newBuilder()
            .addObject(app)
            .build()
            .parse(argv);
        app.run();
    }

    public void run() {
        final FileWalker walker = new FileWalkerImpl();
        final String finalPath = isExampleSeleted ? EXAMPLE : path;

        walker.walk(new File(finalPath))
            .map(UMLAbstractBuilder::build)
            .ifPresent(System.out::println);
    }
}
