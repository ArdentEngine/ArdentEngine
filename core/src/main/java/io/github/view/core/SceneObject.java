package io.github.view.core;

import java.util.HashMap;
import java.util.function.Function;

public final class SceneObject {

	private final HashMap<Class<? extends Script>, Script> scripts = new HashMap<>();

	public <S extends Script> S addScript(Function<SceneObject, S> constructor) {
		S script = constructor.apply(this);
		// TODO: Avoid inheritance causing duplicate scripts
		this.scripts.put(script.getClass(), script);
		return script;
	}

	public <S extends Script> S getScript(Class<S> type) {
		return type.cast(this.scripts.get(type));
	}

	public void process() {
		this.scripts.values().removeIf(Script::wasRemoved);
		this.scripts.values().forEach(script -> {
			switch(script.getProcessState()) {
				case NEW -> script.onStart();
				case READY -> script.onUpdate();
				case TO_BE_REMOVED -> script.onExit();
			}
		});
	}

	public void render() {
		this.scripts.values().removeIf(Script::wasRemoved);
		this.scripts.values().forEach(script -> {
			switch(script.getRenderingState()) {
				case NEW -> script.prepareRendering();
				case READY -> script.render();
				case TO_BE_REMOVED -> script.exitRendering();
			}
		});
	}
}