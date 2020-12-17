package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Class to create a parkour level in the PuzzleProject game
 *
 * @author Christian Kasel
 */
public class ParkourLevel {

    Node nodeToAttach;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    PuzzleSpatial stairs;
    PuzzleBox boxs;
    Material defMaterial;
    private Vector3f entryLoc;

    /**
     * ParkourLevel constructor that will initialize all the private variables,
     * and then call the createLevel function
     *
     * @param nodeToAttach the node in which the level will be attached to
     * @param assetManager the asset manager that will allow us to load a model
     * or materials/textures
     * @param bulletAppState the bullet app state that we will add our controls
     * to
     * @param entryLoc the location in which the level will have
     */
    public ParkourLevel(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.entryLoc = entryLoc;
        Spatial stair = assetManager.loadModel("Models/stair.obj");

        stairs = new PuzzleSpatial(stair, defMaterial, nodeToAttach, entryLoc);
        boxs = new PuzzleBox(new Vector3f(4f, 1f, 3f), defMaterial, nodeToAttach, entryLoc);

        createLevel();
    }

    /**
     * Function to place each of the spatials in the place they should go
     */
    private void createLevel() {
        // Stairs
        for (int i = 1; i <= 5; i++) {
            Spatial stair = assetManager.loadModel("Models/stair.obj");
            stairs = new PuzzleSpatial(stair, defMaterial, nodeToAttach, entryLoc);
            stairs.rotate(new Quaternion(0f, 1f, 0f, -1f));
            stairs.attachSpatial(newVector(-5 + (i * 10), -10 - (i * 9), -3.25f), 0f, newVector(4f, 5f, 5f), assetManager.loadTexture(new TextureKey("Materials/WallMat.png")));
            bulletAppState.getPhysicsSpace().add(stairs.getSpatialControl());
        }

        // Left path
        for (int i = 1; i <= 4; i++) {
            boxs.attachBox(newVector((-14 * i), -9f, (-14 * i)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 5; i++) {
            boxs.attachBox(newVector((-56 - (i * 8)), -9f, (-56 - (14 * i))), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 4; i++) {
            boxs.attachBox(newVector(-96f, -9f, (-126 - (i * 14))), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 5; i++) {
            boxs.attachBox(newVector((-88 + (i * 8)), -9f, (-196 - (i * 14))), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 3; i++) {
            boxs.attachBox(newVector((-42 + (i * 14)), -9f, (-266 - (i * 14))), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }

        // Right path
        for (int i = 1; i <= 4; i++) {
            boxs.attachBox(newVector(14 * i, -9f, -14 * i), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 5; i++) {
            boxs.attachBox(newVector(64 + (i * 8), -9f, -70 - (i * 14)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 4; i++) {
            boxs.attachBox(newVector(96f, -9f, -140 - (i * 14)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 5; i++) {
            boxs.attachBox(newVector(88 - (i * 8), -9f, -196 - (i * 14)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 3; i++) {
            boxs.attachBox(newVector(42 - (i * 14), -9f, -266 - (i * 14)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }

        // Middle Path
        for (int i = 0; i < 6; i++) {
            boxs.attachBox(newVector(0f, -20 - (i * 5), -15 - (i * 20)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 3; i++) {
            boxs.attachBox(newVector(0f, -45, -115 - (i * 15)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 0; i < 12; i++) {
            boxs.attachBox(newVector(0f, -45 + (i * 3), -145 - (i * 12)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }

        // Left in-between path
        for (int i = 1; i <= 8; i++) {
            boxs.attachBox(newVector(0 - (i * 8), -11 - (i * 3), 0 - (i * 16)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 2; i++) {
            boxs.attachBox(newVector(-64 - (i * 1), -35, -128 - (i * 11)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 2; i++) {
            boxs.attachBox(newVector(-66 + (i * 1), -35, -150 - (i * 11)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 8; i++) {
            boxs.attachBox(newVector(-66 + (i * 7), -35 + (i * 3), -172 - (i * 14)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }

        // Right in-between path
        for (int i = 1; i <= 8; i++) {
            boxs.attachBox(newVector(0 + (i * 8), -11 - (i * 3), 0 - (i * 16)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 2; i++) {
            boxs.attachBox(newVector(64 + (i * 1), -35, -128 - (i * 11)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 2; i++) {
            boxs.attachBox(newVector(66 - (i * 1), -35, -150 - (i * 11)), 0f);
            bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());
            boxs = createBox(newVector(4f, 1f, 3f));
        }
        for (int i = 1; i <= 8; i++) {
            boxs.attachBox(newVector(66 - (i * 7), -35 + (i * 3), -172 - (i * 14)), 0f);
            addToPhysics(boxs);
            boxs = createBox(newVector(4f, 1f, 3f));
        }
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
    public Vector3f newVector(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    /**
     * Function to create the boxes that will act as our walls
     *
     * @param scale: the scale of the box
     * @return the PuzzleBox being created
     */
    public PuzzleBox createBox(Vector3f scale) {
        return new PuzzleBox(scale, defMaterial, nodeToAttach, entryLoc);
    }

    /**
     * Adds the "box" parameter to the bulletAppState
     *
     * @param box: PuzzleBox being added to the bulletAppState
     */
    public void addToPhysics(PuzzleBox box) {
        bulletAppState.getPhysicsSpace().add(box.getSpatialControl());
    }
}
