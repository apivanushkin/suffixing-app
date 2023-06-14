package com.epam.rd.autotasks.suffixing;

import com.epam.rd.autotasks.suffixing.config.ApplicationConfig;
import com.epam.rd.autotasks.suffixing.config.ApplicationConfigLoader;
import com.epam.rd.autotasks.suffixing.exception.ConfigurationException;
import com.epam.rd.autotasks.suffixing.exception.FileRenamingException;
import com.epam.rd.autotasks.suffixing.renamer.FileRenamer;
import com.epam.rd.autotasks.suffixing.renamer.FileRenamerFactory;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		LOGGER.log(Level.INFO, "Started with arguments: {0}", Arrays.toString(args));
		try {
			ApplicationConfig config = ApplicationConfigLoader.loadFromFile(Path.of(args[0]));
			FileRenamer renamer = FileRenamerFactory.createFileRenamer(config.getMode(), config.getSuffix());
			for (Path file : config.getFiles()) {
				renameFile(renamer, file);
			}
		} catch (ConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Failed to configure application");
		}
	}

	private static void renameFile(FileRenamer renamer, Path file) {
		try {
			renamer.rename(file);
		} catch (FileRenamingException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
}
