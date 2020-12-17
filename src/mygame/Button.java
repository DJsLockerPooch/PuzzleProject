package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Cylinder;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;

public class Button extends SimpleApplication implements ActionListener {

    private Cylinder cylinder, cylinder2;
    private Geometry button, button2;
    private Material mat, mat2;
    private RigidBodyControl buttonPhy;
    static boolean pressed;
    private final float size;
    private float size2;
    private final Vector3f location;
    private final Vector3f location2;
    private final Vector3f location3;
    private final AssetManager assetManager;
    private final Node root;
    private final BulletAppState bulletAppState;
    private int room1;

    /**
     * Creates a Button Object
     *
     * @param newSize: Rescales the button
     * @param newLocation: Determines where you want to place the button
     * @param assetManager1: Need to pass in assetManager
     * @param nodeToAttach : Need to pass in rootNode
     */
    public Button(float newSize, Vector3f newLocation, AssetManager assetManager1, Node nodeToAttach, int room) {
        this.bulletAppState = stateManager.getState(BulletAppState.class);
        this.pressed = false;
        this.assetManager = assetManager1;
        this.size = newSize;
        this.location = newLocation;
        this.location2 = new Vector3f(location.x, location.y - (size / 2), location.z);
        this.location3 = new Vector3f(location.x, location.y + (size / 2), location.z);
        this.root = nodeToAttach;
        room1 = room;

        createButton();
        addCollision();
    }

    /**
     * Returns control in order to add the button to the bulletAppState
     *
     * @return RigidBodyControl buttonphy
     */
    public RigidBodyControl returnButtonControl() {
        return buttonPhy;
    }

    /**
     * Returns the Geometry
     *
     * @return Geometry button
     */
    public Geometry returnButtonGeo() {
        return button;
    }

    /**
     * Rescales and recolors button to its "pressed" state and sets boolean
     * pressed to true if false and false if true
     */
    protected void isPressed() {
        if (pressed == false) {
            if(room1 == 1){
                buttonPhy.setPhysicsLocation(new Vector3f(location2));
            } else{
                buttonPhy.setPhysicsLocation(new Vector3f(location3));
            }
            mat.setColor("Color", ColorRGBA.Green);
            button.setMaterial(mat);
            pressed = true;
        } else {
            buttonPhy.setPhysicsLocation(location);
            mat.setColor("Color", ColorRGBA.Red);
            button.setMaterial(mat);
            pressed = false;

        }
    }

    /**
     * Function to create the button and another cylinder that surrounds the
     * button
     */
    private void createButton() {
        cylinder = new Cylinder(5, 15, 1f, 1f, true);
        button = new Geometry("Button", cylinder);
        button.scale(size);
        button.rotate(1.55f, 0, 0);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        button.setMaterial(mat);

        size2 = size * 1.25f;
        cylinder2 = new Cylinder(5, 15, 1f, 1f, true);
        button2 = new Geometry("Button", cylinder2);
        button2.scale(size2);
        button2.rotate(1.55f, 0, 0);
        mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Gray);
        button2.setMaterial(mat2);
        
    }

    /**
     * Function to add the button and the button physics to the bullet app state
     */
    private void addCollision() {
        CollisionShape buttonShape = CollisionShapeFactory.createDynamicMeshShape(button);
        buttonPhy = new RigidBodyControl(buttonShape, 0);
        button.addControl(buttonPhy);
        buttonPhy.setPhysicsLocation(location);
        if(room1 == 3){
            button2.setLocalTranslation(location.x, location.y+1, location.z);
        } else{
            button2.setLocalTranslation(location.x, location.y-1, location.z);
        }
        
        root.attachChild(button);
        root.attachChild(button2);
    }

    /**
     * Abstract method that must be implemented
     */
    @Override
    public void simpleInitApp() {
    }

    /**
     * Abstract method that must be implemented
     *
     * @param binding
     * @param isPressed
     * @param tpf
     */
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
    }
}
