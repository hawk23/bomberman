package game.config;

import java.awt.*;

/**
 * Created by Mario on 10.04.2015.
 */
public class GameObjectConfig
{
    private int id;
    private String path;
    private String image;
    private String name;

    private String description = "";

    public GameObjectConfig() {
    }

    public GameObjectConfig(int id, String path, String image, String name, String description) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
