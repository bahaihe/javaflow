package tryit;

import org.apache.commons.javaflow.Continuation;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class X {

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
