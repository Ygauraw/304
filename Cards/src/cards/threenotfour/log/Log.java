package cards.threenotfour.log;

public final class Log {

	private static int log_level = 0;

	public static void setLevel(int level) {

		log_level = level;
	}

	public static void i(String msg) {
		if (log_level > 0) {
			System.out.println(msg);
		}
	}

	public static void d(String msg) {
		if (log_level >= 0) {
			System.out.println(msg);
		}
	}

	public static void e(String msg) {
		System.err.println(msg);
	}

}
