package org.apache.commons.javaflow;

import java.lang.instrument.Instrumentation;
import java.net.URLClassLoader;

/**
 * Continuation instrumentation agent
 *
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		System.out.println("Continuations: Instrumenting");

		URLClassLoader parent = (URLClassLoader) Thread.currentThread().getContextClassLoader();

		// we need a dummy classloader to load classes during instrumentation because
		// if a class is loaded during javaflow instrumentation, the java agent is not called
		// to instrument it.
		URLClassLoader instClassLoader = new URLClassLoader(parent.getURLs(), null);
		Thread.currentThread().setContextClassLoader(instClassLoader);
		// registers the transformer
		inst.addTransformer(new ContinuationClassFileTransformer(), true);
	}
}
