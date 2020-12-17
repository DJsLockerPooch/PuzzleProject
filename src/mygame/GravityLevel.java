package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Class to create a gravity level in the PuzzleProject game
 *
 * @author Christian Kasel
 */
public class GravityLevel {

    Node nodeToAttach;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    PuzzleSpatial stairs;
    PuzzleBox boxs;
    PuzzleBox boxs2;
    Material defMaterial;
    Material defMaterial2;
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
    public GravityLevel(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.defMaterial2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.entryLoc = entryLoc;

        createLevel();
    }

    /**
     * Function to place each of the spatials in the place they should go
     */
    private void createLevel() {
        Quaternion rot90x = new Quaternion(1, 0, 0, -1);
        Quaternion rot90y = new Quaternion(0, 1, 0, -1);
        Quaternion rot90z = new Quaternion(0, 0, 1, -1);

        // First wall you encounter
        boxs = createBox(newVector(10, 13, 1), defMaterial);
        boxs.attachBox(newVector(0, -86, -27), 0f);
        addToPhysics(boxs);

        // Immediate left wall
        boxs = createBox(newVector(12.5f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-11, -80.5f, -13.5f), 0f);
        addToPhysics(boxs);

        // Immediate right wall
        boxs = createBox(newVector(12.5f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(11, -80.5f, -13.5f), 0f);
        addToPhysics(boxs);

        // Start
        boxs2 = createBox(newVector(10, 22.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(0, -61, -23.5f), 0f);
        addToPhysics(boxs2);

        boxs.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormGravMat.png")));
        boxs2.setTexture(assetManager.loadTexture(new TextureKey("Materials/AbGravMat.png")));

        // Left
        boxs2 = createBox(newVector(10, 15.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(25, -61, -36), 0f);
        addToPhysics(boxs2);

        // Left left right
        boxs2 = createBox(newVector(79, 13, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(91, -61, -13.5f), 0f);
        addToPhysics(boxs2);

        // Left left right right
        boxs2 = createBox(newVector(12, 20, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(108, -61, -46), 0f);
        addToPhysics(boxs2);

        // Left left right straight right
        boxs2 = createBox(newVector(15, 55, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(185, -61, -55.5f), 0f);
        addToPhysics(boxs2);

        // Left left right straight right straight
        boxs2 = createBox(newVector(15, 56, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(185, -61, -166.5f), 0f);
        addToPhysics(boxs2);

        // Left left right straight right straight straight
        boxs2 = createBox(newVector(15, 55, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(185, -61, -275.5f), 0f);
        addToPhysics(boxs2);

        // Left left right straight right straight straight straight
        boxs2 = createBox(newVector(15, 33.75f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(185, -61, -366.25f), 0f);
        addToPhysics(boxs2);

        // Left left right straight right straight straight right
        boxs2 = createBox(newVector(79, 13, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(91f, -61, -387.5f), 0f);
        addToPhysics(boxs2);

        // Left left right right straight
        boxs2 = createBox(newVector(12, 20, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(108, -61, -88), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right
        boxs2 = createBox(newVector(10, 35.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(84.5f, -61, -118f), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left
        boxs2 = createBox(newVector(12, 21, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(37, -61, -129), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight
        boxs2 = createBox(newVector(12, 38, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(37, -61, -188), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left
        boxs2 = createBox(newVector(10, 15.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(64.5f, -61, -216f), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left
        boxs2 = createBox(newVector(12, 20, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(68, -61, -186), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right
        boxs2 = createBox(newVector(10, 25f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(81, -61, -156f), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right right
        boxs2 = createBox(newVector(12, 34, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(94, -61, -200), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right right straight
        boxs2 = createBox(newVector(12, 40, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(94, -61, -276), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right right straight right
        boxs2 = createBox(newVector(10, 17.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(64.5f, -61, -288f), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right right straight right left
        boxs2 = createBox(newVector(12, 19, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(35, -61, -297), 0f);
        addToPhysics(boxs2);

        // Left left right right straight right left straight left left right right straight right left left
        boxs2 = createBox(newVector(10, 41.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(64.5f, -61, -326f), 0f);
        addToPhysics(boxs2);

        // Right
        boxs2 = createBox(newVector(10, 35.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-45, -61, -36), 0f);
        addToPhysics(boxs2);

        // Right left
        boxs2 = createBox(newVector(8f, 15, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-39.5f, -61, -61), 0f);
        addToPhysics(boxs2);

        // Right left straight
        boxs2 = createBox(newVector(8f, 31, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-39.5f, -61, -109), 0f);
        addToPhysics(boxs2);

        // Right left straight straight
        boxs2 = createBox(newVector(8f, 15, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-39.5f, -61, -155), 0f);
        addToPhysics(boxs2);

        // Middle intersection
        boxs2 = createBox(newVector(10, 41.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-16.5f, -61, -180), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight
        boxs2 = createBox(newVector(8f, 15, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-39.5f, -61, -205), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight straight
        boxs2 = createBox(newVector(8f, 15, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-39.5f, -61, -237), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight straight right
        boxs2 = createBox(newVector(10, 50.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-82, -61, -262), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight straight right straight
        boxs2 = createBox(newVector(10, 25.75f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-158.25f, -61, -262), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight straight right straight left
        boxs2 = createBox(newVector(8f, 45, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-192f, -61, -297), 0f);
        addToPhysics(boxs2);

        // Right left straight straight straight straight right straight left straight
        boxs2 = createBox(newVector(8f, 29, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-192, -61, -372), 0f);
        addToPhysics(boxs2);

        // Right straight left
        boxs2 = createBox(newVector(10, 39f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-90.5f, -61, -65), 0f);
        addToPhysics(boxs2);

        // Right straight left right
        boxs2 = createBox(newVector(10, 30.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-111, -61, -114), 0f);
        addToPhysics(boxs2);

        // Right straight left right right/left
        boxs2 = createBox(newVector(10, 39f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-131.5f, -61, -65), 0f);
        addToPhysics(boxs2);

        // Right straight left left right right left
        boxs2 = createBox(newVector(10, 29.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-170.5f, -61, -36), 0f);
        addToPhysics(boxs2);

        // Right straight left right right right left left
        boxs2 = createBox(newVector(10, 39, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-190f, -61, -85), 0f);
        addToPhysics(boxs2);

        // Right straight left right left
        boxs2 = createBox(newVector(10, 11, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-131.5f, -61, -135), 0f);
        addToPhysics(boxs2);

        // Right straight left right left straight
        boxs2 = createBox(newVector(10, 22, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-131.5f, -61, -168), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right
        boxs2 = createBox(newVector(10, 31, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-90.5f, -61, -180), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left
        boxs2 = createBox(newVector(10, 29.5f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-170.5f, -61, -180), 0f);
        addToPhysics(boxs2);

        // Right straight left right left straight left
        boxs2 = createBox(newVector(10, 20, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-190, -61, -210), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left
        boxs2 = createBox(newVector(10, 40f, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-140, -61, -220), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right
        boxs2 = createBox(newVector(10, 21, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-90, -61, -231), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right straight
        boxs2 = createBox(newVector(10, 21, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-90, -61, -293), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right straight right straight
        boxs2 = createBox(newVector(10, 21, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-101, -61, -324), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right straight right staight left
        boxs2 = createBox(newVector(10, 33, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.attachBox(newVector(-112, -61, -367), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right straight right staight left left
        boxs2 = createBox(newVector(10, 16, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-86, -61, -390), 0f);
        addToPhysics(boxs2);

        // Right straight left right left right left left right straight right staight left left straight
        boxs2 = createBox(newVector(10, 38, 1), defMaterial2);
        boxs2.rotate(rot90x);
        boxs2.rotate(rot90z);
        boxs2.attachBox(newVector(-30, -61, -390), 0f);
        addToPhysics(boxs2);

        // Left left right straight right straight straight straight (right wall)
        boxs = createBox(newVector(21, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -80.5f, -353.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight straight straight right (back wall)
        boxs = createBox(newVector(12.75f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(11f, -80.5f, -387.25f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight straight straight right (right wall)
        boxs = createBox(newVector(79, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(89, -80.5f, -373.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight straight (end blocker)
        boxs = createBox(newVector(15, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(185, -76.5f, -331.5f), 0f);
        addToPhysics(boxs);

        //Left left right straight right straight straight (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -95, -331.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight (end blocker)
        boxs = createBox(newVector(15, 13.5f, 1), defMaterial);
        boxs.attachBox(newVector(185, -85.5f, -221.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight (small filler wall)
        boxs = createBox(newVector(1, 5, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -67, -221.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight straight (right wall)
        boxs = createBox(newVector(54, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -80.5f, -276.5f), 0f);
        addToPhysics(boxs);

        // Left left straight right (end blocker)
        boxs = createBox(newVector(15, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(185, -76.5f, -111.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -95, -111.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right (right wall)
        boxs = createBox(newVector(42, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -80.5f, -68.5f), 0f);
        addToPhysics(boxs);

        // Left left right straight right straight (right wall)
        boxs = createBox(newVector(54, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(169, -80.5f, -166.5f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right straight right (left wall)
        boxs = createBox(newVector(41.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(64.5f, -80.5f, -337f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right right left (right wall)
        boxs = createBox(newVector(29f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(22, -80.5f, -307f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right straight right (right wall)
        boxs = createBox(newVector(17.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(64.5f, -80.5f, -315f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right right left (left wall)
        boxs = createBox(newVector(8, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(48, -80.5f, -306f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right straight (right wall)
        boxs = createBox(newVector(8, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(81, -80.5f, -306f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right (left wall)
        boxs = createBox(newVector(15.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(64.5f, -80.5f, -299f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right (right wall)
        boxs = createBox(newVector(29.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(52.5f, -80.5f, -277f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right straight (left wall)
        boxs = createBox(newVector(50, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(107, -80.5f, -286f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right straight (right wall)
        boxs = createBox(newVector(20, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(81, -80.5f, -256f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right (right wall)
        boxs = createBox(newVector(34f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(81, -80.5f, -200f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right right (left wall)
        boxs = createBox(newVector(44, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(107, -80.5f, -190f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left right (left wall)
        boxs = createBox(newVector(25, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(81, -80.5f, -145), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left (left wall)
        boxs = createBox(newVector(30, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(55, -80.5f, -176), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left left (left wall)
        boxs = createBox(newVector(2.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(51.5f, -80.5f, -205), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight (left wall)
        boxs = createBox(newVector(27, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(50, -80.5f, -177f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight (right wall)
        boxs = createBox(newVector(18, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(24, -80.5f, -208f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight left (right wall)
        boxs = createBox(newVector(27.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(52.5f, -80.5f, -227f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left straight (right wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(24, -80.5f, -160f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left (right wall)
        boxs = createBox(newVector(20, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(24, -80.5f, -128f), 0f);
        addToPhysics(boxs);

        // Left left right right straight right (right wall)
        boxs = createBox(newVector(35.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(60.5f, -80.5f, -107), 0f);
        addToPhysics(boxs);

        // Left left right right straight right (left wall)
        boxs = createBox(newVector(35.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(84.5f, -80.5f, -129), 0f);
        addToPhysics(boxs);

        // Left left right right straight right left (left wall)
        boxs = createBox(newVector(9f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(50f, -80.5f, -139f), 0f);
        addToPhysics(boxs);

        // Left left right right straight (left wall)
        boxs = createBox(newVector(30f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(121, -80.5f, -98f), 0f);
        addToPhysics(boxs);

        // Left left right right straight (right wall)
        boxs = createBox(newVector(19, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(95, -80.5f, -87), 0f);
        addToPhysics(boxs);

        //Left left right right (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(95, -95, -67), 0f);
        addToPhysics(boxs);

        // Left left right right (end blocker)
        boxs = createBox(newVector(12, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(108, -76.5f, -67f), 0f);
        addToPhysics(boxs);

        //Left left right right (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(121, -95, -67), 0f);
        addToPhysics(boxs);

        //Left left right straight right straight straight (small filler wall)
        boxs = createBox(newVector(1, 5, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(24, -67, -149), 0f);
        addToPhysics(boxs);

        // Left left right right (end blocker)
        boxs = createBox(newVector(12, 13.5f, 1), defMaterial);
        boxs.attachBox(newVector(37, -85.5f, -149f), 0f);
        addToPhysics(boxs);

        //Left left right straight right straight straight (small filler wall)
        boxs = createBox(newVector(1, 5, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(50, -67, -149), 0f);
        addToPhysics(boxs);

        //Left left right straight right straight straight (small filler wall)
        boxs = createBox(newVector(1, 5, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(81, -94, -235), 0f);
        addToPhysics(boxs);

        // Left left right right (end blocker)
        boxs = createBox(newVector(12, 13.5f, 1), defMaterial);
        boxs.attachBox(newVector(94, -75.5f, -235), 0f);
        addToPhysics(boxs);

        //Left left right straight right straight straight (small filler wall)
        boxs = createBox(newVector(1, 5, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(107, -94, -235), 0f);
        addToPhysics(boxs);

        // Left left right straight (right wall)
        boxs = createBox(newVector(24, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(144, -80.5f, -27.5f), 0f);
        addToPhysics(boxs);

        // Left left right (right wall)
        boxs = createBox(newVector(27.75f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(68.25f, -80.5f, -27.5f), 0f);
        addToPhysics(boxs);

        // Left left right right (left wall)
        boxs = createBox(newVector(18.75f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(121f, -80.5f, -47.25f), 0f);
        addToPhysics(boxs);

        // Left left right right (right wall)
        boxs = createBox(newVector(18.75f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(95f, -80.5f, -47.25f), 0f);
        addToPhysics(boxs);

        // Left (end wall)
        boxs = createBox(newVector(8.75f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(41.5f, -80.5f, -37.25f), 0f);
        addToPhysics(boxs);

        // Left (back wall)
        boxs = createBox(newVector(36, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(4.5f, -80.5f, -47f), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left straight (back wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(9, -80.5f, -390f), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left straight (left wall)
        boxs = createBox(newVector(38, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-30, -80.5f, -379), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left (end blocker wall)
        boxs = createBox(newVector(10, 14.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-69, -76.5f, -390), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-69, -95, -379), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left (left wall)
        boxs = createBox(newVector(16, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-86, -80.5f, -379), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left (left wall)
        boxs = createBox(newVector(22, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-101, -80.5f, -356), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left (right wall)
        boxs = createBox(newVector(44, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-123, -80.5f, -358), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight (right wall)
        boxs = createBox(newVector(11, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-111, -80.5f, -313), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right (left wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-90, -80.5f, -335), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left (left wall)
        boxs = createBox(newVector(31, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-79, -80.5f, -303), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left (right wall)
        boxs = createBox(newVector(20, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-101, -80.5f, -292), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right straight (left wall)
        boxs = createBox(newVector(41, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-143, -80.5f, -273), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right straight left (left wall)
        boxs = createBox(newVector(33.5f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-183, -80.5f, -307.5f), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right straight left straight (left wall)
        boxs = createBox(newVector(28.5f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-183, -80.5f, -371.5f), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left (end blocker wall)
        boxs = createBox(newVector(8, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(-192, -76.5f, -342), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right left right straight left left (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-183, -95, -342), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right straight (right wall)
        boxs = createBox(newVector(50, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-150, -80.5f, -251), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight (left wall)
        boxs = createBox(newVector(25f, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-30.5f, -80.5f, -247), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right (left wall)
        boxs = createBox(newVector(23.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-54.5f, -80.5f, -273), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right (right wall)
        boxs = createBox(newVector(16.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-63.75f, -80.5f, -251), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight (right wall)
        boxs = createBox(newVector(14, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-48.5f, -80.5f, -236), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right right (right wall)
        boxs = createBox(newVector(20, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-79, -80.5f, -230), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right right (left wall)
        boxs = createBox(newVector(9, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-101, -80.5f, -241), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right right left (left wall)
        boxs = createBox(newVector(50, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-150, -80.5f, -231), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right right left (right wall)
        boxs = createBox(newVector(50, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-130, -80.5f, -209), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight straight right right left right (right wall)
        boxs = createBox(newVector(9, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-179, -80.5f, -199), 0f);
        addToPhysics(boxs);

        // Right left straight straight right straight (left wall)
        boxs = createBox(newVector(59.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-118.75f, -80.5f, -191), 0f);
        addToPhysics(boxs);

        // Right left straight straight right straight (right wall)
        boxs = createBox(newVector(29.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-170.75f, -80.5f, -169), 0f);
        addToPhysics(boxs);

        // Right left straight straight right (right wall)
        boxs = createBox(newVector(31, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-90.5f, -80.5f, -169), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight  (right wall)
        boxs = createBox(newVector(15, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-48.5f, -80.5f, -205), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(15, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-30.5f, -80.5f, -205), 0f);
        addToPhysics(boxs);

        // Right left straight straight right (left wall)
        boxs = createBox(newVector(4f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-53.5f, -80.5f, -191), 0f);
        addToPhysics(boxs);

        // Right left straight straight right (right wall)
        boxs = createBox(newVector(4f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-53.5f, -80.5f, -169), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight(small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-30.5f, -95, -221), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (end blocker wall)
        boxs = createBox(newVector(8, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(-39.5f, -76.5f, -221), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight(small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-48.5f, -95, -221), 0f);
        addToPhysics(boxs);

        // Right left straight straight right (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-58.5f, -95, -169), 0f);
        addToPhysics(boxs);

        // Right left straight straight right (end blocker wall)
        boxs = createBox(newVector(10, 14.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-58.5f, -76.5f, -180), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-58.5f, -95, -191), 0f);
        addToPhysics(boxs);

        // Right left straight straight left straight (right wall)
        boxs = createBox(newVector(26.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-3.25f, -80.5f, -191), 0f);
        addToPhysics(boxs);

        // Right left straight straight left straight (left wall)
        boxs = createBox(newVector(26.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-3.25f, -80.5f, -169), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight  (right wall)
        boxs = createBox(newVector(15, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-48.5f, -80.5f, -155), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(15, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-30.5f, -80.5f, -155), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight  (right wall)
        boxs = createBox(newVector(30, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-48.5f, -80.5f, -108), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(30, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-30.5f, -80.5f, -108), 0f);
        addToPhysics(boxs);

        // Right left straight straight (small filler wall)
        boxs = createBox(newVector(1, 4.5f, 1), defMaterial);
        boxs.attachBox(newVector(-48.5f, -66.5f, -139), 0f);
        addToPhysics(boxs);

        // Right left straight straight (end blocker wall)
        boxs = createBox(newVector(8, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(-39.5f, -85.5f, -139), 0f);
        addToPhysics(boxs);

        // Right left straight straight (small filler wall)
        boxs = createBox(newVector(1, 4.5f, 1), defMaterial);
        boxs.attachBox(newVector(-30.5f, -66.5f, -139), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(11, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-120.5f, -80.5f, -157), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(11, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-142.5f, -80.5f, -157), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-120.5f, -80.5f, -134), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(49, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-142.5f, -80.5f, -95), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(39, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-179, -80.5f, -85), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (end wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-190, -80.5f, -124), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(39.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-160.75f, -80.5f, -25), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (left wall)
        boxs = createBox(newVector(17.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-160.75f, -80.5f, -47), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(39, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-120.5f, -80.5f, -65), 0f);
        addToPhysics(boxs);

        // Right left straight straight (small filler wall)
        boxs = createBox(newVector(1, 4.5f, 1), defMaterial);
        boxs.attachBox(newVector(-142.5f, -66.5f, -145), 0f);
        addToPhysics(boxs);

        // Right left straight straight (end blocker wall)
        boxs = createBox(newVector(10, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(-131.5f, -85.5f, -145), 0f);
        addToPhysics(boxs);

        // Right left straight straight (small filler wall)
        boxs = createBox(newVector(1, 4.5f, 1), defMaterial);
        boxs.attachBox(newVector(-120.5f, -66.5f, -145), 0f);
        addToPhysics(boxs);

        // Right left straight straight (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-101.5f, -95, -125), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(8.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-111f, -80.5f, -125), 0f);
        addToPhysics(boxs);

        // Right left straight straight straight (right wall)
        boxs = createBox(newVector(8.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-111f, -80.5f, -103), 0f);
        addToPhysics(boxs);

        // Right straight left (left wall)
        boxs = createBox(newVector(38, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-79.5f, -80.5f, -86f), 0f);
        addToPhysics(boxs);

        // Right straight left (left wall)
        boxs = createBox(newVector(38, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-101.5f, -80.5f, -64), 0f);
        addToPhysics(boxs);

        // Right straight (left wall)
        boxs = createBox(newVector(16.5f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-64f, -80.5f, -47f), 0f);
        addToPhysics(boxs);

        // Right left (small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-48.5f, -95, -77f), 0f);
        addToPhysics(boxs);

        // Right left (end blocker)
        boxs = createBox(newVector(8, 14.5f, 1), defMaterial);
        boxs.attachBox(newVector(-39.5f, -76.5f, -77f), 0f);
        addToPhysics(boxs);

        // Right left(small filler wall)
        boxs = createBox(newVector(1, 4, 1), defMaterial);
        boxs.attachBox(newVector(-30.5f, -95, -77f), 0f);
        addToPhysics(boxs);

        // Right left (right wall)
        boxs = createBox(newVector(14, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-48.5f, -80.5f, -62f), 0f);
        addToPhysics(boxs);

        // Right left (left wall)
        boxs = createBox(newVector(14, 18.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-30.5f, -80.5f, -62f), 0f);
        addToPhysics(boxs);

        // Right/straight (right wall)
        boxs = createBox(newVector(44.25f, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-56.25f, -80.5f, -25), 0f);
        addToPhysics(boxs);

        // Right straight left (back wall)
        boxs = createBox(newVector(10, 18.5f, 1), defMaterial);
        boxs.attachBox(newVector(-90.5f, -80.5f, -125f), 0f);
        addToPhysics(boxs);

        // Right straight left (end blocker)
        boxs = boxs = createBox(newVector(11, 14.5f, 1), defMaterial);
        boxs.rotate(rot90y);
        boxs.attachBox(newVector(-101.5f, -76.5f, -113), 0f);
        addToPhysics(boxs);
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
     * Function to create and return the new box
     *
     * @param scale: the scale of the box
     * @param mat: the material of the box
     * @return the PuzzleBox being created
     */
    public PuzzleBox createBox(Vector3f scale, Material mat) {
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
}
