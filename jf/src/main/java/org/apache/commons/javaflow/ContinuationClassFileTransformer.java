package org.apache.commons.javaflow;

import org.apache.commons.javaflow.bytecode.transformation.ResourceTransformer;
import org.apache.commons.javaflow.bytecode.transformation.asm.AsmClassTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.List;

/**
 * Instrumentation transformer
 *
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class ContinuationClassFileTransformer implements ClassFileTransformer {
	private final List<String> includePrefixes;
	private final ResourceTransformer transformer;

	public static ContinuationClassFileTransformer asmTransformer(List<String> includePrefixes) {
		return new ContinuationClassFileTransformer(includePrefixes, new AsmClassTransformer());
	}

	public static ContinuationClassFileTransformer withResourceTransformer(List<String> includePrefixes, ResourceTransformer transformer) {
		return new ContinuationClassFileTransformer(includePrefixes, transformer);
	}

	private ContinuationClassFileTransformer(List<String> includePrefixes, ResourceTransformer transformer) {
		this.includePrefixes = includePrefixes;
		this.transformer = transformer;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (className == null) return null;
		try {
			if (shouldTransform(className)) {
				System.out.println("Instrumenting " + className);

				// we need a dummy classloader to load classes during instrumentation because
				// if a class is loaded during javaflow instrumentation, the java agent is not called
				// to instrument it.
				URLClassLoader parent = (URLClassLoader) Thread.currentThread().getContextClassLoader();
				URLClassLoader instClassLoader = new URLClassLoader(parent.getURLs(), null);
				Thread.currentThread().setContextClassLoader(instClassLoader);

				try {
					return transformer.transform(classfileBuffer);
				} finally {
					Thread.currentThread().setContextClassLoader(parent);
				}
			} else return null;
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean shouldTransform(String className) {
		String cn = className.replace('/', '.');
		for (String s : includePrefixes) {
			if (cn.startsWith(s)) return true;
		}
		return false;
	}
}
