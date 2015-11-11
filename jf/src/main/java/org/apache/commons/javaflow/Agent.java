package org.apache.commons.javaflow;

import java.lang.instrument.Instrumentation;

/**
 * Continuation instrumentation agent
 *
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) {

		System.out.println("Continuations: Instrumenting");
		// registers the transformer
		inst.addTransformer(new ContinuationClassFileTransformer(), false);
	}
}
