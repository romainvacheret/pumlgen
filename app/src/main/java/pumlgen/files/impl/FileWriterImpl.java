package pumlgen.files.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pumlgen.files.models.FileWriter;

public class FileWriterImpl implements FileWriter {

	@Override
	public void write(String path, String text) {
		try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path));) {
			writer.append(text);
		} catch(IOException e) {
			e.printStackTrace();
		} 
	}
}
