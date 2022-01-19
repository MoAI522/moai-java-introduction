package com.moai.cw.fx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {
  private static final String[] filenames = { "i0", "l1", "2", "3", "4", "5", "6", "7", "8", "i9", "l10" };
  private static final String suffix = ".aif";

  private MediaPlayer[] mediaPlayers;

  public SoundPlayer() {
    mediaPlayers = new MediaPlayer[filenames.length];

    for (int i = 0; i < filenames.length; i++) {
      String src = getClass().getClassLoader().getResource("sounds/" + filenames[i] + suffix)
          .toExternalForm();
      Media media = new Media(src);
      mediaPlayers[i] = new MediaPlayer(media);
    }
  }

  public void play(int index) {
    if (filenames[index].startsWith("i")) {
      final SoundPlayer _this = this;
      mediaPlayers[index].setOnEndOfMedia(new Runnable() {
        public void run() {
          _this.play(index + 1);
        }
      });
    } else if (filenames[index].startsWith("l")) {
      mediaPlayers[index].setCycleCount(MediaPlayer.INDEFINITE);
    }
    mediaPlayers[index].seek(new Duration(0));
    mediaPlayers[index].play();
  }

  public void stop(int index) {
    mediaPlayers[index].stop();
  }
}