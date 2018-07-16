package arbitrail.libra.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	public enum Props {
		balance_check_threshold, simulate, libra_frequency, pending_service_frequency
	}

	public static Properties loadProperties(String filename) throws IOException {
		Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(filename)) {
			properties.load(in);
		}
		return properties;
	}
	
}
