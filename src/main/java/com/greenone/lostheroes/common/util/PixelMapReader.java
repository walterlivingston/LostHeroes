package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.LostHeroes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PixelMapReader {
    private final BufferedImage map;
    private final int mapWidth;
    private final int mapHeight;
    private final int[][] colorMap;

    public PixelMapReader(String filename) {
        BufferedImage map1;
        try{
            map1 = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("assets/"+ LostHeroes.MOD_ID +"/textures/"+filename+".png")));
        } catch (IOException e) {
            e.printStackTrace();
            map1 = new BufferedImage(255,255,255);
        }
        map = map1;
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        colorMap = new int[mapWidth][mapHeight];
        generateColorMap();
    }

    private void generateColorMap() {
        for(int x=0; x<mapWidth; x++){
            for(int y=0; y<mapHeight; y++){
                colorMap[x][y] = map.getRGB(x,y);
            }
        }
    }

    public BufferedImage getMap() {
        return map;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int[][] getColorMap() {
        return colorMap;
    }

    public String getColor(int x, int y){
        return Integer.toHexString(colorMap[x][y]);
    }
}
