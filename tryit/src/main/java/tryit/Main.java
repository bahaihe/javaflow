package tryit;

import org.apache.commons.javaflow.Continuation;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("---> Should instrument these classes");

		AClz aClz = new AClz();
		AnExample pTarget = new AnExample();
		System.out.println("<--- Should instrument these classes");
		Continuation c = Continuation.startWith(aClz);
		while (c != null) {
			System.out.println("-----------------------");
			c = Continuation.continueWith(c);
		}
		System.out.println("returned a continuation");
	}
}
