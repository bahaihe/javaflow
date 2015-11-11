import org.apache.commons.javaflow.Continuation;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class X {
	static class MyRunnable implements Runnable {
		public void run() {
			System.out.println("started!");
			for (int i = 0; i < 10; i++)
				echo(i);
		}

		private void echo(int x) {
			System.out.println(x);
			Continuation.suspend();
		}
	}

	public static void main(String[] args) {
		System.out.println("OK");

		Continuation c = Continuation.startWith(new MyRunnable());
		while (c != null) {
			System.out.println("-----------------------");
			c = Continuation.continueWith(c);
		}
		System.out.println("returned a continuation");
	}
}
