package tryit;

import org.apache.commons.javaflow.Continuation;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class AClz implements Runnable {
	public void run() {
		System.out.println("AClz : started!");
		for (int i = 0; i < 10; i++)
			echo(i);
	}

	private void echo(int x) {
		System.out.println(x);
		Continuation.suspend();
	}
}
