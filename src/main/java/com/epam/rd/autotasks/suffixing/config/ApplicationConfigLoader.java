package com.epam.rd.autotasks.suffixing.config;

import com.epam.rd.autotasks.suffixing.exception.ConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ApplicationConfigLoader {

	private static final String MODE = "mode";
	private static final String SUFFIX = "suffix";
	private static final String FILES = "files";
	private static final Logger LOGGER = Logger.getLogger(ApplicationConfigLoader.class.getName());

	private ApplicationConfigLoader() {
	}

	public static ApplicationConfig loadFromFile(Path file) throws ConfigurationException {
		try {
			Properties properties = loadPropertiesFromFile(file);
			validate(properties);
			return createApplicationConfig(properties.getProperty(MODE), properties.getProperty(SUFFIX),
				properties.getProperty(FILES).split(":"));
		} catch (IOException e) {
			throw new ConfigurationException("Failed to load configuration file from " + file);
		}
	}

	private static Properties loadPropertiesFromFile(Path file) throws IOException {
		Properties properties = new Properties();
		try (InputStream in = new FileInputStream(file.toString())) {
			properties.load(in);
			return properties;
		}
	}

	private static void validate(Properties properties) throws ConfigurationException {
		boolean valid = false;
		if (Arrays.stream(Mode.values()).noneMatch(value ->
			value.toString().equalsIgnoreCase(properties.getProperty(MODE)))) {
			LOGGER.log(Level.SEVERE, "Mode is not recognized: {0}", properties.get(MODE));
		} else if (Objects.isNull(properties.getProperty(SUFFIX))) {
			LOGGER.log(Level.SEVERE, "No suffix is configured");
		} else if (Objects.isNull(properties.getProperty(FILES)) || properties.getProperty(FILES).isEmpty()) {
			LOGGER.log(Level.WARNING, "No files are configured to be copied/moved");
		} else {
			valid = true;
		}

		if (!valid) {
			throw new ConfigurationException("Invalid configuration file");
		}
	}

	private static ApplicationConfig createApplicationConfig(String mode, String suffix, String[] files) {
		return new ApplicationConfig() {
			@Override
			public Mode getMode() {
				return Mode.valueOf(mode.toUpperCase());
			}

			@Override
			public String getSuffix() {
				return suffix;
			}

			@Override
			public Set<Path> getFiles() {
				return Arrays.stream(files).map(Path::of).collect(Collectors.toSet());
			}
		};
	}
}
