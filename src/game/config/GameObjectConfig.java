package game.config;

import org.newdawn.slick.Image;

public class GameObjectConfig
{
    private int id;
    private String path;
    private Image image;
    private String name;

    private String description = "";

    public GameObjectConfig() {
    }

    public GameObjectConfig(int id, String path, Image image, String name, String description) {
        this.id = id;
        this.path = path;
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
