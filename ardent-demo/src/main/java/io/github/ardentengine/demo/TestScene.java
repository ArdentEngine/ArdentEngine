package io.github.ardentengine.demo;

import io.github.ardentengine.core.scene.Node;

public class TestScene extends Node {

    @Override
    protected void onEnter() {
        System.out.println("Enter test scene");
    }

    @Override
    protected void onExit() {
        System.out.println("Exit test scene");
    }
}
