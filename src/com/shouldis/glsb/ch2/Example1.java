package com.shouldis.glsb.ch2;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import com.shouldis.glsb.GLProgram;

public class Example1 extends GLProgram {

	private FloatBuffer red;

	@Override
	public void startup() {
		red = MemoryUtil.memAllocFloat(4);
		red.put(1.0f).put(0.0f).put(0.0f).put(1.0f);
		red.flip();
	}

	@Override
	public void render(float currentTime) {
		glClearBufferfv(GL_COLOR, 0, red);
	}

	public static void main(String[] args) {
		new Example1().run();
	}

}