package org.apache.commons.javaflow;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
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

		File workDir = new File("continuations-conf");
		System.out.println("Continuations, instrumenting. Work dir is " + workDir.getAbsolutePath());

		File instr = new File(workDir, "instrument.prefixes");
		List<String> prefixes = ((List<String>) FileUtils.readLines(instr)).stream()
				.map(String::trim)
				.filter(s -> !s.startsWith("#"))
				.filter(s -> !s.isEmpty())
				.collect(toList());
		System.out.println("Instrumenting prefixes : " + prefixes);
		// registers the transformer
		inst.addTransformer(ContinuationClassFileTransformer.asmTransformer(prefixes), true);
	}
}
