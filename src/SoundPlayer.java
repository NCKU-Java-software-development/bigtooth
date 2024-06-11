import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class SoundPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    // 加载音效文件
    SoundPlayer(String soundFileName) {
        try {
            // 获取音效文件的 URL
            URL soundFile = getClass().getResource(soundFileName);
            if (soundFile == null) {
                System.err.println("音效文件未找到: " + soundFileName);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // 打开音效剪辑
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // 获取音量控制器
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 播放音效
    public void playSound() {
        if (clip != null) {
            clip.setFramePosition(0); // 将音效播放位置设置到起点
            clip.start(); // 开始播放
        }
    }

    // 设置音量（音量值范围：-80.0 到 6.0206）
    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    // 停止音效
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // 停止播放
        }
    }
}
