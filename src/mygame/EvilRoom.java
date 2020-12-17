package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import static mygame.PuzzleProject.evilButton;

/**
 *
 * @author Kara
 */
public class EvilRoom extends SimpleApplication implements ActionListener {

    static Node nodeToAttach;
    AssetManager assetManager;
    static BulletAppState bulletAppState;
    static PuzzleBox boxs;
    PuzzleBox boxc;
    static Material defMaterial;
    Material defMaterial2;
    private static Vector3f entryLoc;

    /**
     * This room is evil. Don't crouch.
     *
     * @param nodeToAttach the node that the room (and all inside of it) will be
     * attached to
     * @param assetManager the asset manager that allows us to use materials,
     * textures, Spatials, etc.
     * @param bulletAppState the bullet app state that all of our collisions
     * will be added to
     * @param entryLoc the location of the entrance to the level that will be
     * passed to each spatial
     */
    public EvilRoom(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.defMaterial2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.entryLoc = entryLoc;

        boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
        boxs.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormGravMat.png")));
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);
        boxc.setTexture(assetManager.loadTexture(new TextureKey("Materials/AbGravMat.png")));

        createLevel();
    }

    /**
     * Platforms to jump on... pretty simple, right?
     */
    private void createLevel() {
        // Path1 (right)
        for (int i = 0; i < 6; i = i + 2) {
            boxs.attachBox(newVector(6f, -20 - (i * 5), -15 - (i * 20)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
        }
        for (int i = 1; i < 3; i = i + 2) {
            boxs.attachBox(newVector(6f, -45, -115 - (i * 15)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
        }
        // Path2 (left)
        for (int i = 1; i < 6; i = i + 2) {
            boxs.attachBox(newVector(-6f, -20 - (i * 5), -15 - (i * 20)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
        }
        for (int i = 0; i < 3; i = i + 2) {
            boxs.attachBox(newVector(-6f, -45, -115 - (i * 15)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
        }
        // Ceiling blocks
        boxc.attachBox(newVector(0f, 45f, -140f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(40f, 46f, -120f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-40f, 46f, -120f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(80f, 46f, -100f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-80f, 46f, -100f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(40f, 46f, -80f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-40f, 46f, -80f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(0f, 46f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(40f, 46f, -160f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-40f, 46f, -160f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(80f, 46f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-80f, 46f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(40f, 46f, -200f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(-40f, 46f, -200f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(0f, 46f, -220f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(0f, 46f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);

        boxc.attachBox(newVector(0f, 46f, -100f), 0f);
        bulletAppState.getPhysicsSpace().add(boxc.getSpatialControl());
        boxc = createBox(newVector(15f, 1f, 15f), defMaterial2);
    }

    /**
     * Function to create a new Vector3f with "x (length)", "y (height)" and "z
     * (width)"
     *
     * @param x: length of the vector
     * @param y: height of the vector
     * @param z: width of the vector
     * @return the vector created
     */
    public static Vector3f newVector(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    /**
     * Function to create and return the new box
     *
     * @param scale: the scale of the box
     * @param mat: the material of the box
     * @return the PuzzleBox being created
     */
    public static PuzzleBox createBox(Vector3f scale, Material mat) {
        return new PuzzleBox(scale, mat, nodeToAttach, entryLoc);
    }

    /**
     * Adds the "box" parameter to the bulletAppState
     *
     * @param box: PuzzleBox being added to the bulletAppState
     */
    public void addToPhysics(PuzzleBox box) {
        bulletAppState.getPhysicsSpace().add(box.getSpatialControl());
    }

    public static void pressEvilButton() {
        if (evilButton == true) {
            for (int i = 1; i < 12; i = i + 2) {
                boxs.attachBox(newVector(6f, -45 + (i * 3), -145 - (i * 12)), 0f);
                bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
                boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
            }
            for (int i = 0; i < 12; i = i + 2) {
                boxs.attachBox(newVector(-6f, -45 + (i * 3), -145 - (i * 12)), 0f);
                bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
                boxs = createBox(newVector(4f, 1f, 3f), defMaterial);
            }
        }
    }

    @Override
    public void simpleInitApp() {
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
    }
}
