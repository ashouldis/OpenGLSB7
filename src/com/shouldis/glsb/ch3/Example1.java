package com.shouldis.glsb.ch3;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import com.shouldis.glsb.GLProgram;

public class Example1 extends GLProgram {

	private int program;

	private FloatBuffer color;

	private FloatBuffer attrib;

	private IntBuffer vertexArrays;

	@Override
	public void startup() {
		program = buildProgram();
		vertexArrays = MemoryUtil.memAllocInt(1);
		glCreateVertexArrays(vertexArrays);
		glBindVertexArray(vertexArrays.get(0));

		color = MemoryUtil.memAllocFloat(4);
		color.put(0.0f).put(0.2f).put(0.0f).put(1.0f);
		color.flip();

		attrib = MemoryUtil.memAllocFloat(4);
	}

	@Override
	public void shutdown() {
		glDeleteVertexArrays(vertexArrays);
		glDeleteProgram(program);
	}

	private int buildProgram() {
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexShaderSource = readFile("res\\ch3\\e1.vs");
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
		glDeleteShader(fragmentShader);

		return program;
	}

	@Override
	public void update(float currentTime) {
		color.put(0, (float) (Math.sin(currentTime) * 0.5f + 0.5f));
		color.put(1, (float) (Math.cos(currentTime) * 0.5f + 0.5f));

		attrib.put(0, (float) (Math.sin(currentTime) * 0.5f));
		attrib.put(1, (float) (Math.cos(currentTime) * 0.6f));
	}

	@Override
	public void render(float currentTime) {
		glVertexAttrib4fv(0, attrib);

		glClearBufferfv(GL_COLOR, 0, color);

		glUseProgram(program);

		glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	public static void main(String[] args) {
		new Example1().run();
	}

}
