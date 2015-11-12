package org.apache.commons.javaflow;

import java.lang.instrument.ClassFileTransformer;
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
		URLClassLoader instClassLoader = new URLClassLoader(parent.getURLs(), null);
		Thread.currentThread().setContextClassLoader(instClassLoader);
		Class<?> trClz = instClassLoader.loadClass("org.apache.commons.javaflow.ContinuationClassFileTransformer");
		ClassFileTransformer transformer = (ClassFileTransformer) trClz.newInstance();
		// registers the transformer
		inst.addTransformer(transformer, true);
	}
}
