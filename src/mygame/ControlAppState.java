package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.asset.TextureKey;
import com.jme3.texture.Texture;
import static mygame.Button.pressed;
import static mygame.EvilRoom.boxs;
import static mygame.EvilRoom.defMaterial;
import static mygame.GravityPuzzleLevel.box1;
import static mygame.GravityPuzzleLevel.box2;
import static mygame.GravityPuzzleLevel.box3;
import static mygame.GravityPuzzleLevel.boxZoneBlock;
import static mygame.GravityPuzzleLevel.boxZoneBlock2;
import static mygame.GravityPuzzleLevel.boxZoneBlock3;
import static mygame.PuzzleProject.activeCoordinates;
import static mygame.PuzzleProject.activeRoom;
import static mygame.PuzzleProject.boxHeld2;
import static mygame.PuzzleProject.boxZone;
import static mygame.PuzzleProject.boxZone2;
import static mygame.PuzzleProject.boxZone3;
import static mygame.PuzzleProject.boxZoneTut;
import static mygame.TutorialLevel.tutBoxMove2;
import static mygame.TutorialLevel.abTutBoxMove;
import static mygame.TutorialLevel.tutBoxMove;
import static mygame.TutorialLevel.tutBoxZone;
import static mygame.PuzzleProject.evilButton;
import static mygame.EvilRoom.pressEvilButton;

/**
 *
 */
public class ControlAppState extends AbstractAppState implements ActionListener {

    // Variable Declaration
    private SimpleApplication app;
    private String currentGrav = "Down", name;
    Material matOrb;
    private Node shootables, inventory;
    private boolean left = false, right = false, up = false, down = false,
            crouch = false, sprint = false, jumpBool = false, boxHeld = false,
            normGrav = false, abGrav = true, gravy = true, gravMove = false, buttonHasMoved = false, didUpdatePlatforms = false;
    protected Geometry moveBox;
    protected Geometry moveBox2;
    private RigidBodyControl boxPhy;
    private RigidBodyControl boxPhy2;

    private float sprint2 = 1f, crouch2 = 1f, lockCam = 1.5f, currGrav = -60, camPosition = 3.2f;
    private double distanceX, distanceY, distanceZ;
    private int jumpCounter = 0;

    AssetManager assetManager;
    private BulletAppState bulletAppState;

    private Button b1;
    private CharacterControl player, player2;

    private Vector3f crouchVector = new Vector3f();

    private RoomAppState roomAppState;
    private RoomAppState[] rooms;
    private float[][] roomDoorLocs;
    private float playerPosition;
    private int prevRoom = -1;
    private boolean didMoveBox = false;

    private BitmapFont guiFont;
    private AppSettings settings;

    /**
     * Constructor creating ControlAppState
     *
     * @param f BitmapFont variable for initialization
     * @param s Settings variable for initialization
     */
    public ControlAppState(BitmapFont f, AppSettings s, AssetManager assetManager) {
        guiFont = f;
        settings = s;
        this.assetManager = assetManager;
    }

    /**
     * Constructor for initializing ControlAppState & variables
     *
     * @param stateManager AppStateManager from main file
     * @param app Application from main file
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;// cast to a more specific class
        bulletAppState = stateManager.getState(BulletAppState.class);
        roomAppState = stateManager.getState(RoomAppState.class);

        setUpKeys();
        createPlayer();
        createButton();
        createShootables();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        // Call custom methods...
    }

    /**
     * Method for enabling/disabling of ControlAppState
     *
     * @param enabled boolean T to enable or F to disable
     */
    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
//        if (enabled) {
//        }
    }

    /**
     * Function to map a key to a string variable to be used to perform a
     * specific task later
     */
    private void setUpKeys() {
        this.app.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        this.app.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        this.app.getInputManager().addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        this.app.getInputManager().addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        this.app.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));

        this.app.getInputManager().addMapping("Crouch", new KeyTrigger(KeyInput.KEY_LCONTROL));
        this.app.getInputManager().addMapping("Sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));

        this.app.getInputManager().addMapping("Press", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        this.app.getInputManager().addMapping("PickUp", new KeyTrigger(KeyInput.KEY_E));

        this.app.getInputManager().addMapping("FlipGrav", new KeyTrigger(KeyInput.KEY_R));

        this.app.getInputManager().addListener(this, "Left");
        this.app.getInputManager().addListener(this, "Right");
        this.app.getInputManager().addListener(this, "Up");
        this.app.getInputManager().addListener(this, "Down");
        this.app.getInputManager().addListener(this, "Jump");

        this.app.getInputManager().addListener(this, "Crouch");
        this.app.getInputManager().addListener(this, "Sprint");

        this.app.getInputManager().addListener(this, "Press");
        this.app.getInputManager().addListener(this, "PickUp");

        this.app.getInputManager().addListener(this, "FlipGrav");
    }

    /**
     * Function to determine what button was pressed, and performs the specific
     * task that button refers to
     *
     * @param binding the string representing the key press the user hit
     * @param isPressed boolean that will be used to set certain variables as
     * true
     * @param tpf float variable in case a times per frame (tpf) variable is
     * needed
     */
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right = isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down")) {
            down = isPressed;
        } else if (binding.equals("Jump") && isPressed) {
            if (jumpCounter > 2) {
                float jumpNum = 20;
                //Jump in currentGrav direction
                if (currentGrav.equals("Down")) {
                    jumpNum = 20f;
                } else if (currentGrav.equals("Up")) {
                    jumpNum = -20f;
                }
                //Either jump active capsule (standing or crouching)
                if (player.isEnabled() && player.onGround()) {
                    player.jump(new Vector3f(0, jumpNum, 0));
                }
                if (player2.isEnabled() && player2.onGround()) {
                    player2.jump(new Vector3f(0, jumpNum, 0));
                }
            }
            jumpCounter = 0;
            jumpBool = true;
        } else if (binding.equals("Crouch") && isPressed) {
            //Toggles to either crouch or stand player depending on which is active
            if (player.isEnabled() && player.onGround()) {
                Crouch(player);
                crouch = true;
            } else if (player2.isEnabled() && player2.onGround()) {
                Stand(player2);
                crouch = false;
            }
        } else if (binding.equals("Sprint")) {
            if (jumpCounter > 2) {
                sprint = isPressed;
            } else {
                sprint = false;
            }
        } else if (binding.equals("FlipGrav") && (player.onGround() || player2.onGround()) && (activeRoom == 0 || activeRoom == 2 || activeRoom == 3 || activeRoom == 4)) {
            //Swaps current gravity to opposite depending on current orientation,
            // if on ground, and which room is active
            if (currentGrav.equalsIgnoreCase("Down") && jumpCounter > 2) {
                player.setGravity(new Vector3f(0, 60f, 0));
                player2.setGravity(new Vector3f(0, 60f, 0));
                currGrav = 60;
                gravy = false;
                gravMove = true;
            } else if (currentGrav.equalsIgnoreCase("Up") && jumpCounter > 2) {
                player.setGravity(new Vector3f(0, -60f, 0));
                player2.setGravity(new Vector3f(0, -60f, 0));
                currGrav = -60;
                gravy = true;
                gravMove = true;
            }

        } else if (binding.equals("PickUp") && !isPressed && !boxHeld && !boxHeld2) {
            // E to pick up block
            CollisionResults results2 = new CollisionResults();
            Ray ray2 = new Ray(this.app.getCamera().getLocation(), this.app.getCamera().getDirection());
            shootables.collideWith(ray2, results2);

            if (results2.size() > 0 && results2.getClosestCollision().getGeometry().getName() == "moveBox") {
                distanceX = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().x - this.app.getCamera().getLocation().x, 2);
                distanceY = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().y - this.app.getCamera().getLocation().y, 2);
                distanceZ = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().z - this.app.getCamera().getLocation().z, 2);
                if (inventory.getChildren().isEmpty() && Math.sqrt(distanceX + distanceY + distanceZ) <= 10) {
                    results2.getCollision(0).getGeometry().removeControl(boxPhy);
                    bulletAppState.getPhysicsSpace().remove(boxPhy);
                    shootables.detachChild(results2.getCollision(0).getGeometry());
                    inventory.attachChild(results2.getCollision(0).getGeometry());
                    name = inventory.getChild(0).getName();
                    inventory.getChild(name).setLocalTranslation(this.app.getCamera().getLocation().x + (this.app.getCamera().getDirection().x * 5), this.app.getCamera().getLocation().y, this.app.getCamera().getLocation().z + (this.app.getCamera().getDirection().z * 5));
                    boxHeld = true;
                }
            } else if (results2.size() > 0 && results2.getClosestCollision().getGeometry().getName() == "moveBox2") {
                distanceX = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().x - this.app.getCamera().getLocation().x, 2);
                distanceY = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().y - this.app.getCamera().getLocation().y, 2);
                distanceZ = Math.pow(results2.getCollision(0).getGeometry().getLocalTranslation().z - this.app.getCamera().getLocation().z, 2);
                if (inventory.getChildren().isEmpty() && Math.sqrt(distanceX + distanceY + distanceZ) <= 10) {
                    results2.getCollision(0).getGeometry().removeControl(boxPhy2);
                    bulletAppState.getPhysicsSpace().remove(boxPhy2);
                    shootables.detachChild(results2.getCollision(0).getGeometry());
                    inventory.attachChild(results2.getCollision(0).getGeometry());
                    name = inventory.getChild(0).getName();
                    inventory.getChild(name).setLocalTranslation(this.app.getCamera().getLocation().x + (this.app.getCamera().getDirection().x * 5), this.app.getCamera().getLocation().y, this.app.getCamera().getLocation().z + (this.app.getCamera().getDirection().z * 5));
                    boxHeld2 = true;
                }
            }
        } else if (binding.equals("PickUp") && !isPressed && boxHeld) {
            if (!inventory.getChildren().isEmpty()) {
                inventory.getChild(name).setLocalTranslation(this.app.getCamera().getLocation().x + (this.app.getCamera().getDirection().x * 5), this.app.getCamera().getLocation().y + (this.app.getCamera().getDirection().y * 2.5f), this.app.getCamera().getLocation().z + (this.app.getCamera().getDirection().z * 5));
                shootables.attachChild(inventory.getChild(name));
                inventory.detachAllChildren();
                boxPhy = new RigidBodyControl(1f);
                moveBox = (Geometry) shootables.getChild(name);
                moveBox.addControl(boxPhy);
                bulletAppState.getPhysicsSpace().add(boxPhy);
                boxPhy.setGravity(new Vector3f(0, -50, 0));
                boxHeld = false;
            }
        } else if (binding.equals("PickUp") && !isPressed && boxHeld2) {
            if (!inventory.getChildren().isEmpty()) {
                inventory.getChild(name).setLocalTranslation(this.app.getCamera().getLocation().x + (this.app.getCamera().getDirection().x * 5), this.app.getCamera().getLocation().y + (this.app.getCamera().getDirection().y * 2.5f), this.app.getCamera().getLocation().z + (this.app.getCamera().getDirection().z * 5));
                shootables.attachChild(inventory.getChild(name));
                inventory.detachAllChildren();
                boxPhy2 = new RigidBodyControl(1f);
                moveBox2 = (Geometry) shootables.getChild(name);
                moveBox2.addControl(boxPhy2);
                bulletAppState.getPhysicsSpace().add(boxPhy2);
                boxPhy2.setGravity(new Vector3f(0, -50, 0));
                boxHeld2 = false;
            }
        } else if (binding.equals("Press") && !isPressed) {
            CollisionResults results = new CollisionResults();
            Ray ray = new Ray(this.app.getCamera().getLocation(), this.app.getCamera().getDirection());
            int click = b1.returnButtonGeo().collideWith(ray, results);
            if (click == 2 && results.getCollision(1).getDistance() < 15) {
                b1.isPressed();
            }
        } /*else if (binding.equals("Toggle")) {
            if (isPressed) {
                if (!roomAppState.getEnabled()) {
                    roomAppState.setEnabled(true);
                } else {
                    roomAppState.setEnabled(false);
                }
            }
        }*/
    }

    /**
     * Method that runs every frame, checking for key inputs -Note that update
     * is only called while the state is both attached and enabled
     *
     * @param tpf variable to have consistent change differing fps
     */
    @Override
    public void update(float tpf) {
        //Calls button-based onUpdate
        buttonUpdate();
        activeRoom = checkCurrRoom();
        normGrav = gravy;

        //if grav value switches true to false, or vice versa, change gravity
        // and orientation appropriately
        if (abGrav == false && normGrav == true) {
            this.app.getFlyByCamera().setUpVector(new Vector3f(0, 1, 0));
            lockCam = 1.5f;
            this.app.getCamera().getRotation().set(this.app.getCamera().getRotation().getY(), this.app.getCamera().getRotation().getX(), this.app.getCamera().getRotation().getW(), this.app.getCamera().getRotation().getZ());
            currentGrav = "Down";
        } else if (abGrav == true && normGrav == false) {
            this.app.getFlyByCamera().setUpVector(new Vector3f(0, -1, 0));
            lockCam = -1.5f;
            this.app.getCamera().getRotation().set(this.app.getCamera().getRotation().getY(), this.app.getCamera().getRotation().getX(), this.app.getCamera().getRotation().getW(), this.app.getCamera().getRotation().getZ());
            currentGrav = "Up";
        }

        //if boxes haven't been moved to new location, update to new room
        //this is because one box is used in tutorial room, so we move it afterwards
        if (didMoveBox == false && activeRoom == 2) {
            moveBox.removeControl(boxPhy);
            bulletAppState.getPhysicsSpace().remove(boxPhy);
            moveBox.setLocalTranslation(new Vector3f(-60f, -5f, -650f));
            boxPhy = new RigidBodyControl(1f);
            moveBox = (Geometry) shootables.getChild(name);
            moveBox.addControl(boxPhy);
            bulletAppState.getPhysicsSpace().add(boxPhy);
            boxPhy.setGravity(new Vector3f(0, -50, 0));
            didMoveBox = true;
            boxZone = (new Vector3f(boxZone.x, boxZone.y, (boxZone.z) + (activeCoordinates.z)));
            boxZone2 = (new Vector3f(boxZone2.x, boxZone2.y, (boxZone2.z) + (activeCoordinates.z)));
            boxZone3 = (new Vector3f(boxZone3.x, boxZone3.y, (boxZone3.z) + (activeCoordinates.z)));
        }

        abGrav = gravy;

        // If "player" (regular-sized collisionshape) is active, send it as a parameter for camera position, etc
        // Else if "player2" (crouch-sized collisionshape) is active, send that instead
        if (player.isEnabled()) {
            OnUpdate(player);
        } else if (player2.isEnabled()) {
            OnUpdate(player2);
        }

        //if in gravityPuzzleLevel, check moving platforms
        if (activeRoom == 2) {
            //moving blocks 1
            if ((!boxHeld && moveBox.getLocalTranslation().x < boxZone.x + 2 && moveBox.getLocalTranslation().x > boxZone.x - 2 && moveBox.getLocalTranslation().z < boxZone.z + 2 && moveBox.getLocalTranslation().z > boxZone.z - 2 && moveBox.getLocalTranslation().y >= boxZone.y && moveBox.getLocalTranslation().y <= boxZone.y + 3) || (!boxHeld2 && moveBox2.getLocalTranslation().x < boxZone.x + 2 && moveBox2.getLocalTranslation().x > boxZone.x - 2 && moveBox2.getLocalTranslation().z < boxZone.z + 2 && moveBox2.getLocalTranslation().z > boxZone.z - 2 && moveBox2.getLocalTranslation().y >= boxZone.y && moveBox2.getLocalTranslation().y <= boxZone.y + 3)) {
                box1.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box1.getSpatialControl());
                box1.zoneMoving(30, 'x', "left");
                box1.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box1.getSpatialControl());

                if (boxZoneBlock.getTex().getName().equals("Materials/NormInteract.png")) {
                    boxZoneBlock.setTexture(assetManager.loadTexture(new TextureKey("Materials/InteractedMat.png")));
                }
            } else {
                box1.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box1.getSpatialControl());
                box1.zoneMoving(65, 'x', "right");
                box1.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box1.getSpatialControl());

                if (boxZoneBlock.getTex().getName().equals("Materials/InteractedMat.png")) {
                    boxZoneBlock.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));
                }
            }
            //moving blocks 2
            if ((!boxHeld && moveBox.getLocalTranslation().x < boxZone2.x + 2 && moveBox.getLocalTranslation().x > boxZone2.x - 2 && moveBox.getLocalTranslation().z < boxZone2.z + 2 && moveBox.getLocalTranslation().z > boxZone2.z - 2 && moveBox.getLocalTranslation().y >= boxZone2.y && moveBox.getLocalTranslation().y <= boxZone2.y + 3) || (!boxHeld2 && moveBox2.getLocalTranslation().x < boxZone2.x + 2 && moveBox2.getLocalTranslation().x > boxZone2.x - 2 && moveBox2.getLocalTranslation().z < boxZone2.z + 2 && moveBox2.getLocalTranslation().z > boxZone2.z - 2 && moveBox2.getLocalTranslation().y >= boxZone2.y && moveBox2.getLocalTranslation().y <= boxZone2.y + 3)) {
                box2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box2.getSpatialControl());
                box2.zoneMoving(-5, 'x', "left");
                box2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box2.getSpatialControl());

                box3.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box3.getSpatialControl());
                box3.zoneMoving(-25, 'x', "left");
                box3.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box3.getSpatialControl());

                if (boxZoneBlock2.getTex().getName().equals("Materials/NormInteract.png")) {
                    boxZoneBlock2.setTexture(assetManager.loadTexture(new TextureKey("Materials/InteractedMat.png")));
                }
            } else {
                box2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box2.getSpatialControl());
                box2.zoneMoving(75, 'x', "right");
                box2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box2.getSpatialControl());

                box3.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box3.getSpatialControl());
                box3.zoneMoving(55, 'x', "right");
                box3.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box3.getSpatialControl());

                if (boxZoneBlock2.getTex().getName().equals("Materials/InteractedMat.png")) {
                    boxZoneBlock2.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));
                }
            }
            //moving blocks 3
            if ((!boxHeld && moveBox.getLocalTranslation().x < boxZone3.x + 2 && moveBox.getLocalTranslation().x > boxZone3.x - 2 && moveBox.getLocalTranslation().z < boxZone3.z + 2 && moveBox.getLocalTranslation().z > boxZone3.z - 2 && moveBox.getLocalTranslation().y >= boxZone3.y && moveBox.getLocalTranslation().y <= boxZone3.y + 3) || (!boxHeld2 && moveBox2.getLocalTranslation().x < boxZone3.x + 2 && moveBox2.getLocalTranslation().x > boxZone3.x - 2 && moveBox2.getLocalTranslation().z < boxZone3.z + 2 && moveBox2.getLocalTranslation().z > boxZone3.z - 2 && moveBox2.getLocalTranslation().y >= boxZone3.y && moveBox2.getLocalTranslation().y <= boxZone3.y + 3)) {
                box2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box2.getSpatialControl());
                box2.zoneMoving(-30, 'z', "back");
                box2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box2.getSpatialControl());

                box3.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box3.getSpatialControl());
                box3.zoneMoving(-70, 'z', "back");
                box3.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box3.getSpatialControl());

                if (boxZoneBlock3.getTex().getName().equals("Materials/NormInteract.png")) {
                    boxZoneBlock3.setTexture(assetManager.loadTexture(new TextureKey("Materials/InteractedMat.png")));
                }
            } else {
                box2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box2.getSpatialControl());
                box2.zoneMoving(-80, 'z', "forward");
                box2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box2.getSpatialControl());

                box3.removeCollision();
                bulletAppState.getPhysicsSpace().remove(box3.getSpatialControl());
                box3.zoneMoving(-120, 'z', "forward");
                box3.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(box3.getSpatialControl());

                if (boxZoneBlock3.getTex().getName().equals("Materials/InteractedMat.png")) {
                    boxZoneBlock3.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));
                }
            }
        }

        //If tutorial room is active
        if (activeRoom == 0) {
            //move platform if box is on boxZone, else move back to original location
            if ((!boxHeld && moveBox.getLocalTranslation().x < boxZoneTut.x + 2 && moveBox.getLocalTranslation().x > boxZoneTut.x - 2 && moveBox.getLocalTranslation().z < boxZoneTut.z + 2 && moveBox.getLocalTranslation().z > boxZoneTut.z - 2 && moveBox.getLocalTranslation().y >= boxZoneTut.y && moveBox.getLocalTranslation().y <= boxZoneTut.y + 3)
                    || (!boxHeld2 && moveBox2.getLocalTranslation().x < boxZoneTut.x + 1.5 && moveBox2.getLocalTranslation().x > boxZoneTut.x - 2 && moveBox2.getLocalTranslation().z < boxZoneTut.z + 2 && moveBox2.getLocalTranslation().z > boxZoneTut.z - 2 && moveBox2.getLocalTranslation().y >= boxZoneTut.y && moveBox2.getLocalTranslation().y <= boxZoneTut.y + 3)) {
                //if boxZone texture is normal, change back to interacted
                if (tutBoxZone.getTex().getName().equals("Materials/NormInteract.png")) {
                    tutBoxZone.setTexture(assetManager.loadTexture(new TextureKey("Materials/InteractedMat.png")));
                }
                abTutBoxMove.removeCollision();
                bulletAppState.getPhysicsSpace().remove(abTutBoxMove.getSpatialControl());
                abTutBoxMove.zoneMoving(-97.5f, 'z', "forward");
                abTutBoxMove.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(abTutBoxMove.getSpatialControl());
            } else {
                //if boxZone texture is interacted, change back to normal
                if (tutBoxZone.getTex().getName().equals("Materials/InteractedMat.png")) {
                    tutBoxZone.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));
                }
                abTutBoxMove.removeCollision();
                bulletAppState.getPhysicsSpace().remove(abTutBoxMove.getSpatialControl());
                abTutBoxMove.zoneMoving(-47.5f, 'z', "back");
                abTutBoxMove.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(abTutBoxMove.getSpatialControl());
            }
        }

        // If the box is being held, show the box in front of the player
        if (boxHeld) {
            // Gets the difference in location from player to box
            Vector3f vectorDifference = new Vector3f(
                    (this.app.getCamera().getLocation().x) - (moveBox.getLocalTranslation().x),
                    (this.app.getCamera().getLocation().y) - (moveBox.getLocalTranslation().y),
                    (this.app.getCamera().getLocation().z) - (moveBox.getLocalTranslation().z)
            );
            //Updates box location according to player position && rotation
            moveBox.setLocalTranslation(vectorDifference.addLocal(moveBox.getLocalTranslation()));
            Quaternion worldDiff = new Quaternion(this.app.getCamera().getRotation().mult(moveBox.getWorldRotation().inverse()));
            moveBox.setLocalRotation(worldDiff.multLocal(moveBox.getLocalRotation()));
            moveBox.move(this.app.getCamera().getDirection().mult(3));
            moveBox.move(this.app.getCamera().getUp().mult(-1f));
            moveBox.rotate(0.3f, FastMath.PI, 0);
        }
        // If the other box is being held, show the box in front of the player
        if (boxHeld2) {
            // Gets the difference in location from player to box
            Vector3f vectorDifference = new Vector3f(
                    (this.app.getCamera().getLocation().x) - (moveBox2.getLocalTranslation().x),
                    (this.app.getCamera().getLocation().y) - (moveBox2.getLocalTranslation().y),
                    (this.app.getCamera().getLocation().z) - (moveBox2.getLocalTranslation().z)
            );
            //Updates box location according to player position && rotation
            moveBox2.setLocalTranslation(vectorDifference.addLocal(moveBox2.getLocalTranslation()));
            Quaternion worldDiff = new Quaternion(this.app.getCamera().getRotation().mult(moveBox2.getWorldRotation().inverse()));
            moveBox2.setLocalRotation(worldDiff.multLocal(moveBox2.getLocalRotation()));
            moveBox2.move(this.app.getCamera().getDirection().mult(3));
            moveBox2.move(this.app.getCamera().getUp().mult(-1f));
            moveBox2.rotate(0.3f, FastMath.PI, 0);
        }
    }

    /**
     * Function for update-based player movement, depending on if Crouching or
     * Standing
     *
     * @param player CharacterController, either "Standing" or "Crouching"
     * capsule shape
     */
    public void OnUpdate(CharacterControl player) {
        //if the player falls to high/low, resets position and box positions to correct coordinates
        if (player.getPhysicsLocation().y < -40 || player.getPhysicsLocation().y > 40) {
            player.setPhysicsLocation(activeCoordinates);
            this.player.setLinearVelocity(new Vector3f(0, 0, 0));
            player2.setLinearVelocity(new Vector3f(0, 0, 0));
            if (currentGrav.equalsIgnoreCase("Up")) {
                this.app.getCamera().getRotation().set(1, 0, 0, 0);
                this.player.setGravity(new Vector3f(0, -60f, 0));
                player2.setGravity(new Vector3f(0, -60f, 0));
                currGrav = -60;
                gravy = true;

            } else {
                this.app.getCamera().getRotation().set(0, 1, 0, 0);
            }
            if (boxHeld) {
                boxHeld = false;
            }
            if (boxHeld2) {
                boxHeld2 = false;
            }

            if (activeRoom == 0) {
                moveBox.removeControl(boxPhy);
                bulletAppState.getPhysicsSpace().remove(boxPhy);
                moveBox.setLocalTranslation(new Vector3f(-40f, 0f, tutBoxMove.getLocalTranslation().z - 150));
                boxPhy = new RigidBodyControl(1f);
                moveBox.addControl(boxPhy);
                bulletAppState.getPhysicsSpace().add(boxPhy);
                shootables.attachChild(moveBox);
            } else if (activeRoom == 2) {
                //if activeRoom is gravityRoom
                moveBox.removeControl(boxPhy);
                bulletAppState.getPhysicsSpace().remove(boxPhy);
                moveBox.setLocalTranslation(new Vector3f(-60f, -5f, -650f));
                boxPhy = new RigidBodyControl(1f);
                moveBox.addControl(boxPhy);
                bulletAppState.getPhysicsSpace().add(boxPhy);
                shootables.attachChild(moveBox);

                moveBox2.removeControl(boxPhy2);
                bulletAppState.getPhysicsSpace().remove(boxPhy2);
                moveBox2.setLocalTranslation(new Vector3f(75f, 15f, -835f));
                boxPhy2 = new RigidBodyControl(1f);
                moveBox2.addControl(boxPhy2);
                bulletAppState.getPhysicsSpace().add(boxPhy2);
                shootables.attachChild(moveBox2);

            }
        }
        //Adds to jumpCounter while player is on ground
        // (can only jump when jumpCounter > 2)
        if (player.onGround()) {
            jumpCounter++;
        } else {
            jumpCounter = 0;
        }

        //If jumpCounter reaches 3,
        // move while falling variable set to false
        // and variable to jumping set to false
        if (jumpCounter == 3) {
            gravMove = false;
            jumpBool = false;
            this.player.setLinearVelocity(new Vector3f(0, 0, 0));
            player2.setLinearVelocity(new Vector3f(0, 0, 0));
        }

        //move player based on gravity and orientation
        if (currentGrav.equals("Down")) {
            if (this.app.getCamera().getUp().y < 0) {
                this.app.getCamera().lookAtDirection(new Vector3f(0, this.app.getCamera().getDirection().y, 0), new Vector3f(this.app.getCamera().getUp().x, 0, this.app.getCamera().getUp().z));
            }
        } else if (currentGrav.equals("Up")) {
            if (this.app.getCamera().getUp().y > 0) {
                this.app.getCamera().lookAtDirection(new Vector3f(0, this.app.getCamera().getDirection().y, 0), new Vector3f(this.app.getCamera().getUp().x, 0, this.app.getCamera().getUp().z));
            }
        }

        //multiply speed based on sprinting or crouching
        if (sprint) {
            sprint2 = 2f;
        } else {
            sprint2 = 1f;
        }
        if (crouch) {
            crouch2 = .5f;
        } else {
            crouch2 = 1f;
        }

        Vector3f camLeft = new Vector3f(this.app.getCamera().getLeft()).multLocal(0.4f);
        Vector3f walkDirection = new Vector3f(0, 0, 0);

        //set number to multiply diagonal movement by,
        // making equivalent speed in each direction
        float num;
        if ((up || down) && (left || right)) {
            num = (float) Math.sqrt(2) / 2;
        } else {
            num = 1;
        }

        //set movement direction if not falling
        if (left && (gravMove == false)) {
            walkDirection.addLocal(new Vector3f(.5f * num * camLeft.x * crouch2, 0, .5f * num * camLeft.z * crouch2));
        }
        if (right && (gravMove == false)) {
            walkDirection.addLocal(new Vector3f(.5f * num * camLeft.x * crouch2, 0, .5f * num * camLeft.z * crouch2).negate());
        }
        if (up && (gravMove == false)) {
            walkDirection.addLocal(new Vector3f(.5f * num * (camLeft.z * -lockCam) * sprint2 * crouch2, 0, .5f * num * (camLeft.x * lockCam) * sprint2 * crouch2));
        }
        if (down && (gravMove == false)) {
            walkDirection.addLocal(new Vector3f(.5f * num * (camLeft.z * -lockCam) * crouch2, 0, .5f * num * (camLeft.x * lockCam) * crouch2).negate());
        }

        //apply movement velocity if not jumping
        if (jumpBool == false) {
            player.setWalkDirection(walkDirection);
        }

        //set camera of player, depending on standing or croucing
        CrouchCam(player);
    }

    /**
     * Function to "crouch" the player
     *
     * @param player the "Standing" capsule seeking to crouch
     */
    public void Crouch(CharacterControl player) {
        //gets current location of player & removes it
        crouchVector = player.getPhysicsLocation();
        CapsuleCollisionShape capsuleShape2 = new CapsuleCollisionShape(1.5f, 3, 1);
        bulletAppState.getPhysicsSpace().remove(player);
        player.setEnabled(false);
        player.destroy();

        //creates new "crouching" player
        player2 = new CharacterControl(capsuleShape2, 0.05f);
        player2.setJumpSpeed(40);
        player2.setFallSpeed(60);

        //set location based on gravity
        if (currentGrav.equals("Down")) {
            player2.setPhysicsLocation(new Vector3f(crouchVector.x, crouchVector.y - 1.5f, crouchVector.z));//0 130 0
            player2.setGravity(new Vector3f(0, -60f, 0));
        } else if (currentGrav.equals("Up")) {
            player2.setPhysicsLocation(new Vector3f(crouchVector.x, crouchVector.y + 1.5f, crouchVector.z));//0 130 0
            player2.setGravity(new Vector3f(0, 60f, 0));
        }
        player2.setContactResponse(true);

        //enable new player
        player2.setEnabled(true);
        bulletAppState.getPhysicsSpace().add(player2);
        jumpCounter = 0;
        sprint = false;
    }

    /**
     * Function to "stand" the player
     *
     * @param player2 the "Crouching" capsule seeking to stand
     */
    public void Stand(CharacterControl player2) {
        //gets current location of player & removes it
        crouchVector = player2.getPhysicsLocation();
        CapsuleCollisionShape capsuleShape3 = new CapsuleCollisionShape(1.5f, 6f, 1);
        bulletAppState.getPhysicsSpace().remove(player2);
        player2.setEnabled(false);
        player2.destroy();

        //creates new "standing" player
        player = new CharacterControl(capsuleShape3, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);

        //set location based on gravity
        if (currentGrav.equals("Down")) {
            player.setPhysicsLocation(new Vector3f(crouchVector.x, crouchVector.y + 1.5f, crouchVector.z));//0 130 0
            player.setGravity(new Vector3f(0, -60f, 0));
        } else if (currentGrav.equals("Up")) {
            player.setPhysicsLocation(new Vector3f(crouchVector.x, crouchVector.y - 1.5f, crouchVector.z));//0 130 0
            player.setGravity(new Vector3f(0, 60f, 0));
        }
        player.setContactResponse(true);

        //enable new player
        player.setEnabled(true);
        bulletAppState.getPhysicsSpace().add(player);
        jumpCounter = 0;
        sprint = false;
    }

    /**
     * Function to adjust the camera to the eye level the "Standing" or
     * "Crouching" player capsule
     *
     * @param player CharacterController, either "Standing" or "Crouching"
     * capsule shape
     */
    public void CrouchCam(CharacterControl player) {
        //set cam position based on crouching
        if (crouch) {
            camPosition = 1.7f;
        } else {
            camPosition = 3.2f;
        }
        //applies cam position based on gravity
        if (currentGrav.equals("Down")) {
            this.app.getCamera().setLocation(new Vector3f(player.getPhysicsLocation().x, player.getPhysicsLocation().y + camPosition, player.getPhysicsLocation().z));
        } else if (currentGrav.equals("Up")) {
            this.app.getCamera().setLocation(new Vector3f(player.getPhysicsLocation().x, player.getPhysicsLocation().y - camPosition, player.getPhysicsLocation().z));
        }
    }

    /**
     * Function to initialize the player
     */
    public void createPlayer() {
        //create "standing" capsule
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(40);
        player.setFallSpeed(60);
        player.setGravity(new Vector3f(0, -60f, 0));
        player.setPhysicsLocation(new Vector3f(0, 0, 0));//0 130 0
        player.setContactResponse(true);

        //create "crouching" capsule
        CapsuleCollisionShape capsuleShape2 = new CapsuleCollisionShape(1.5f, 3f, 1);
        player2 = new CharacterControl(capsuleShape2, 0.05f);
        player2.setJumpSpeed(40);
        player2.setFallSpeed(60);
        player2.setGravity(new Vector3f(0, -60f, 0));
        player2.setPhysicsLocation(new Vector3f(crouchVector.x, crouchVector.y - 2.3f, crouchVector.z));//0 130 0
        player2.setContactResponse(true);

        //disable crouching player
        player2.setEnabled(false);
        bulletAppState.getPhysicsSpace().add(player2);

        bulletAppState.getPhysicsSpace().add(player);
    }

    /**
     * Function to create the cross hairs where the user's mouse will point
     */
    protected void initCrossHairs() {
        this.app.setDisplayStatView(false);
        guiFont = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        this.app.getGuiNode().attachChild(ch);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * Function to create a button Button requires float size, Vector3f
     * location, assetManager, and rootNode
     */
    public void createButton() {
        //Tutorial Level Button
        b1 = new Button(1.5f, new Vector3f(-55f, -7.5f, -200f), this.app.getAssetManager(), this.app.getRootNode(), 1);

//        bulletAppState.getPhysicsSpace().add(b1.returnButtonControl());
    }

    /**
     * Function to initialize the shootables Node which the boxes will be able
     * to be interacted with
     */
    public void createShootables() {

        initCrossHairs(); // a "+" in the middle of the screen to help aiming

        shootables = new Node("Shootables");
        inventory = new Node("Inventory");

        Box cube = new Box(1, 1, 1);

        matOrb = new Material(this.app.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matOrb.setColor("Color", ColorRGBA.White);

        TextureKey wallTexture = new TextureKey("Materials/BlockMat.png");
        wallTexture.setGenerateMips(true);
        Texture tex = this.app.getAssetManager().loadTexture(wallTexture);
        matOrb.setTexture("ColorMap", tex);

        moveBox = new Geometry("moveBox", cube);

        //Box Tutorial Position
        moveBox.move(new Vector3f(-40f, 0f, -280f));

        //boxRoom position
        //moveBox.move(new Vector3f(-60f,-5f,-350f)); //-300z for each nextRoom
        moveBox.setMaterial(matOrb);
        boxPhy = new RigidBodyControl(1f);
        moveBox.addControl(boxPhy);
        bulletAppState.getPhysicsSpace().add(boxPhy);
        boxPhy.setGravity(new Vector3f(0, -50, 0));

        moveBox2 = new Geometry("moveBox2", cube);
        moveBox2.move(new Vector3f(75f, 15f, -835f)); //-300z for each nextRoom
        moveBox2.setMaterial(matOrb);
        boxPhy2 = new RigidBodyControl(1f);
        moveBox2.addControl(boxPhy2);
        bulletAppState.getPhysicsSpace().add(boxPhy2);
        boxPhy2.setGravity(new Vector3f(0, -50, 0));

        shootables.attachChild(moveBox);
        shootables.attachChild(moveBox2);
        this.app.getRootNode().attachChild(inventory);
        this.app.getRootNode().attachChild(shootables);
    }

    /**
     * Function to apply the buttons effect onUpdate for each activeRoom,
     * activeRoom 0 --> TutorialRoom
     */
    public void buttonUpdate() {
        if(buttonHasMoved == false && activeRoom == 3){
            b1.destroy();
            b1 = new Button(0.5f, new Vector3f(0, 42, -1040), this.app.getAssetManager(), this.app.getRootNode(), 3);
            buttonHasMoved = true;
        }
        if (pressed == true) {
            if (activeRoom == 0) {
                tutBoxMove2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(tutBoxMove2.getSpatialControl());
                tutBoxMove2.zoneMoving(-7.5f, 'y', "up");
                tutBoxMove2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(tutBoxMove2.getSpatialControl());
            } else if (activeRoom == 3 && didUpdatePlatforms == false){
                evilButton = true;
                pressEvilButton();
                evilButton = false;
                didUpdatePlatforms = true;
            }
        }
        if (pressed == false) {
            if (activeRoom == 0) {
                tutBoxMove2.removeCollision();
                bulletAppState.getPhysicsSpace().remove(tutBoxMove2.getSpatialControl());
                tutBoxMove2.zoneMoving(-50, 'y', "down");
                tutBoxMove2.addCollision(0f);
                bulletAppState.getPhysicsSpace().add(tutBoxMove2.getSpatialControl());
            }
            else if (activeRoom == 3){
                evilButton = false;
            }
        }
    }

    /**
     * Stores the locations of entries/exits of the rooms
     */
    protected void setUpRooms() {
        rooms = PuzzleProject.getRooms();
        roomDoorLocs = new float[2][rooms.length];
        for (int i = 0; i < rooms.length; i++) {
            roomDoorLocs[0][i] = rooms[i].getEntryLocation().z;
            roomDoorLocs[1][i] = rooms[i].getExitLocation().z;
        }
    }

    /**
     * Checks which room the player is in based on the player's z-coordinate
     *
     * @return currRoom the number of the current room the player is in
     */
    private int checkCurrRoom() {
        if (player.isEnabled()) {
            playerPosition = player.getPhysicsLocation().z;
        } else if (player2.isEnabled()) {
            playerPosition = player2.getPhysicsLocation().z;
        }
        boolean playerMoved = false;

        if (activeRoom >= 0) {
            // Check if the position is outside the last-known room the player was in
            if ((playerPosition > roomDoorLocs[0][activeRoom] || playerPosition < roomDoorLocs[1][activeRoom])) {
                playerMoved = true;
                prevRoom = activeRoom; // Keep track of which room we used to be in to compare later
            } else {
                playerMoved = false;
            }
        }

        if (playerMoved) {
            for (int i = 0; i < rooms.length; i++) {
                if (playerPosition < roomDoorLocs[0][i] && playerPosition > roomDoorLocs[1][i]) {
                    activeRoom = i;
                    break;
                }
            }
            if (activeRoom > prevRoom) {
                Vector3f temp = rooms[activeRoom].getEntryLocation();
                Vector3f updatedCheckpoint = new Vector3f(temp.x, temp.y + 3, temp.z - 1);
                PuzzleProject.activeCoordinates = updatedCheckpoint;
            }
        }

//        System.out.println("Active room is " + activeRoom); // Testing purposes
        return activeRoom;
    }
}
