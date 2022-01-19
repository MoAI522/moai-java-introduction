package com.moai.cw.fx;

import java.io.InputStream;
import java.util.ArrayList;

import com.moai.cw.Constants;
import com.moai.cw.util.GraphicObject;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Drawer {
  private Image[] textures;

  public Drawer() {
    loadTextures();
  }

  public void loadTextures() {
    textures = new Image[Constants.TEXTURE_NUMBER];
    for (int i = 0; i < Constants.TEXTURE_NUMBER; i++) {
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("textures/" + i + ".png");
      textures[i] = new Image(is);
    }
  }

  public void drawObjects(ArrayList<GraphicObject> objects, PixelWriter pw) {
    for (GraphicObject obj : objects) {
      switch (obj.type) {
        case TEXTURE: {
          PixelReader pr = textures[obj.textureIndex].getPixelReader();
          int originX = (int) Math.floor(obj.position.x),
              originY = (int) Math.floor(obj.position.y),
              pixelRatioX = (int) Math.floor(Constants.PIXEL_RATIO * obj.scale.x),
              pixelRatioY = (int) Math.floor(Constants.PIXEL_RATIO * obj.scale.y),
              uTo = obj.uv.x + obj.size.x,
              vTo = obj.uv.y + obj.size.y,
              dispW = Constants.DISPLAY_WIDTH, dispH = Constants.DISPLAY_HEIGHT;
          for (int v = obj.uv.y, i = 0; v < vTo; v++, i++) {
            if (obj.hReverse) {
              for (int u = uTo - 1, j = 0; u >= obj.uv.x; u--, j++) {
                if (originX + j < 0 || originX + j >= dispW || originY + i < 0 || originY + i >= dispH)
                  continue;
                for (int k = 0; k < pixelRatioY; k++) {
                  for (int l = 0; l < pixelRatioX; l++) {
                    if (pr.getArgb(u, v) == 0xff00ff00) {
                      continue;
                    }
                    pw.setPixels((originX + j) * pixelRatioX + l, (originY + i) * pixelRatioY + k,
                        1, 1, pr, u, v);
                  }
                }
              }
            } else {
              for (int u = obj.uv.x, j = 0; u < uTo; u++, j++) {
                if (originX + j < 0 || originX + j >= dispW || originY + i < 0 || originY + i >= dispH)
                  continue;
                for (int k = 0; k < pixelRatioY; k++) {
                  for (int l = 0; l < pixelRatioX; l++) {
                    if (pr.getArgb(u, v) == 0xff00ff00) {
                      continue;
                    }
                    pw.setPixels((originX + j) * pixelRatioX + l, (originY + i) * pixelRatioY + k,
                        1, 1, pr, u, v);
                  }
                }
              }
            }
          }
          break;
        }
        case TEXTURE_SCROLL: {
          PixelReader pr = textures[obj.textureIndex].getPixelReader();
          int originX = (int) Math.floor(obj.position.x),
              originY = (int) Math.floor(obj.position.y),
              pixelRatioX = (int) Math.floor(Constants.PIXEL_RATIO * obj.scale.x),
              pixelRatioY = (int) Math.floor(Constants.PIXEL_RATIO * obj.scale.y),
              originU = obj.trimUV.x,
              originV = obj.trimUV.y,
              uTo = obj.uv.x + obj.size.x,
              vTo = obj.uv.y + obj.size.y,
              trimSizeU = obj.trimSize.x, trimSizeV = obj.trimSize.y,
              dispW = Constants.DISPLAY_WIDTH, dispH = Constants.DISPLAY_HEIGHT;
          for (int v = obj.uv.y, i = 0; v < vTo; v++, i++) {
            for (int u = obj.uv.x, j = 0; u < uTo; u++, j++) {
              if (originX + j < 0 || originX + j >= dispW || originY + i < 0 || originY + i >= dispH)
                continue;
              int su = u % trimSizeU, sv = v % trimSizeV;
              for (int k = 0; k < pixelRatioY; k++) {
                for (int l = 0; l < pixelRatioX; l++) {
                  if (pr.getArgb(originU + su, originV + sv) == 0xff00ff00) {
                    continue;
                  }
                  pw.setPixels((originX + j) * pixelRatioX + l, (originY + i) * pixelRatioY + k,
                      1, 1, pr, originU + su, originV + sv);
                }
              }
            }
          }
          break;
        }
        case COLOR: {
          int originX = Math.max((int) Math.floor(obj.position.x * Constants.PIXEL_RATIO), 0),
              originY = Math.max((int) Math.floor(obj.position.y * Constants.PIXEL_RATIO), 0),
              toX = Math.min((int) Math.floor((obj.position.x + obj.scale.x) * Constants.PIXEL_RATIO),
                  Constants.DISPLAY_WIDTH * Constants.PIXEL_RATIO),
              toY = Math.min((int) Math.floor((obj.position.y + obj.scale.y) * Constants.PIXEL_RATIO),
                  Constants.DISPLAY_HEIGHT * Constants.PIXEL_RATIO);
          Color c = new Color(obj.color.r, obj.color.g, obj.color.b, obj.color.a);
          for (int x = originX; x < toX; x++) {
            for (int y = originY; y < toY; y++) {
              pw.setColor(x, y, c);
            }
          }
          break;
        }
      }
    }
  }
}
