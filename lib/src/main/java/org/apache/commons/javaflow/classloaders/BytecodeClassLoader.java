/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.javaflow.classloaders;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class BytecodeClassLoader extends ClassLoader {

	class NameClassAdapter extends ClassVisitor {
		private String className;

		public NameClassAdapter() {
			super(Opcodes.ASM5);
		}

		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			className = name;
		}

		public String getName() {
			return className;
		}
	}

	public Class<?> loadClass(final byte[] bytecode) {
		final NameClassAdapter nameClassAdapter = new NameClassAdapter();

		new ClassReader(bytecode).accept(nameClassAdapter, 0);

		final String name = nameClassAdapter.getName().replace('/', '.');

		// System.out.println("loading class " + name);

		final Class<?> clazz = defineClass(name, bytecode, 0, bytecode.length);

		return clazz;
	}
}