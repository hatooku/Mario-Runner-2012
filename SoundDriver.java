import javax.sound.sampled.*;
import java.io.*;

public class SoundDriver {
	private Clip[] clips;
	private FloatControl gainControl;

	SoundDriver(String[] aClips)	{

		clips = new Clip[aClips.length];
		try{
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
			AudioSystem.NOT_SPECIFIED,
			16, 2, 4,
			AudioSystem.NOT_SPECIFIED, true);
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			for(int i = 0; i<  clips.length; i++) {
				File soundFile = new File(aClips[i]);
				BufferedInputStream bs = new BufferedInputStream(new FileInputStream(soundFile));
				AudioInputStream soundIn = AudioSystem.getAudioInputStream(bs);
				clips[i] = (Clip)AudioSystem.getLine(info);
				clips[i].open(soundIn);
				gainControl = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
			}
		System.out.println("Audio File Loaded");
		} catch (UnsupportedAudioFileException ex){
			System.out.println("Unsupported Audio File");
		} catch (LineUnavailableException ex) {
			System.out.println("Line Unavailable");
		} catch (IOException ex) {
			System.out.println("IO Error" + ex);
		}




		}

	public void play(int value) {

		clips[value].stop();
		clips[value].setFramePosition(0);
		clips[value].start();
	}

	public void loop(int value) {
		clips[value].stop();
		clips[value].setFramePosition(0);
		clips[value].loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop(int value) {
		clips[value].stop();
	}

	public void setVolume(float volume) {
		//gainControl.setValue(volume);
	}





}