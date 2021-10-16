package pumlgen.files.models;

@FunctionalInterface
public interface FileWriter {
	public void write(String path, String text);
	
}
