package io.github.ardentengine.core.scene;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

public class TestNode {

    @Test
    public void testIsInsideScene() {
        var sceneTree = new SceneTree();
        var node = new Node();
        Assertions.assertFalse(node.isInsideScene());
        Assertions.assertNull(node.sceneTree());
        node.enterScene(sceneTree);
        Assertions.assertTrue(node.isInsideScene());
        Assertions.assertEquals(sceneTree, node.sceneTree());
    }

    @Test
    public void testAddChild() {
        var node = new Node();
        var child = new Node();
        Assertions.assertFalse(node.children().contains(child));
        Assertions.assertNull(child.parent());
        node.addChild(child);
        Assertions.assertTrue(node.children().contains(child));
        Assertions.assertEquals(node, child.parent());
    }

    @Test
    public void testAddChildEnterScene() {
        var sceneTree = new SceneTree();
        var node = new Node();
        var child = new Node();
        node.enterScene(sceneTree);
        Assertions.assertTrue(node.isInsideScene());
        Assertions.assertFalse(child.isInsideScene());
        node.addChild(child);
        Assertions.assertTrue(child.isInsideScene());
    }

    @Test
    public void testAddNullChild() {
        Assertions.assertThrows(NullPointerException.class, () -> new Node().addChild(null));
    }

    @Test
    public void testAddChildSameNode() {
        var node = new Node();
        Assertions.assertThrows(IllegalArgumentException.class, () -> node.addChild(node));
    }

    @Test
    public void testAddChildAlreadyHasParent() {
        var parent1 = new Node();
        var parent2 = new Node();
        var child = new Node();
        parent1.addChild(child);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parent2.addChild(child));
    }

    @Test
    public void testAddRootAsChild() {
        var sceneTree = new SceneTree();
        var root = new Node();
        var node = new Node();
        root.addChild(node);
        root.enterScene(sceneTree);
        Assertions.assertThrows(IllegalArgumentException.class, () -> node.addChild(root));
    }

    @Test
    public void testAddChildIsAncestor() {
        var root = new Node();
        var node1 = new Node();
        var node2 = new Node();
        node1.addChild(node2);
        root.addChild(node1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> node2.addChild(root));
    }

    @Test
    public void testChildCount() {
        var parent = new Node();
        parent.addChild(new Node());
        parent.addChild(new Node());
        parent.addChild(new Node());
        Assertions.assertEquals(3, parent.childCount());
        parent.addChild(new Node());
        Assertions.assertEquals(4, parent.childCount());
    }

    @Test
    public void testAddChildAtIndex() {
        var parent = new Node();
        var child1 = new Node();
        var child2 = new Node();
        parent.addChild(child1);
        parent.addChild(child2);
        Assertions.assertEquals(List.of(child1, child2), parent.children());
        var child3 = new Node();
        parent.addChild(child3, 1);
        Assertions.assertEquals(List.of(child1, child3, child2), parent.children());
    }

    @Test
    public void testAddChildAtNegativeIndex() {
        var parent = new Node();
        var child1 = new Node();
        var child2 = new Node();
        var child3 = new Node();
        var child4 = new Node();
        parent.addChild(child1);
        parent.addChild(child2);
        parent.addChild(child3);
        parent.addChild(child4);
        Assertions.assertEquals(List.of(child1, child2, child3, child4), parent.children());
        var child5 = new Node();
        parent.addChild(child5, -2);
        Assertions.assertEquals(List.of(child1, child2, child3, child5, child4), parent.children());
    }

    @Test
    public void testAddChildAtIndexOutOfBounds() {
        var parent = new Node();
        parent.addChild(new Node());
        parent.addChild(new Node());
        parent.addChild(new Node());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> parent.addChild(new Node(), 4));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> parent.addChild(new Node(), -5));
    }

    @Test
    public void testRemoveFromScene() {
        var sceneTree = new SceneTree();
        var root = new Node();
        var node = new Node();
        root.addChild(node);
        root.enterScene(sceneTree);
        Assertions.assertTrue(node.isInsideScene());
        Assertions.assertEquals(root, node.parent());
        Assertions.assertTrue(root.children().contains(node));
        node.removeFromScene();
        Assertions.assertFalse(node.isInsideScene());
        Assertions.assertNull(node.parent());
        Assertions.assertTrue(root.children().isEmpty());
    }

    @Test
    public void testSetParent() {
        var root = new Node();
        var parent1 = new Node();
        var parent2 = new Node();
        root.addChild(parent1);
        root.addChild(parent2);
        var node = new Node();
        parent1.addChild(node);
        Assertions.assertEquals(parent1, node.parent());
        Assertions.assertTrue(parent1.children().contains(node));
        Assertions.assertFalse(parent2.children().contains(node));
        node.setParent(parent2);
        Assertions.assertEquals(parent2, node.parent());
        Assertions.assertFalse(parent1.children().contains(node));
        Assertions.assertTrue(parent2.children().contains(node));
    }

    @Test
    public void setGetChildAtIndex() {
        var parent = new Node();
        var child0 = new Node();
        var child1 = new Node();
        var child2 = new Node();
        parent.addChild(child0);
        parent.addChild(child1);
        parent.addChild(child2);
        Assertions.assertEquals(child0, parent.getChild(0));
        Assertions.assertEquals(child1, parent.getChild(1));
        Assertions.assertEquals(child2, parent.getChild(2));
    }

    @Test
    public void testGetChildAtIndexOutOfBounds() {
        var parent = new Node();
        parent.addChild(new Node());
        parent.addChild(new Node());
        parent.addChild(new Node());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> parent.getChild(3));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> parent.getChild(-4));
    }

    @Test
    public void testGetChildAtNegativeIndex() {
        var parent = new Node();
        var child0 = new Node();
        var child1 = new Node();
        var child2 = new Node();
        parent.addChild(child0);
        parent.addChild(child1);
        parent.addChild(child2);
        Assertions.assertEquals(child2, parent.getChild(-1));
        Assertions.assertEquals(child1, parent.getChild(-2));
        Assertions.assertEquals(child0, parent.getChild(-3));
    }

    private static class MyNode1 extends Node {

    }

    private static class MyNode2 extends Node {

    }

    private static class MyNode3 extends Node {

    }

    @Test
    public void testGetChildOfType() {
        var root = new Node();
        var node1 = new Node();
        var myNode1 = new MyNode1();
        var myNode2 = new MyNode2();
        var myNode1b = new MyNode1();
        root.addChild(node1);
        root.addChild(myNode1);
        root.addChild(myNode2);
        root.addChild(myNode1b);
        Assertions.assertEquals(myNode1, root.getChild(MyNode1.class));
        Assertions.assertEquals(myNode2, root.getChild(MyNode2.class));
        Assertions.assertNull(root.getChildOrNull(MyNode3.class));
        Assertions.assertThrows(NoSuchElementException.class, () -> root.getChild(MyNode3.class));
    }

    @Test
    public void testGetChildrenOfType() {
        var root = new Node();
        var node1 = new Node();
        var myNode1 = new MyNode1();
        var myNode2 = new MyNode2();
        var myNode1b = new MyNode1();
        root.addChild(node1);
        root.addChild(myNode1);
        root.addChild(myNode2);
        root.addChild(myNode1b);
        Assertions.assertEquals(List.of(myNode1, myNode1b), root.getChildren(MyNode1.class));
        Assertions.assertEquals(List.of(myNode2), root.getChildren(MyNode2.class));
        Assertions.assertTrue(root.getChildren(MyNode3.class).isEmpty());
    }

    @Test
    public void testFindChild() {
        var node = new Node();
        var child1 = new Node();
        child1.setName("Child1");
        node.addChild(child1);
        var child2 = new Node();
        child2.setName("Child2");
        node.addChild(child2);
        Assertions.assertEquals(child1, node.findChild(child -> child.name().equals("Child1")));
        Assertions.assertEquals(child2, node.findChild(child -> child.name().equals("Child2")));
        Assertions.assertNull(node.findChild(child -> child.name().equals("Something")));
    }

    @Test
    public void testFindChildren() {
        var node = new Node();
        var child1 = new Node();
        var child2 = new Node();
        var child3 = new Node();
        var child4 = new Node();
        var child5 = new Node();
        child1.setName("Result");
        child2.setName("Not");
        child3.setName("Result");
        child4.setName("Not");
        child5.setName("Result");
        node.addChild(child1);
        node.addChild(child2);
        node.addChild(child3);
        node.addChild(child4);
        node.addChild(child5);
        Assertions.assertEquals(List.of(child1, child3, child5), node.findChildren(child -> child.name().equals("Result")));
    }

    @Test
    public void testGetSiblingIndex() {
        var node = new Node();
        var child0 = new Node();
        var child1 = new Node();
        var child2 = new Node();
        var child3 = new Node();
        node.addChild(child0);
        node.addChild(child1);
        node.addChild(child2);
        node.addChild(child3);
        Assertions.assertEquals(0, child0.siblingIndex());
        Assertions.assertEquals(1, child1.siblingIndex());
        Assertions.assertEquals(2, child2.siblingIndex());
        Assertions.assertEquals(3, child3.siblingIndex());
        Assertions.assertEquals(-1, node.siblingIndex());
    }

    @Test
    public void testSetSiblingIndex() {
        var node = new Node();
        var child0 = new Node();
        var child1 = new Node();
        var child2 = new Node();
        node.addChild(child0);
        node.addChild(child1);
        node.addChild(child2);
        Assertions.assertEquals(List.of(child0, child1, child2), node.children());
        Assertions.assertTrue(child1.setSiblingIndex(0));
        Assertions.assertEquals(List.of(child1, child0, child2), node.children());
        Assertions.assertTrue(child2.setSiblingIndex(1));
        Assertions.assertEquals(List.of(child1, child2, child0), node.children());
        Assertions.assertFalse(node.setSiblingIndex(42));
    }

    @Test
    public void testSetSiblingIndexNegative() {
        var node = new Node();
        var child0 = new Node();
        var child1 = new Node();
        var child2 = new Node();
        node.addChild(child0);
        node.addChild(child1);
        node.addChild(child2);
        Assertions.assertEquals(List.of(child0, child1, child2), node.children());
        Assertions.assertTrue(child1.setSiblingIndex(-1));
        Assertions.assertEquals(List.of(child0, child2, child1), node.children());
        Assertions.assertTrue(child2.setSiblingIndex(-3));
        Assertions.assertEquals(List.of(child2, child0, child1), node.children());
    }

    @Test
    public void testSetSiblingIndexOutOfBounds() {
        var node = new Node();
        var child = new Node();
        node.addChild(child);
        node.addChild(new Node());
        node.addChild(new Node());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> child.setSiblingIndex(3));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> child.setSiblingIndex(-4));
    }

    @Test
    public void testFindNode() {
        var root = new Node();
        var node1 = new Node();
        node1.setName("Node1");
        var node2 = new Node();
        node2.setName("Node2");
        var node1a = new Node();
        node1a.setName("Result");
        var node1b = new Node();
        node1b.setName("Node1b");
        var node2a = new Node();
        node2a.setName("Node2a");
        var node2b = new Node();
        node2b.setName("Result");
        node1.addChild(node1a);
        node1.addChild(node1b);
        node2.addChild(node2a);
        node2.addChild(node2b);
        root.addChild(node1);
        root.addChild(node2);
        Assertions.assertEquals(node1a, root.findNode(node -> node.name().equals("Result")));
        Assertions.assertNull(root.findNode(node -> node.name().equals("Node3")));
    }

    @Test
    public void testFindNodes() {
        var root = new Node();
        var node1 = new Node();
        node1.setName("Node1");
        var node2 = new Node();
        node2.setName("Node2");
        var node1a = new Node();
        node1a.setName("Result");
        var node1b = new Node();
        node1b.setName("Node1b");
        var node2a = new Node();
        node2a.setName("Node2a");
        var node2b = new Node();
        node2b.setName("Result");
        node1.addChild(node1a);
        node1.addChild(node1b);
        node2.addChild(node2a);
        node2.addChild(node2b);
        root.addChild(node1);
        root.addChild(node2);
        Assertions.assertEquals(List.of(node1a, node2b), root.findNodes(node -> node.name().equals("Result")));
        Assertions.assertTrue(root.findNodes(node -> node.name().equals("Node3")).isEmpty());
    }
}
