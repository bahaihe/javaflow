package org.apache.commons.javaflow;

import org.apache.commons.javaflow.bytecode.transformation.ResourceTransformer;
import org.junit.Test;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * @author kostas.kougios
 * Date: 13/11/15
 */
public class ContinuationClassFileTransformerTest {

	byte[] testClz = new byte[]{5, 6, 7};

	@Test
	public void transformsPrefixedPositive() throws IllegalClassFormatException {
		ResourceTransformer transformer = mock(ResourceTransformer.class);
		ContinuationClassFileTransformer t = ContinuationClassFileTransformer.withResourceTransformer(Collections.singletonList("tryit."), transformer);
		t.transform(null, "tryit.Main", null, null, testClz);
		verify(transformer).transform(testClz);
	}

	@Test
	public void transformsPrefixedNegative() throws IllegalClassFormatException {
		ResourceTransformer transformer = mock(ResourceTransformer.class);
		ContinuationClassFileTransformer t = ContinuationClassFileTransformer.withResourceTransformer(Collections.singletonList("tryit."), transformer);
		t.transform(null, "x.Main", null, null, testClz);
		verify(transformer, never()).transform(testClz);
	}

}