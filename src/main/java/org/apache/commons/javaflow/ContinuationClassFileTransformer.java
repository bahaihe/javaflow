package org.apache.commons.javaflow;

import org.apache.commons.javaflow.bytecode.transformation.ResourceTransformer;
import org.apache.commons.javaflow.bytecode.transformation.asm.AsmClassTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Instrumentation transformer
 *
 * @author kostas.kougios
 * Date: 11/11/15
 */
public class ContinuationClassFileTransformer implements ClassFileTransformer {
	private ResourceTransformer transformer = new AsmClassTransformer();

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (!className.startsWith("java/lang/") && !className.startsWith("java/util/") && !className.startsWith("sun/")) {
			System.out.println("Instrumenting " + className);

			return transformer.transform(classfileBuffer);
		} else return classfileBuffer;
	}
}
