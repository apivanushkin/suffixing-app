package com.epam.rd.autotasks.suffixing.renamer;

import com.epam.rd.autotasks.suffixing.exception.FileRenamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class ReplaceFileRenamer extends FileRenamer {

	protected ReplaceFileRenamer(String suffix) {
		super(suffix);
	}

	@Override
	protected void processFile(Path file, String name) throws FileRenamingException {
		try {
			Path moveTo = file.resolveSibling(name);
			Files.move(file, moveTo, StandardCopyOption.REPLACE_EXISTING);
			logger.log(Level.INFO, "{0} => {1}", new Object[]{file, moveTo});
		} catch (IOException e) {
			throw new FileRenamingException(e.getMessage());
		}
	}
}
