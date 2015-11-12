package tryit;

import org.apache.commons.javaflow.Continuation;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class DoRun {

	public void run() {
		System.out.println("---> Should instrument these classes");
		C1 c1 = new C1();
		System.out.println("<--- Should instrument these classes");
		doCont(c1);
	}

	private void doCont(Runnable r) {
		Continuation c = Continuation.startWith(r);
		while (c != null) {
			System.out.println("-----------------------");
			c = Continuation.continueWith(c);
		}
	}
}
