package com.shouldis.glsb.ch2;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL45.*;

import org.lwjgl.system.MemoryUtil;

import com.shouldis.glsb.GLProgram;

public class Example2 extends GLProgram {

	private FloatBuffer color;

	@Override
	public void startup() {
		color = MemoryUtil.memAllocFloat(4);
		color.put(new float[3]).put(1.0f);
		color.flip();
	}

	@Override
	public void update(float currentTime) {
		color.put(0, (float) (Math.sin(currentTime) * 0.5f + 0.5f));
		color.put(1, (float) (Math.cos(currentTime) * 0.5f + 0.5f));
	}

	@Override
	public void render(float currentTime) {
		glClearBufferfv(GL_COLOR, 0, color);
	}

	public static void main(String[] args) {
		new Example2().run();
	}

}
