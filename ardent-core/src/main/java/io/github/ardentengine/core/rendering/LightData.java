package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.math.Vector3;

public abstract class LightData {

    public abstract void setEnabled(boolean enabled);

    public abstract void setPosition(Vector3 position);

    public abstract void setAmbient(Color ambient);

    public abstract void setDiffuse(Color diffuse);

    public abstract void setSpecular(Color specular);
}
