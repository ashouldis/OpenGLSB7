package com.shouldis.glsb.ch2;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import com.shouldis.glsb.GLProgram;

public class Example3 extends GLProgram {

	private IntBuffer vertexArrays;

	private FloatBuffer color;

	private int program;

	@Override
	public void startup() {
		program = buildProgram();
		vertexArrays = MemoryUtil.memAllocInt(1);
		glCreateVertexArrays(vertexArrays);
		glBindVertexArray(vertexArrays.get(0));

		color = MemoryUtil.memAllocFloat(4);
		color.put(new float[3]).put(1.0f);
		color.flip();
	}

	@Override
	public void shutdown() {
		glDeleteVertexArrays(vertexArrays);
		glDeleteProgram(program);
		glDeleteVertexArrays(vertexArrays);
	}

	private int buildProgram() {
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexShaderSource = readFile("res\\ch2\\e3.vs");
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);

		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentShaderSource = readFile("res\\ch2\\e3.fs");
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);

		int program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glLinkProgram(program);

		glDeleteShader(vertexShader);
		glDeleteBuffers(fragmentShader);

		return program;
	}

	@Override
	public void update(float currentTime) {
		color.put(0, (float) (Math.sin(currentTime) * 0.5f + 0.5f));
		color.put(1, (float) (Math.cos(currentTime) * 0.5f + 0.5f));

		glPointSize((float) Math.abs(Math.sin(currentTime)) * 20.0f);
	}

	@Override
	public void render(float currentTime) {
		glClearBufferfv(GL_COLOR, 0, color);

		glUseProgram(program);

		glDrawArrays(GL_POINTS, 0, 1);
	}

	public static void main(String[] args) {
		new Example3().run();
	}

}