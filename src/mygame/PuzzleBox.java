package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * Class to create Boxes to be used within the PuzzleProject Game
 *
 * @author Christian Kasel
 */
public class PuzzleBox extends PuzzleSpatial {

    private Material mat;
    private final Node nodeToAttach;
    private final Box b;
    private final Geometry box;
    private CollisionShape collShape;
    private RigidBodyControl spatialControl;
    private Spatial spatial;
    private Vector3f entryLoc;

    /**
     * Constructor to create a new Box used in the PuzzleProject game
     *
     * @param scale The scale of the box to be created
     * @param mat1 The material the box will use
     * @param nodeToAttach The node in which the box will be attached
     * @param entryLoc the location of the entrance to the room it is being
     * placed in
     */
    public PuzzleBox(Vector3f scale, Material mat1, Node nodeToAttach, Vector3f entryLoc) {
        super(new Geometry("Box", new Box(scale.x, scale.y, scale.z)), mat1, nodeToAttach, entryLoc);
        b = new Box(scale.x, scale.y, scale.z);
        box = new Geometry("Box", b);
        this.nodeToAttach = nodeToAttach;
        this.entryLoc = entryLoc;
        mat = mat1;
        spatial = (Spatial) box;

        box.setMaterial(mat);
    }

    /**
     * Function to attach the box to the node and add collision to the box
     *
     * @param translation the translation the box will be moved via
     * @param mass the mass the box will have
     */
    public void attachBox(Vector3f translation, float mass) {
        moveBox(translation);
        addCollision(mass);
        nodeToAttach.attachChild(spatial);
    }

    /**
     * Function to move the box by the vector the user inputs
     *
     * @param translation The Vector3f the box will be translated by
     */
    public void moveBox(Vector3f translation) {
        spatial.setLocalTranslation(new Vector3f(translation.x + nodeToAttach.getLocalTranslation().x, translation.y + nodeToAttach.getLocalTranslation().y, translation.z + nodeToAttach.getLocalTranslation().z - entryLoc.z));
    }

    /**
     * Function to add collision (mass) to the boxes
     *
     * @param mass float value of the mass in which the box will have
     */
    @Override
    public void addCollision(float mass) {
        collShape = CollisionShapeFactory.createDynamicMeshShape(spatial);
        spatialControl = new RigidBodyControl(collShape, mass);
        spatial.addControl(spatialControl);
    }

    /**
     * Function to remove collision (control) from the box
     */
    public void removeCollision() {
        spatial.removeControl(spatialControl);
    }

    /**
     * Function to move the boxes smoothly
     *
     * @param max the maximum the box will move by
     * @param axis the axis the box will move along
     * @param Dir the direction along the axis it will move
     */
    public void zoneMoving(float max, char axis, String Dir) {
        if (axis == 'x' && "left".equals(Dir)) {
            if (box.getLocalTranslation().x > max) {
                box.setLocalTranslation(box.getLocalTranslation().x - .2f, box.getLocalTranslation().y, box.getLocalTranslation().z);
            }
        } else if (axis == 'y' && "down".equals(Dir)) {
            if (box.getLocalTranslation().y > max) {
                box.setLocalTranslation(box.getLocalTranslation().x, box.getLocalTranslation().y - .2f, box.getLocalTranslation().z);
            }
        } else if (axis == 'z' && "forward".equals(Dir)) {
            if (box.getLocalTranslation().z > max) {
                box.setLocalTranslation(box.getLocalTranslation().x, box.getLocalTranslation().y, box.getLocalTranslation().z - .2f);
            }
        } else if (axis == 'x' && "right".equals(Dir)) {
            if (box.getLocalTranslation().x < max) {
                box.setLocalTranslation(box.getLocalTranslation().x + .2f, box.getLocalTranslation().y, box.getLocalTranslation().z);
            }
        } else if (axis == 'y' && "up".equals(Dir)) {
            if (box.getLocalTranslation().y < max) {
                box.setLocalTranslation(box.getLocalTranslation().x, box.getLocalTranslation().y + .2f, box.getLocalTranslation().z);
            }
        } else if (axis == 'z' && "back".equals(Dir)) {
            if (box.getLocalTranslation().z < max) {
                box.setLocalTranslation(box.getLocalTranslation().x, box.getLocalTranslation().y, box.getLocalTranslation().z + .2f);
            }
        }
    }

    /**
     * Function to return the translation the box has been moved relative to the
     * origin
     *
     * @return the translation vector
     */
    public Vector3f getLocalTranslation() {
        return box.getLocalTranslation();
    }

    /**
     * Function to return the spatial's control (physics)
     *
     * @return the RigidBodyControl that controls the spatial
     */
    @Override
    public RigidBodyControl getSpatialControl() {
        return spatialControl;
    }

    /**
     * Function to set the material of the box after it has been initialized
     *
     * @param mat material box will be set to
     */
    public void setMat(Material mat) {
        this.mat = mat;
        spatial.setMaterial(mat);
    }

    /**
     * Function to override the spatial's default texture
     *
     * @param tex the texture that will go on the spatial
     */
    @Override
    public void setTexture(Texture tex) {
        super.setTexture(tex);
    }

    /**
     * Rotate the spatial by the rotation parameter
     *
     * @param rotation the rotation the spatial will be rotated by
     */
    @Override
    public void rotate(Quaternion rotation) {
        spatial.rotate(rotation);
    }
}
