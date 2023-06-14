package com.epam.rd.autotasks.suffixing.renamer;

import com.epam.rd.autotasks.suffixing.exception.FileRenamingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public abstract class FileRenamer {

	protected final Logger logger;
	protected final String suffix;

	protected FileRenamer(String suffix) {
		this.suffix = suffix;
		logger = Logger.getLogger(getClass().getName());
	}

	public final void rename(Path file) throws FileRenamingException {
		if (Files.exists(file)) {
			processFile(file, resolveFileName(file.getFileName().toString(), suffix));
		} else {
			throw new FileRenamingException("No such file: " + file);
		}
	}

	protected String resolveFileName(String name, String suffix) {
		int lastDotIndex = name.lastIndexOf(".");
		return name.substring(0, lastDotIndex) + suffix + name.substring(lastDotIndex);
	}

	protected abstract void processFile(Path file, String name) throws FileRenamingException;
}
