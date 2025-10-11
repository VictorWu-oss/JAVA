//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;
import processing.core.PApplet;
import processing.core.PImage;

import javax.sound.sampled.Port;

public final class WorldModel {
    public static final int KEYED_IMAGE_MIN = 5;
    public static final int PROPERTY_KEY = 0;
    public static final int PROPERTY_ID = 1;
    public static final int PROPERTY_COL = 2;
    public static final int PROPERTY_ROW = 3;
    public static final int ENTITY_NUM_PROPERTIES = 4;
    public static final int COLOR_MASK = 16777215;
    public static final int KEYED_RED_IDX = 2;
    public static final int KEYED_GREEN_IDX = 3;
    public static final int KEYED_BLUE_IDX = 4;
    private int numRows;
    private int numCols;
    private Background[][] background;
    private Entity[][] occupancy;
    private Set<Entity> entities;

    public WorldModel() {
    }

    public int getRows() {
        return this.numRows;
    }

    public int getCols() {
        return this.numCols;
    }

    public Set<Entity> getEntities() {
        return this.entities;
    }

    private static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);
                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }

    }

    public static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return (List)images.computeIfAbsent(key, (k) -> new LinkedList());
    }

    private static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();

        for(int i = 0; i < img.pixels.length; ++i) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }

        img.updatePixels();
    }

    public static void loadImages(Scanner in, ImageStore imageStore, PApplet screen) {
        for(int lineNumber = 0; in.hasNextLine(); ++lineNumber) {
            try {
                processImageLine(imageStore.getImages(), in.nextLine(), screen);
            } catch (NumberFormatException var5) {
                System.out.printf("Image format error on line %d\n", lineNumber);
            }
        }

    }

    private void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Sapling.SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[Sapling.SAPLING_HEALTH_IDX]);
            Sapling entity = Sapling.createSapling(id, pt, imageStore.getImageList(Sapling.SAPLING_KEY), health);
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Sapling.SAPLING_KEY, Sapling.SAPLING_NUM_PROPERTIES));
        }
    }

    private void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Dude.DUDE_NUM_PROPERTIES) {
            DudeNotFull entity = DudeNotFull.createDudeNotFull(id, pt, Double.parseDouble(properties[Dude.DUDE_ACTION_PERIOD_IDX]), Double.parseDouble(properties[Dude.DUDE_ANIMATION_PERIOD_IDX]), Integer.parseInt(properties[Dude.DUDE_RESOURCE_LIMIT_IDX]), imageStore.getImageList("dude"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Dude.DUDE_KEY, Dude.DUDE_NUM_PROPERTIES));
        }
    }

    private void parseSuperDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DudeSuper.SUPERDUDE_NUM_PROPERTIES) {
            DudeSuper entity = DudeSuper.createDudeSuper(id, pt, Double.parseDouble(properties[DudeSuper.SUPERDUDE_ACTION_PERIOD_IDX]), Double.parseDouble(properties[DudeSuper.SUPERDUDE_ANIMATION_PERIOD_IDX]), Integer.parseInt(properties[DudeSuper.SUPERDUDE_RESOURCE_LIMIT_IDX]), imageStore.getImageList("superdude"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DudeSuper.SUPERDUDE_KEY, DudeSuper.SUPERDUDE_NUM_PROPERTIES));
        }
    }

    private void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Fairy.FAIRY_NUM_PROPERTIES) {
            Fairy entity = Fairy.createFairy(id, pt, Double.parseDouble(properties[Fairy.FAIRY_ACTION_PERIOD_IDX]), Double.parseDouble(properties[Fairy.FAIRY_ANIMATION_PERIOD_IDX]), imageStore.getImageList("fairy"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Fairy.FAIRY_KEY, Fairy.FAIRY_NUM_PROPERTIES));
        }
    }

    private void parseCar(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Car.CAR_ACTION_PERIOD_IDX) {
            Car entity = Car.createCar(id, pt, Double.parseDouble(properties[Car.CAR_ACTION_PERIOD_IDX]), Double.parseDouble(properties[Car.CAR_ANIMATION_PERIOD_IDX]), imageStore.getImageList("car"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Car.CAR_KEY, Car.CAR_NUM_PROPERTIES));
        }
    }

    private void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Tree.TREE_NUM_PROPERTIES) {
            Tree entity = Tree.createTree(id, pt, Double.parseDouble(properties[Tree.TREE_ACTION_PERIOD_IDX]), Double.parseDouble(properties[Tree.TREE_ANIMATION_PERIOD_IDX]), Integer.parseInt(properties[Tree.TREE_HEALTH_IDX]), imageStore.getImageList(Tree.TREE_KEY));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Tree.TREE_KEY, Tree.TREE_NUM_PROPERTIES));
        }
    }

    private void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES) {
            Obstacle entity = Obstacle.createObstacle(id, pt, Double.parseDouble(properties[Obstacle.OBSTACLE_ANIMATION_PERIOD_IDX]), imageStore.getImageList(Obstacle.OBSTACLE_KEY));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Obstacle.OBSTACLE_KEY, Obstacle.OBSTACLE_NUM_PROPERTIES));
        }
    }

    private void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == House.HOUSE_NUM_PROPERTIES) {
            House entity = House.createHouse(id, pt, imageStore.getImageList("house"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", House.HOUSE_KEY, House.HOUSE_NUM_PROPERTIES));
        }
    }

    private void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Stump.STUMP_NUM_PROPERTIES) {
            Stump entity = Stump.createStump(id, pt, imageStore.getImageList(Stump.STUMP_KEY));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Stump.STUMP_KEY, Stump.STUMP_NUM_PROPERTIES));
        }
    }

    private void parseSoldier(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Soldier.SOLDIER_NUM_PROPERTIES) {
            Soldier entity = Soldier.createSoldier(id, pt, Double.parseDouble(properties[Soldier.SOLDIER_ACTION_PERIOD_IDX]), Double.parseDouble(properties[Soldier.SOLDIER_ANIMATION_PERIOD_IDX]), imageStore.getImageList("soldier"));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Soldier.SOLDIER_KEY, Soldier.SOLDIER_NUM_PROPERTIES));
        }
    }

    private void parsePortal(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == Portal.PORTAL_NUM_PROPERTIES) {
            Portal entity = Portal.createPortal(id, pt, Double.parseDouble(properties[Portal.PORTAL_ANIMATION_PERIOD_IDX]), imageStore.getImageList(Portal.PORTAL_KEY));
            this.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Portal.PORTAL_KEY, Portal.PORTAL_NUM_PROPERTIES));
        }
    }


    public Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", 5);
        if (properties.length >= 4) {
            String key = properties[PROPERTY_KEY];
            String id = properties[PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[PROPERTY_COL]), Integer.parseInt(properties[PROPERTY_ROW]));
            properties = properties.length == ENTITY_NUM_PROPERTIES
                    ? new String[0] : properties[ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case "obstacle" -> this.parseObstacle(properties, pt, id, imageStore);
                case "dude" -> this.parseDude(properties, pt, id, imageStore);
                case "fairy" -> this.parseFairy(properties, pt, id, imageStore);
                case "house" -> this.parseHouse(properties, pt, id, imageStore);
                case "tree" -> this.parseTree(properties, pt, id, imageStore);
                case "sapling" -> this.parseSapling(properties, pt, id, imageStore);
                case "stump" -> this.parseStump(properties, pt, id, imageStore);
                case "soldier" -> this.parseSoldier(properties, pt, id, imageStore);
                case "portal" -> this.parsePortal(properties, pt, id, imageStore);
                case "superdude" -> this.parseSuperDude(properties, pt, id, imageStore);
                case "car" -> this.parseCar(properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }

        } else {
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if (row < this.numRows) {
            int rows = Math.min(cells.length, this.numCols);

            for(int col = 0; col < rows; ++col) {
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }

    }

    public void parseSaveFile(Scanner saveFile, ImageStore imageStore) {
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;

        while(saveFile.hasNextLine()) {
            ++lineCounter;
            String line = saveFile.nextLine().strip();
            if (line.endsWith(":")) {
                headerLine = lineCounter;
                lastHeader = line;
                switch (line) {
                    case "Backgrounds:":
                        this.background = new Background[this.numRows][this.numCols];
                        break;
                    case "Entities:":
                        this.occupancy = new Entity[this.numRows][this.numCols];
                        this.entities = new HashSet();
                }
            } else {
                switch (lastHeader) {
                    case "Rows:":
                        this.numRows = Integer.parseInt(line);
                        break;
                    case "Cols:":
                        this.numCols = Integer.parseInt(line);
                        break;
                    case "Backgrounds:":
                        this.parseBackgroundRow(line, lineCounter - headerLine - 1, imageStore);
                        break;
                    case "Entities:":
                        this.parseEntity(line, imageStore);
                }
            }
        }

    }

    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground) {
        this.parseSaveFile(saveFile, imageStore);
        if (this.background == null) {
            this.background = new Background[this.numRows][this.numCols];

            for(Background[] row : this.background) {
                Arrays.fill(row, defaultBackground);
            }
        }

        if (this.occupancy == null) {
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet();
        }

    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public Optional<Entity> getOccupant(Point pos) {
        return this.isOccupied(pos) ? Optional.of(this.getOccupancyCell(pos)) : Optional.empty();
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, (Entity)null);
        }

    }

    public void removeEntity(EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents(entity);
        this.removeEntityAt(entity.getPosition());
    }

    public void moveEntity(EventScheduler scheduler, Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, (Entity)null);
            Optional<Entity> occupant = this.getOccupant(pos);
            occupant.ifPresent((target) -> this.removeEntity(scheduler, target));
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }

    }

    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }


    // One for Class<?>
    public Optional<Entity> findNearest(Point position, Class<? extends Entity> targets) {
        List<Entity> ofType = new LinkedList();
        for (Entity entity : this.entities) {
            if (targets.isInstance(entity)){
                ofType.add(entity);
            }
        }
        return Entity.nearestEntity(ofType, position);
    }

     //Another for List<Class<? extends Entity>
    public Optional<Entity> findNearest(Point position, List<Class<? extends Entity>> targets) {
        List<Entity> ofType = new LinkedList<>();

        for (Entity entity : this.entities) {
            for (Class<? extends Entity> classz : targets){
                if (classz.isInstance(entity)) {
                    ofType.add(entity);
                    break;
                }
            }
        }
        return Entity.nearestEntity(ofType, position);
    }


    public boolean isOccupied(Point pos) {
        if(!withinBounds(pos)){
            return false;
        }
        Entity entity = getOccupancyCell(pos);

        return entity != null && !(entity instanceof Portal);
        //return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }


    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0 && pos.x < this.numCols;
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            throw new IllegalArgumentException("position occupied");
        } else {
            this.addEntity(entity);
        }
    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log() {
        List<String> list = new ArrayList();
        for(Entity entity : this.entities) {
            String log = entity.log();
            if (log != null) {list.add(log);}
        }

        return list;
    }

}
