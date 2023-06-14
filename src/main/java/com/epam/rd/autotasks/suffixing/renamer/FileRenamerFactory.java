package com.epam.rd.autotasks.suffixing.renamer;

import com.epam.rd.autotasks.suffixing.config.Mode;
import java.util.Objects;

public class FileRenamerFactory {

	private FileRenamerFactory() {
	}

	public static FileRenamer createFileRenamer(Mode mode, String suffix) {
		if (Objects.nonNull(suffix)) {
			if (mode == Mode.COPY) {
				return new CopyFileRenamer(suffix);
			} else if (mode == Mode.MOVE) {
				return new ReplaceFileRenamer(suffix);
			} else {
				throw new IllegalArgumentException("unsupported mode value: " + mode);
			}
		} else {
			throw new IllegalArgumentException("suffix can not be null");
		}
	}
}