package tryit;

/**
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class DoRun {

	public void run() {
		System.out.println("---> Should instrument these classes");
		C1 c1 = new C1();
		C2 c2 = new C2();
		System.out.println("<--- Should instrument these classes");
		doCont(c1);
		doCont(c2);
	}

	private void doCont(Runnable c1) {
		System.out.println("doCont " + c1);
//		Continuation c = Continuation.startWith(c1);
//		while (c != null) {
//			System.out.println("-----------------------");
//			c = Continuation.continueWith(c);
//		}
	}
}