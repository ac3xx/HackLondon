package frontend;

/**
 * Created by James on 28/02/15.
 */
public enum Tile {
    ROCK("rock"),
    WATER("water"),
    TREE("tree"),
    TREEALT("treealt"),
    GRASS("grass"),
    SAND("sand");

    private final String fileName;
    Tile(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }

    public String getFile(String file) {
        return file + ".png";
    }

    public String getTransitionWith(Tile otherTile, TileTransition transition) {
        return getFile(fileName + "_"
                + otherTile.getFileName()
                + "_"
                + transition.ordinal());
    }

}
