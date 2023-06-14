package com.epam.rd.autotasks.suffixing.renamer;

import com.epam.rd.autotasks.suffixing.exception.FileRenamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class CopyFileRenamer extends FileRenamer {

	CopyFileRenamer(String suffix) {
		super(suffix);
	}

	@Override
	protected void processFile(Path file, String name) throws FileRenamingException {
		try {
			Path copyTo = file.resolveSibling(name);
			Files.copy(file, copyTo, StandardCopyOption.REPLACE_EXISTING);
			logger.log(Level.INFO, "{0} -> {1}", new Object[]{file, copyTo});
		} catch (IOException e) {
			throw new FileRenamingException(e.getMessage());
		}
	}
}
