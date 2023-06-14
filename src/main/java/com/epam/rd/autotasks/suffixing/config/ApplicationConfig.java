package com.epam.rd.autotasks.suffixing.config;

import java.nio.file.Path;
import java.util.Set;

public interface ApplicationConfig {

	Mode getMode();

	String getSuffix();

	Set<Path> getFiles();
}
