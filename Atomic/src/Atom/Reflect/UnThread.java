/*******************************************************************************
 * Copyright 2021 Itzbenz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package Atom.Reflect;

import org.jetbrains.annotations.NotNull;

public class UnThread extends Thread {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}catch (InterruptedException ignored) {
		
		}
	}
	
	public static void sleep(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		}catch (InterruptedException ignored) {
		
		}
	}
	
	public synchronized void start() {
		Thread.currentThread().start();
	}
	
	
	public void run() {
		Thread.currentThread().run();
	}
	
	
	public void interrupt() {
		Thread.currentThread().interrupt();
	}
	
	
	public boolean isInterrupted() {
		return Thread.currentThread().isInterrupted();
	}
	
	
	public int countStackFrames() {
		return Thread.currentThread().countStackFrames();
	}
	
	
	public String toString() {
		return Thread.currentThread().toString();
	}
	
	
	public ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	
	public void setContextClassLoader(ClassLoader cl) {
		Thread.currentThread().setContextClassLoader(cl);
	}
	
	@NotNull
	
	public StackTraceElement[] getStackTrace() {
		return Thread.currentThread().getStackTrace();
	}
	
	
	public long getId() {
		return Thread.currentThread().getId();
	}
	
	@NotNull
	
	public Thread.State getState() {
		return Thread.currentThread().getState();
	}
	
	
	public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return Thread.currentThread().getUncaughtExceptionHandler();
	}
	
	
	public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh) {
		Thread.currentThread().setUncaughtExceptionHandler(eh);
	}
}
