package com.shouldis.glsb;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public abstract class GLProgram implements Runnable {

	protected final long window;

	private float time;

	static {
		if (!glfwInit()) {
			throw new IllegalStateException("GLFW initialization failed");
		}
		GLFWErrorCallback.createPrint(System.err).set();
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	}

	protected GLProgram() {
		window = glfwCreateWindow(600, 600, getClass().getCanonicalName(), 0, 0);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
	}

	public abstract void startup();

	public abstract void render(final float currentTime);

	public void update(final float currentTime) {
		// Omit by default
	}

	public void shutdown() {
		// Omit by default
	}

	@Override
	public void run() {
		startup();
		long time0 = System.nanoTime();
		long time1;
		glfwSwapInterval(1);
		glfwShowWindow(window);
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			time1 = System.nanoTime();
			time += (time1 - time0) / 1E9f;
			time0 = time1;
			update(time);
			render(time);
			glfwSwapBuffers(window);
		}
		glfwDestroyWindow(window);
		glfwTerminate();
		shutdown();
		glfwSetErrorCallback(null).free();
	}

	public static String readFile(final String path) {
		String string = null;
		try {
			string = Files.readString(Path.of(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

}