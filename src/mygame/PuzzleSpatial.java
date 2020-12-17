package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 * Class to create a Spatial from a custom 3D model within the PuzzleProject
 * Game
 *
 * @author Christian Kasel
 */
public class PuzzleSpatial {

    private Spatial spatial;
    private Material material;
    private CollisionShape collShape;
    private RigidBodyControl spatialControl;
    private Node nodeToAttach;
    private Texture tex;
    private Vector3f entryLoc;

    /**
     * Constructor to initialize a PuzzleSpatial object
     *
     * @param spatial the Spatial object shape
     * @param material the material of the Spatial
     * @param nodeToAttach in case the Spatial will be attached to a specific
     * node it can be sent here
     * @param entryLoc the location of the entrance to the room it is being
     * placed in
     */
    public PuzzleSpatial(Spatial spatial, Material material, Node nodeToAttach, Vector3f entryLoc) {
        this.spatial = spatial;
        this.material = material;
        this.collShape = CollisionShapeFactory.createDynamicMeshShape(spatial);
        this.spatialControl = new RigidBodyControl(collShape);
        this.nodeToAttach = nodeToAttach;
        this.entryLoc = entryLoc;
    }

    /**
     * Function to add the spatial to the node it was initialized to
     *
     * @param translation the vector that the spatial will be translated by
     * @param mass the mass the spatial will have
     * @param scale the dimensions the spatial will be scaled by
     * @param tex the texture the spatial will have
     */
    public void attachSpatial(Vector3f translation, float mass, Vector3f scale, Texture tex) {
        scale(scale);
        setTexture(tex);
        moveSpatial(translation);
        addCollision(mass);
        this.nodeToAttach.attachChild(spatial);
    }

    /**
     * Function to translate the Spatial via a Vector3f
     *
     * @param translation the translation in which the Spatial will travel
     */
    public void moveSpatial(Vector3f translation) {
        spatial.setLocalTranslation(new Vector3f(translation.x + nodeToAttach.getLocalTranslation().x, translation.y + nodeToAttach.getLocalTranslation().y, translation.z + nodeToAttach.getLocalTranslation().z - entryLoc.z));
    }

    /**
     * Function to add collision (or mass) to the spatial
     *
     * @param mass float value of the mass the Spatial will have
     */
    public void addCollision(float mass) {
        collShape = CollisionShapeFactory.createDynamicMeshShape(spatial);
        spatialControl = new RigidBodyControl(collShape, mass);
        spatial.addControl(spatialControl);
    }

    /**
     * Function to rotate the spatial by a Quaternion
     *
     * @param rotation rotation the spatial will be rotated by
     */
    public void rotate(Quaternion rotation) {
        spatial.rotate(rotation);
    }

    /**
     * Function to return the control the Spatial follows
     *
     * @return the private control the Spatial uses
     */
    public RigidBodyControl getSpatialControl() {
        return spatialControl;
    }

    /**
     * Function to scale the Spatial
     *
     * @param scale float value of the scale in which will scale the Spatial
     */
    public void scale(Vector3f scale) {
        spatial.scale(scale.x, scale.y, scale.z);
    }

    /**
     * In case a texture is desired, this function allows a texture to be set on
     * the Spatial
     *
     * @param tex the texture that will override the previous texture with what
     * the spatial will now have
     */
    public void setTexture(Texture tex) {
        this.tex = tex;
        material.setTexture("ColorMap", tex);
        spatial.setMaterial(material);
    }

    /**
     * Function to return the texture of the spatial
     *
     * @return the texture the spatial has
     */
    public Texture getTex() {
        return tex;
    }
}
