package pumlgen;

import java.io.File;
import java.util.function.Consumer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import pumlgen.files.impl.FileWalkerImpl;
import pumlgen.files.impl.FileWriterImpl;
import pumlgen.files.models.FileWalker;
import pumlgen.files.models.FileWriter;
import pumlgen.uml.builders.UMLAbstractBuilder;

public class App {

    @Parameter(names = {"--path", "-p"})
    private String path;

    @Parameter(names = {"--example", "-e"})
    private boolean isExampleSeleted = false;

    @Parameter(names = {"--file", "-f"})
    private String filepath = "";

    private static final String EXAMPLE = "src/main/resources";
    public static void main(String... argv) {
        final App app = new App();

        JCommander.newBuilder()
            .addObject(app)
            .build()
            .parse(argv);
        app.run();
    }

    private Consumer<String> printOrWrite = text -> {
        if(filepath.equals("")) {
            System.out.println(text);
        } else {
            FileWriter writer = new FileWriterImpl();
            writer.write(filepath, text);
        }
    };

    public void run() {
        final FileWalker walker = new FileWalkerImpl();
        final String finalPath = isExampleSeleted ? EXAMPLE : path;

        walker.walk(new File(finalPath))
            .map(UMLAbstractBuilder::build)
            .ifPresent(printOrWrite);
    }
}
