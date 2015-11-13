package org.apache.commons.javaflow;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URLClassLoader;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Continuation instrumentation agent
 *
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

		URLClassLoader parent = (URLClassLoader) Thread.currentThread().getContextClassLoader();

		File workDir = new File("continuations-conf");
		System.out.println("Continuations, instrumenting. Work dir is " + workDir.getAbsolutePath());

		File instr = new File(workDir, "instrument.prefixes");
		List<String> packages = ((List<String>) FileUtils.readLines(instr)).stream()
				.map(String::trim)
				.filter(s -> s.startsWith("#"))
				.filter(String::isEmpty)
				.collect(toList());
		// we need a dummy classloader to load classes during instrumentation because
		// if a class is loaded during javaflow instrumentation, the java agent is not called
		// to instrument it.
		URLClassLoader instClassLoader = new URLClassLoader(parent.getURLs(), null);
		Thread.currentThread().setContextClassLoader(instClassLoader);
		// registers the transformer
		inst.addTransformer(ContinuationClassFileTransformer.asmTransformer(packages), true);
	}
}
