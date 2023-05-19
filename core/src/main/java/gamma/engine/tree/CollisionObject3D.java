package gamma.engine.tree;

import gamma.engine.annotations.EditorRange;
import gamma.engine.annotations.EditorVariable;
import gamma.engine.physics.Collision3D;
import gamma.engine.physics.PhysicsSystem;
import gamma.engine.physics.Projection;
import gamma.engine.rendering.CubeMesh;
import gamma.engine.rendering.DebugRenderer;
import vecmatlib.matrix.Mat4f;
import vecmatlib.vector.Vec3f;
import vecmatlib.vector.Vec4f;

import java.util.List;

public class CollisionObject3D extends Node3D {

	@EditorVariable(name = "Bounding box")
	@EditorRange
	public Vec3f boundingBox = Vec3f.One();

	@EditorVariable(name = "Offset")
	@EditorRange
	public Vec3f offset = Vec3f.Zero();

	@Override
	protected void onEnter() {
		super.onEnter();
		PhysicsSystem.add(this);
	}

	@Override
	protected void onEditorProcess() {
		super.onEditorProcess();
		DebugRenderer.addToBatch(CubeMesh.INSTANCE, mesh -> {
			Mat4f shape = Mat4f.translation(this.offset).multiply(Mat4f.scaling(this.boundingBox.dividedBy(2.0f)));
			DebugRenderer.SHADER.setUniform("transformation_matrix", this.globalTransformation().multiply(shape));
			DebugRenderer.SHADER.setUniform("color", 0.0f, 1.0f, 0.0f, 1.0f);
			mesh.drawElements();
		});
	}

	@Override
	protected void onExit() {
		super.onExit();
		PhysicsSystem.remove(this);
	}

	public final void resolveCollision(CollisionObject3D that) {
		Vec3f normal = Vec3f.Zero();
		float depth = Float.POSITIVE_INFINITY;
		Mat4f rotationA = this.globalRotation();
		Mat4f rotationB = that.globalRotation();
		Vec3f[] axes = new Vec3f[] {
				rotationA.col0().xyz().normalized(),
				rotationA.col1().xyz().normalized(),
				rotationA.col2().xyz().normalized(),
				rotationB.col0().xyz().normalized(),
				rotationB.col1().xyz().normalized(),
				rotationB.col2().xyz().normalized()
		};
		for(Vec3f axis : axes) {
			Projection projectionA = this.projectBoundingBox(axis);
			Projection projectionB = that.projectBoundingBox(axis);
			if(!projectionA.overlaps(projectionB)) {
				return;
			}
			float axisDepth = projectionA.getOverlap(projectionB).length();
			if(axisDepth < depth) {
				depth = axisDepth;
				normal = axis;
			}
		}
		if(this.meanCenter().minus(that.meanCenter()).dot(normal) < 0.0f) {
			normal = normal.negated();
		}
		this.onCollision(new Collision3D(that, normal, depth));
	}

	public final Projection projectBoundingBox(Vec3f axis) {
		float min = Float.POSITIVE_INFINITY, max = Float.NEGATIVE_INFINITY;
		for(Vec3f vertex : this.getVertices()) {
			float projection = vertex.dot(axis);
			if(projection < min) min = projection;
			if(projection > max) max = projection;
		}
		return new Projection(min, max);
	}

	public final List<Vec3f> getVertices() {
		Vec3f halfExtents = this.boundingBox.dividedBy(2.0f);
		Vec3f origin = this.globalPosition().plus(this.offset);
		Mat4f rotation = this.globalRotation();
		Vec3f scale = this.globalScale();
		return List.of(
				origin.plus(rotation.multiply(new Vec4f(halfExtents.x(), halfExtents.y(), halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(-halfExtents.x(), halfExtents.y(), halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(halfExtents.x(), -halfExtents.y(), halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(halfExtents.x(), halfExtents.y(), -halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(-halfExtents.x(), -halfExtents.y(), halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(halfExtents.x(), -halfExtents.y(), -halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(-halfExtents.x(), halfExtents.y(), -halfExtents.z(), 1.0f)).xyz().multiply(scale)),
				origin.plus(rotation.multiply(new Vec4f(-halfExtents.x(), -halfExtents.y(), -halfExtents.z(), 1.0f)).xyz().multiply(scale))
		);
	}

	public final Vec3f meanCenter() {
		return this.getVertices().stream().reduce(Vec3f.Zero(), Vec3f::plus).dividedBy(8);
	}

	protected void onCollision(Collision3D collision) {

	}
}